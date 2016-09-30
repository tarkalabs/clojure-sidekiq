(defproject clojure-sidekiq "0.1.0"
  :description "Push to sidekiq queue from clojure."
  :url "http://github.com/tarkalabs/clojure-sidekiq"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.taoensso/carmine "2.14.0"]
                 [org.clojure/data.json "0.2.6"]
                 [secure-rand "0.1"]])
