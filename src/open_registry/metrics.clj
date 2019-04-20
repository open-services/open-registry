(ns open-registry.metrics
  (:require [iapetos.core :as prometheus]
            [iapetos.export :as export]
            [iapetos.collector.jvm :as jvm]))

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

(defn registry-export []
  (export/text-format registry))

(defn increase [k]
  (prometheus/inc registry k))
