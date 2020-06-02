(ns async.util
  (:require [clojure.core.async :as a]))


(defn quick-timeout [f]
  (js/setTimeout f 100))

;; Chaining
(defn go>! [ch val]
  (a/go (a/>! ch val)))

(defn wait-and-go>! [ms ch data]
  (js/setTimeout #(go>! ch data) ms))

(defn- chain-funcs [& funcs]
  (let [step-chan (a/chan)]
    (go>! step-chan funcs)
    (a/go
      (while true
        (let [funcs (a/<! step-chan)
              func (first funcs)]
          (func)
          (when (not-empty (drop 1 funcs ))
            (wait-and-go>! 150 step-chan (drop 1 funcs)) ))))))


(def wait #())

(defn chain [& coll-of-funcs]
  "chain functions or arrays of functions"
  (apply chain-funcs (flatten coll-of-funcs) ))
