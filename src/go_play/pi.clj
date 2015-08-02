(ns go-play.pi
  (:use [maya.core])
  (:require [clojure.core.async :refer [chan go alt! >! <! <!!]]))

;; A port of:
;; https://golang.org/doc/play/pi.go

(defn term [k]
  (maya -1 Math/pow k * 4 :as num,
         k * 2 + 1 :as denom,
         num / denom))

(defn pi [n]
  (let [ch (chan n)
        f  (atom 0)]
    (dotimes [k (inc n)]
      (go (>! ch (term k))))
    (dotimes [k (inc n)]
      (swap! f + (<!! (go (<! ch)))))
    @f))

(pi 5000)
