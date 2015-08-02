(ns go-play.even-odd
  (:require [clojure.core.async :refer [chan go alt! >!]]))

(def even (chan))
(def odd  (chan))

(go (while true
  (alt!
    even ([v] (println "Got" v "from even"))
    odd  ([v] (println "Got" v "from odd")))))

(go (doseq [i (range 0 10 2)]
      (>! even i)
      (Thread/sleep (rand-int 1000))))

(go (doseq [i (range 1 10 2)]
      (>! odd i)
      (Thread/sleep (rand-int 1000))))
