(ns clojure-sidekiq.core-test
  (:require [clojure.test :refer :all]
            [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json]
            [clojure-sidekiq.core :refer :all]))


(defn payload-attributes [payload-string]
  (let [payload (json/read-str payload-string)]
    (select-keys payload ["class" "args"])))

(deftest enqueue-defaults
  (let [worker-name "TestWorker"
        args [1 2 3]]
    (enqueue "TestWorker" [1 2 3])
    (is (= (payload-attributes (wcar* (car/lpop "queue:default"))) {"class" worker-name"args" args}))))
