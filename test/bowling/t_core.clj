(ns bowling.t-core
  (:use midje.sweet)
  (:use [bowling.core]))

(defn only [pins-down]
  (into [] (repeat 10 [pins-down pins-down])))

(def spare-ex [[1 1] [1 1] [1 1] [1 1] [8 2] [1 1] [1 1] [1 1] [1 1] [1 1]])

(def strike-ex [[1 1] [1 1] [1 1] [1 1] [10] [1 1] [1 1] [1 1] [1 1] [1 1]])

(def strike-a-la-fin [[2 3] [4 5] [10] [3 4] [5 5] [6 6] [7 7] [8 8] [9 9] [8 2 10]])

(def all-strike [[10] [10] [10] [10] [10] [10] [10] [10] [10] [10 10 10]])

(def in-middle-game [[3 4] [1 3] [6 0] [10] [] [] [] [] [] []])

(defn game-generator []
  (vec (for [frames (range 10)]
         (let [shot1 (int (rand 11))
               shot2 (if (= shot1 10) 0 (int (rand (- 11 shot1))))]
           [shot1 shot2]))))

(facts "Check our bowling game score calculator"
 (fact "only 0 pins down"                      (score (only 0)) => 0)
 (fact "only 1 pins down"                      (score (only 1)) => 20)
 (fact "only 1 pins down and 1 spare"          (score spare-ex) => 29)
 (fact "only 1 pins down and 1 strike"         (score strike-ex) => 30)
 (fact "all strike "                          (score all-strike) => 300)
 (fact "a score while in the middle of a game" (score in-middle-game) => 27))
