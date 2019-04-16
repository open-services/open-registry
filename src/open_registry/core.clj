(ns open-registry.core
  (:require [org.httpkit.server :refer [run-server]]
            [clj-http.client :as http2]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.pprint :refer [pprint]]
            [clojure.java.io :as io]
            [iapetos.core :as prometheus]
            [iapetos.export :as export]
            [iapetos.collector.jvm :as jvm]
            [ipfs-api.files :refer [write read path-exists?]]
            [npm-registry-follow.core :refer [listen-for-changes]])
  (:gen-class))

(defonce registry
  (-> (prometheus/collector-registry)
      (jvm/initialize)
      (prometheus/register
        (prometheus/counter :app/metadata)
        (prometheus/counter :app/metadata-cached)
        (prometheus/counter :app/metadata-fetch-npm)

        (prometheus/counter :app/tarball)
        (prometheus/counter :app/tarball-cached)
        (prometheus/counter :app/tarball-fetch-npm)

        (prometheus/counter :app/change-feed-skip)
        (prometheus/counter :app/change-feed-update))))

(def api-multiaddr "/ip4/127.0.0.1/tcp/5001")

(def registry-url (String. (or (System/getenv "REGISTRY_URL")
                               "http://registry.open-registry.dev:3000")))

(def replicate-url "https://registry.npmjs.org")

(defn metadata-handler [package-name force-refresh]
  (future (prometheus/inc registry :app/metadata))
  (let [path (format "/npmjs.org/%s/metadata.json" package-name)
        url (format "%s/%s" replicate-url package-name)
        exists? (path-exists? api-multiaddr path)]
    (if (and exists? (not force-refresh))
      (do
        (future (prometheus/inc registry :app/metadata-cached))
        (read api-multiaddr path))
      (let [res (http2/get url {:as :text})
            new-body (clojure.string/replace (:body res)
                                             (re-pattern replicate-url)
                                             registry-url)]
        (future (prometheus/inc registry :app/metadata-fetch-npm))
        (write api-multiaddr path new-body)
        (-> res
            (dissoc :headers)
            (assoc :body new-body))))))

(defn serve-cached-tarball [path]
  (future (prometheus/inc registry :app/tarball-cached))
  (java.io.ByteArrayInputStream. (read api-multiaddr path {:as :byte-array})))

(defn get-tarball [url path]
  (future (prometheus/inc registry :app/tarball))
  (let [exists? (path-exists? api-multiaddr path)]
    (if exists?
      (serve-cached-tarball path)
      (let [res (http2/get url {:as :byte-array})]
        (future (prometheus/inc registry :app/tarball-fetch-npm))
        (write api-multiaddr path (:body res))
        (java.io.ByteArrayInputStream. (:body res))))))

(defn tarball-handler [package-name tarball]
  (let [path (format "/npmjs.org/%s/%s" package-name tarball)
        url (format "%s/%s/-/%s" replicate-url package-name tarball)]
    (get-tarball url path)))

(defn scoped-tarball-handler [scope package-name tarball]
  (let [path (format "/npmjs.org/%s/%s/%s" scope package-name tarball)
        url (format "%s/%s/%s/-/%s" replicate-url scope package-name tarball)]
    (get-tarball url path)))

(defroutes app-routes
  (GET "/metrics" [] (export/text-format registry))
  (GET "/:package" [package] (metadata-handler package false))
  (GET "/:package/-/:tarball" [package tarball] (tarball-handler package tarball))
  (GET "/:scope/:package/-/:tarball" [scope package tarball] (scoped-tarball-handler scope package tarball))
  (route/not-found "Couldnt find that for you"))

(defn handle-change [package-name]
  (let [path (format "/npmjs.org/%s/metadata.json" package-name)
        exists? (path-exists? api-multiaddr path)]
    (when exists?
      ;; TODO seems sometimes npm are not up-to-date with their own registry
      ;; as too quick requests can give us old information, even though the
      ;; replication server told us there was a change
      (metadata-handler package-name true))
    (if exists?
      (future (prometheus/inc registry :app/change-feed-update))
      (future (prometheus/inc registry :app/change-feed-skip)))))

;; Listens for changes to the npm registry and updates the metadata for
;; packages that already exists in our cache
(defn update-metadata-for-existing-packages []
  (println "Now listening for npm registry changes")
  (listen-for-changes handle-change))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5432"))
        threads (Integer/parseInt (or (System/getenv "SERVER_THREADS") "128"))]
    (update-metadata-for-existing-packages)
    (run-server #'app-routes {:port port
                              :thread threads})
    (println (str "Server running on port " port))))
