(ns clojure-sidekiq.core
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json]
            [secure-rand.core :refer (hex)]))

(def redis-config (atom
              {:pool {}
               :spec {:host "127.0.0.1" :port 6379 :db 3}}))

(defn configure [c] (swap! redis-config merge c))

(defmacro wcar*  [& body] `(car/wcar @redis-config ~@body))

(def default-opts {:queue "default" :retry false})

(defn payload-map [worker-name args opts]
  {:class worker-name
   :args args
   :retry (:retry opts)
   :queue (:queue opts)
   :jid (hex 12)
   :created_at (System/currentTimeMillis)
   :enqueued_at (System/currentTimeMillis)})

(defn enqueue
  ([worker-name args] (enqueue worker-name args default-opts))
  ([worker-name args opts]
   (let [payload (payload-map worker-name args (merge default-opts opts))
         payload-string (json/write-str payload)
         queue (:queue payload)
         queue-string (str "queue:" queue)]
     (wcar* (car/lpush queue-string payload-string)))))
