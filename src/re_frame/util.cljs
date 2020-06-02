(ns re-frame.util
  (:require
   [cljs.spec.alpha :as s]
   [re-frame.core :as re-frame :refer [after path ->interceptor]]))

(defn before [f]
  (->interceptor :before (fn [{{[_ & args] :event}:coeffects :as fx}]
                           (apply f args)
                           fx)))

(defn- check-and-throw
  "Throws an exception if `db` doesn't match the Spec `a-spec`."
  [a-spec db]
  (when-not (s/valid? a-spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str a-spec db)) {}))))

