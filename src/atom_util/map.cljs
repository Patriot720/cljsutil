(ns atom-util.map
  (:require [reagent.core :as r :refer [atom]]))

(defn values->atoms [items]
  "Turns each item of a map -> atom"
  (into {} (map (fn [[k v]] [k (atom v)]) items)))

(defn update-atoms
  [atoms-map new-values-map]
  {:pre [(map? atoms-map) (map? new-values-map)]}
  (doseq [[key atom] atoms-map]
    (reset! atom (key new-values-map))))
