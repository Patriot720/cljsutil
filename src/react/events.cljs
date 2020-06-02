(ns react.events)


(def ^:private native-input-setter (-> js/Object
                                       (.getOwnPropertyDescriptor js/window.HTMLInputElement.prototype "value")
                                       .-set))

(defn- trigger-input-jq [jq-element]
  (let [event (js/Event. "input" #js {"bubbles" true})]
    (.dispatchEvent (.get jq-element 0) event)))

(defn trigger [jq-element event]
  "Trigger on Jquery jq-element bubbling"
  (let [event (js/Event. event #js {"bubbles" true})]
    (.dispatchEvent (.get jq-element 0) event)))

(defn click [jq-element]
  "Trigger click on Jquery jq-element bubbling"
  (let [event (js/Event. "click" #js {"bubbles" true})]
    (.dispatchEvent (.get jq-element 0) event)))

(defn mouse-over [jq-element]
  "Trigger mouseover on Jquery jq-element bubbling"
  (let [event (js/Event. "mouseover" #js {"bubbles" true})]
    (.dispatchEvent (.get jq-element 0) event)))


(defn- set-input [jq-element value]
  (.call native-input-setter jq-element value))

;; (defn- set-input-jq [jq-element value]
;;   (set-input (.get jq-element 0) value)
;;   jq-element)

(defn update-input [jquery-element value]
  "Update input bubbling"
  (set-input (.get jquery-element 0) value)
  (trigger-input-jq jquery-element))

