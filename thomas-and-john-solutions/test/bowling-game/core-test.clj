(ns bowling_game.core-test
  (:require [midje.sweet :refer :all]
            [bowling_game.core :refer :all]))
​
(facts "Calcul du score"
       (fact "zero à chaque coup"
             (score (repeat 10 [0 0])) => 0)
       (fact "six points au total"
             (score [[1 1] [2 0] [2 0] (repeat 7 [0 0])]) => 6)
       (fact "douze strikes !"
             (score (repeat 12 [:x])) => 300)
       (fact "une partie complète"
             (score [[2 :s] [3 2] [:x] [:x] [7 0] [5 :s] [8 :s] [:x] [0 0] [:x :x :x]]) => 147))