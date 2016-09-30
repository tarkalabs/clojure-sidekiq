# clojure-sidekiq

Enqueue jobs to sidekiq from clojure. Closely mirrors the official ruby sidekiq interface and supports job scheduling.

Installation
------------

```clojure
[clojure-sidekiq "0.1.0"]
```

## Usage

```clojure
(ns my.app
  (:require [clojure-sidekiq.core :as sidekiq]))

(sidekiq/configure {:pool {} :spec {:host "127.0.0.1" :port 6379 :db 3}) ;; optional

;; enqueuing a job
(sidekiq/enqueue "WorkerName" ["argument", "array"])

;; enqueuing to a specific queue with retry enabled
(sidekiq/enqueue "WorkerName" ["argument", "array"] {:queue "queue-name" :retry true})

```

Redis configuration mirros the configuration supported by https://github.com/ptaoussanis/carmine. Checkout Carmine for more details on redis configuration.

## License

Copyright © 2016

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
