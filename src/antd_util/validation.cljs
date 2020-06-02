(ns antd-util.validation
  (:require [form-validator.core :as form-validator]
            [appointments-frontend.db :as db]
            [antd-util.form :refer [FormItem]]
            [reagent.core :as r :refer [atom]]
            [appointments-frontend.strings :as strings]))

(swap! form-validator/conf #(merge % {:atom r/atom}))

(def validator (form-validator/init-form
                {:form-spec ::db/class}))

(defn error-status [validator name]
  (when (form-validator/?show-message validator name) "error"))

(defn error-message [validator name]
  (form-validator/?show-message validator name strings/messages))

(defn set-validator-values->atoms
  ([atoms validator] (swap! validator (fn [form]
                                        (assoc form :names->value atoms))) ))

(defn with-validation-for
  "returns ant design form item with form validation"
  [validator key other-properties]
  (merge  {:validate-status (error-status validator key)
           :help (error-message validator key)}
          other-properties))

(defn form-item-with-validation-for [validator key label & children]
  (into [:> FormItem (with-validation-for validator key
                        {:label label})] children))

(defn atom? [item]
  (= (type item) reagent.ratom/RAtom))

(defn deref-atom-map [atoms]
  (into {} (map (fn [[k v]]
                  [k (if (atom? v) @v v)] ) atoms) ))

(defn valid-form? [validator form-values]
  (set-validator-values->atoms (deref-atom-map form-values) validator)
  (form-validator/validate-form-and-show? validator))

(defn submit-when-valid [validator form-values & callbacks]
  (when (valid-form? validator form-values)
    (doseq [callback callbacks]
      (callback))))
