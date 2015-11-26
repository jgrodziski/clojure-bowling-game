(ns bowling.core-cljtest
  (:require [clojure.test :refer :all]))

(def only0-game [[0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0] [0 0]])

(def only1-game [[1 1] [1 1] [1 1] [1 1] [1 1] [1 1] [1 1] [1 1] [1 1] [1 1]])

(defn score [game]
  (reduce + (flatten game)))

(defn spare? [frame]
  (and (= (+ (get frame 0) (get frame 1)) 10)
       (not= (get frame 0) 10)
       (= (count frame) 2)))

(deftest spare
  (is (true? (spare? [8 2])))
  (is (false? (spare? [1 1 1])))
  (is (false? (spare? [1 8 1])))
  (is (false? (spare? [10 0])))
  (is (true? (spare? [0 10])))
  (is (false? (spare? [0 0])))
  (is (false? (spare? [5 2]))))

(defn spare-bonus [frame next-frame]
  (if (spare? frame)
    (get next-frame 0)
    0))

(deftest spare-bonus-test
  (is (= (spare-bonus [3 3] [2 0]) 0))
  (is (= (spare-bonus [5 5] [3 0]) 3))
  (is (= (spare-bonus [7 3] [0 0]) 0))
  (is (= (spare-bonus [10 0] [1 0]) 0)))

(defn strike? [frame]
  (= (get frame 0) 10))

(deftest strike
  (is (true? (strike? [10 0]))))

(deftest only0
  (is (= (score only0-game) 0)))

(deftest only1
  (is (= (score only1-game) 20)))
