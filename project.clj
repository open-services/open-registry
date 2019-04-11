(defproject open-registry "0.1.0-SNAPSHOT"
  :description "A Open Implementation of a JavaScript modules registry"
  :url "https://open-registry.dev"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"]
                 [clj-http "3.9.1"]
                 [compojure "1.6.1"]
                 [ipfs-api "0.1.1-SNAPSHOT"]
                 [io.prometheus/simpleclient_hotspot "0.6.0"]
                 [iapetos "0.1.8"]]
  :plugins [[lein-ring "0.12.5"]]
  :main ^:skip-aot open-registry.core
  :ring {:handler open-registry.core/app-routes}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
