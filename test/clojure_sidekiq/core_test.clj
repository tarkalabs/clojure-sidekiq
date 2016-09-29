(ns clojure-sidekiq.core-test
  (:require [clojure.test :refer :all]
            [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json]
            [clojure-sidekiq.core :refer :all]))

(defn payload-attributes [payload-string]
  (let [payload (json/read-str payload-string)]
    (select-keys payload ["class" "args" "retry"])))

(deftest enqueue-defaults
  (wcar* (car/flushall))
  (let [worker-name "TestWorker"
        args [1 2 3]]
    (enqueue worker-name args)
    (is (= (payload-attributes (wcar* (car/lpop "queue:default"))) {"class" worker-name "args" args "retry" false})))
    (is (= (wcar* (car/smembers "queues") ["default"]))))

(deftest enqueue-non-defaults
  (wcar* (car/flushall))
  (let [worker-name "AnotherWorker"
        args ["some-string-arg"]]
    (enqueue worker-name args {:queue "my-queue" :retry true})
    (is (= (payload-attributes (wcar* (car/lpop "queue:my-queue"))) {"class" worker-name "args" args "retry" true})))
    (is (= (wcar* (car/smembers "queues") ["my-queue"]))))
