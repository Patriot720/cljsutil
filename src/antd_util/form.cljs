(ns antd-util.form
  (:require
   [oops.core :refer [oget]]
   [antd-util.caret-fix  :refer [caret-fix]]
   ["antd" :as antd :refer [Form]]))
;
(def grid-horizontal-layout {:label-col {:xs {:span 24}
                                         :sm {:span 8}}
                             :wrapper-col {:xs {:span 24}
                                           :sm {:span 16}}})
(def FormItem (.-Item Form))

(defn form [m & children]
  (into [:> Form (merge
                  grid-horizontal-layout
                  m)] children))

(defn submit-button [submit-text]
  [:> FormItem
   [:> antd/Button {:type "primary"
                    :htmlType "submit"} submit-text]])

(defn input
  [{value :value,
    :or {placeholder "" update-suggestions #()} :as m}]
  [:> antd/Input (merge m {:on-input
                           (caret-fix #(reset! value (oget % "target.value")))
                           :value @value} )])
