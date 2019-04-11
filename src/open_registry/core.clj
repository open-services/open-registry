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
            [ipfs-api.files :refer [write read path-exists?]])
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
        (prometheus/counter :app/tarball-fetch-npm))))

(def api-multiaddr "/ip4/127.0.0.1/tcp/5001")

(defn metadata-handler [package-name]
  (future (prometheus/inc registry :app/metadata))
  (let [path (format "/npmjs.org/%s/metadata.json" package-name)
        url (format "https://registry.npmjs.org/%s" package-name)
        exists? (path-exists? api-multiaddr path)]
    (if exists?
      (do
        (future (prometheus/inc registry :app/metadata-cached))
        (read api-multiaddr path))
      (let [res (http2/get url {:as :text})
            new-body (clojure.string/replace (:body res)
                                             #"https://registry.npmjs.org"
                                             ;; TODO needs to rewrite the right address
                                             "http://registry.open-registry.dev:3000")]
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
        url (format "https://registry.npmjs.org/%s/-/%s" package-name tarball)]
    (get-tarball url path)))

(defn scoped-tarball-handler [scope package-name tarball]
  (let [path (format "/npmjs.org/%s/%s/%s" scope package-name tarball)
        url (format "https://registry.npmjs.org/%s/%s/-/%s" scope package-name tarball)]
    (get-tarball url path)))

(defroutes app-routes
  (GET "/metrics" [] (export/text-format registry))
  (GET "/:package" [package] (metadata-handler package))
  (GET "/:package/-/:tarball" [package tarball] (tarball-handler package tarball))
  (GET "/:scope/:package/-/:tarball" [scope package tarball] (scoped-tarball-handler scope package tarball))
  (route/not-found "Couldnt find that for you"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5432"))]
    (run-server #'app-routes {:port port})
    (println (str "Server running on port " port))))
