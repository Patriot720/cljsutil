(ns tour.util
  (:require
   [reagent.core :as r]
   ["antd" :as antd]))


(defn- step [& children]
  (into [:div {:style {:margin 15}}] children))

(defn- normal-step [context text & [actions confirm-text timeout]]
  [step
   [:div text]
   [:> antd/Button
    {:type "primary"
     :class "next_tour_slide"
     :on-click (fn []
                 (when actions (actions))
                 (js/setTimeout #(.goTo context (.-step context)) (or timeout 550)))} (or confirm-text "Понятно" )]])

(defn- yes-no-step [context text yes no & [confirm-text decline-text]]
  [step
   [:div text]
   [:> antd/Button
    {:type "danger"
     :style {:margin-right 15}
     :on-click (fn []
                 (no context))}
    (or decline-text "Нет")]
   [:> antd/Button
    {:type "primary"
     :class "next_tour_slide"
     :on-click (fn []
                 (yes context))}
    (or confirm-text "Да")]])

(defn yes-no-step-fn [text yes no & [confirm-text decline-text]]
  (fn [context]
    (r/as-element [yes-no-step context text yes no confirm-text decline-text])))

(defn step-fn [text & [actions confirm-text]]
  (fn [context]
    (r/as-element [normal-step context text actions confirm-text])))
(defn custom-confirm-action-step-fn [text on-confirm & [confirm-text]]
  (fn [context]
    (r/as-element
     [step
      [:div text]
      [:> antd/Button
       {:type "primary"
        :class "next_tour_slide"
        :on-click (fn [] (on-confirm context))}
       (or confirm-text "Да")]
      ] ) )
  )
