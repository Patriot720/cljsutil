(ns cljs.util)

(defn inc-biggest-val-in-a-coll-of-maps [coll-of-maps key & [min]]
  {:post [(number? %)]}
  "input coll-of-maps of maps and a key to get biggest val + 1"
  (let [next-thing (key (apply max-key #(key %) coll-of-maps))]
    (if next-thing
      (inc next-thing)
      (or min 0))))

(defn map-items->int [coll-of-maps key]
  {:pre [(coll? coll-of-maps)]
   :post [(coll? %)]}
  "non-recursive update coll-of-maps of maps specific key values -> int"
  (map #(update % key int) coll-of-maps))
 

(defn dissoc-when
  "Dissoc those keys from m which are mentioned among ks and whose
   values in m satisfy pred."
  [pred m & ks]
  (apply dissoc m (filter #(pred (m %)) ks)))

(defn delete-by-index [coll i]
  (let [coll (vec coll)]
    (vec (concat (subvec coll 0 i)
                 (subvec coll (inc i)))) ))
