(ns antd-util.caret-fix
  (:require [oops.core :refer [oget+ oget oset!]]))
;;

(defn
  reset-caret-fix-wrap
  [reset-func item]
  (let [element (oget item "target")
        caret (.-selectionStart (oget item "target"))]
    (reset-func item)
    (.requestAnimationFrame
     js/window (fn []
                 (oset! element "selectionStart" caret)
                 (oset! element "selectionEnd" caret)))))

(defn caret-fix [f]
  (partial reset-caret-fix-wrap f))
