(ns open-registry.http
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [clj-http.client :as http2]
            [ipfs-api.files :refer [write read path-exists?]]
            [open-registry.metrics :as metrics]
            ))

(def replicate-url "https://registry.npmjs.org")

;; TODO should be passed from open-registry.core
(def registry-url (String. (or (System/getenv "REGISTRY_URL")
                               "http://registry.open-registry.dev:3000")))

;; TODO needs System/getenv + passed from open-registry.core
(def api-multiaddr (String. (or (System/getenv "IPFS_API")
                               "/ip4/127.0.0.1/tcp/5001")))

(defn metadata-handler [package-name force-refresh]
  (future (metrics/increase :app/metadata))
  (let [path (format "/npmjs.org/%s/metadata.json" package-name)
        url (format "%s/%s" replicate-url package-name)
        exists? (path-exists? api-multiaddr path)]
    ;; TODO should not be here as we're writing the new registry url permanently..
    (if (and exists? (not force-refresh))
      (do
        (future (metrics/increase :app/metadata-cached))
        (read api-multiaddr path))
      (let [res (http2/get url {:as :text})
            new-body (clojure.string/replace (:body res)
                                             (re-pattern replicate-url)
                                             registry-url)]
        (future (metrics/increase :app/metadata-fetch-npm))
        (write api-multiaddr path new-body)
        (-> res
            (dissoc :headers)
            (assoc :body new-body))))))

(defn serve-cached-tarball [path]
  (future (metrics/increase :app/tarball-cached))
  (java.io.ByteArrayInputStream. (read api-multiaddr path {:as :byte-array})))

(defn get-tarball [url path]
  (future (metrics/increase :app/tarball))
  (let [exists? (path-exists? api-multiaddr path)]
    (if exists?
      (serve-cached-tarball path)
      (let [res (http2/get url {:as :byte-array})]
        (future (metrics/increase :app/tarball-fetch-npm))
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
  (GET "/:package" [package] (metadata-handler package false))
  (GET "/:package/-/:tarball" [package tarball] (tarball-handler package tarball))
  (GET "/:scope/:package/-/:tarball" [scope package tarball] (scoped-tarball-handler scope package tarball))
  (route/not-found "Couldnt find that for you"))

(defn start-server [port threads]
    (run-server #'app-routes {:port port
                              :thread threads})
    (println (str "Server running on port " port " with " threads " threads")))

(comment
  (future
    (println "hello"))
  (future
    (start-server 5432 8))
  
  )
