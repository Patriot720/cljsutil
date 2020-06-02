(ns antd-util.autocomplete
  (:require ["antd" :as antd :refer [AutoComplete]]
            [re-frame.core :refer [subscribe dispatch]]
            [cljs.test :refer-macros [deftest testing is]]
            [appointments-frontend.strings :as strings]
            [reagent.core :as r]
            [antd-util.caret-fix  :refer [caret-fix]]
            [oops.core :refer [oget+ oget oset!]])
  )


(def  Option (oget AutoComplete "Option"))
(def  OptGroup (oget AutoComplete "OptGroup"))

(defn view [m input]
  " m required fields:
  :suggestions any"
  (let [selected? (r/atom false)
        focused? (r/atom false)]
    (fn [m input]
      [:> antd/AutoComplete (merge m
                                   {:on-blur #(reset! focused? false)
                                    :open (not (or
                                                (not @focused?)
                                                (empty? (:suggestions m))
                                                @selected?
                                                (empty? (:value m))))
                                    :on-focus #(reset! focused? true)
                                    :on-select (fn [val option]
                                                 ((:on-select m) val option)
                                                 (reset! selected? true))} )
       (conj input selected?)])))



