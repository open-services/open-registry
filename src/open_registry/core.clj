(ns open-registry.core
  (:require [clojure.pprint :refer [pprint]]
            [clojure.java.io :as io]
            [ipfs-api.files :refer [write read path-exists?]]
            [npm-registry-follow.core :refer [listen-for-changes]]
            [open-registry.http :as http]
            [open-registry.metrics :as metrics])
  (:gen-class))

(defn handle-change [package-name]
  (let [path (format "/npmjs.org/%s/metadata.json" package-name)
        exists? (path-exists? http/api-multiaddr path)]
    (when exists?
      ;; TODO seems sometimes npm are not up-to-date with their own registry
      ;; as too quick requests can give us old information, even though the
      ;; replication server told us there was a change
      (http/metadata-handler package-name true))
    (if exists?
      (future (metrics/increase :app/change-feed-update))
      (future (metrics/increase :app/change-feed-skip)))))

;; Listens for changes to the npm registry and updates the metadata for
;; packages that already exists in our cache
(defn update-metadata-for-existing-packages []
  (println "Now listening for npm registry changes")
  (listen-for-changes handle-change))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5432"))
        metrics-port (Integer/parseInt (or (System/getenv "METRICS_PORT") "2345"))
        threads (Integer/parseInt (or (System/getenv "SERVER_THREADS") "128"))
        in-dev? (Boolean/parseBoolean (or (System/getenv "SERVER_DEV") "false"))]
    (update-metadata-for-existing-packages)
    (http/start-server port threads in-dev?)
    (metrics/start-server metrics-port)
  ))

(comment
  (println "hello world")
  (future
    (http/start-server 5432 8)
    )
  
  )
