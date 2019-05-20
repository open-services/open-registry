(defproject open-registry "0.1.0-SNAPSHOT"
  :description "A Open Implementation of a JavaScript modules registry"
  :url "https://open-registry.dev"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"]
                 [clj-http "3.9.1"]
                 [ring/ring-devel "1.7.1"]
                 [compojure "1.6.1"]
                 [open-services/ipfs-api "1.1.0"]
                 [open-services/npm-registry-follow "1.2.1"]
                 [io.prometheus/simpleclient_hotspot "0.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [iapetos "0.1.8"]
                 [org.clojure/tools.logging "0.4.1"]]
  ;; Only to be used in dev for profiling etc
  ;; :jvm-opts ["-Dcom.sun.management.jmxremote"
  ;;            "-Dcom.sun.management.jmxremote.port=9010"
  ;;            "-Dcom.sun.management.jmxremote.local.only=false"
  ;;            "-Dcom.sun.management.jmxremote.authenticate=false"
  ;;            "-Dcom.sun.management.jmxremote.ssl=false"]
  :plugins [[lein-ring "0.12.5"]]
  :main ^:skip-aot open-registry.core
  :ring {:handler open-registry.core/app-routes}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
