(ns open-registry.draw-logo
  (:import java.io.File
           java.awt.Color
           java.awt.image.BufferedImage
           javax.imageio.ImageIO
           java.awt.RenderingHints))

;; 0 = background
;; 1 = orange
;; 2 = blue
(def grid [[0 1 1 0 2 2 0]
           [1 0 0 1 0 0 2]
           [1 0 0 1 2 2 0]
           [1 0 0 1 0 0 2]
           [0 1 1 0 0 0 2]])

;; the width and height of each block
(def block-size 20)

;; the margin of each block
(def block-offset (+ block-size 5))

;; border radius (smoothing)
(def radius 5)

(def background-block (Color. 255 255 255))
(def blue-block (Color. 134 187 216))
(def orange-block (Color. 246 174 45))

(def color-map {0 background-block
                1 orange-block
                2 blue-block})


(defn draw-logo [background path]
  (def bi (BufferedImage. 190 140 BufferedImage/TYPE_INT_ARGB))
  (def g (.createGraphics bi))
  ;; enable anti-aliasing + better render quality
  (.setRenderingHints g {RenderingHints/KEY_ANTIALIASING
                         RenderingHints/VALUE_ANTIALIAS_ON

                         RenderingHints/KEY_RENDERING
                         RenderingHints/VALUE_RENDER_QUALITY})
  ;; use background if set
  (when background
    (.setColor g background)
    (.fillRect g 0 0 190 140))
  (let [current-y (atom 10)]
    (doseq [row grid]
      (let [current-x (atom 10)]
        (doseq [item row]
          (let [color (get color-map item)]
            (.setColor g color)
            ;; (println (format "x=%s y=%s" @current-x @current-y))
            (if (not= item 0)
              (.fillRoundRect g @current-x @current-y block-size block-size radius radius)))
          (swap! current-x + block-offset)))
      (swap! current-y + block-offset)))

  (ImageIO/write bi "png"  (File. path)))

(draw-logo false "logos/transparent.png")
(draw-logo (Color. 0 0 0) "logos/black.png")
(draw-logo (Color. 255 255 255) "logos/white.png")
