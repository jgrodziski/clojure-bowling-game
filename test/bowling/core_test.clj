(ns bowling.core-test
  "The game consists of **10 frames** as shown above. In each frame the player has
   two opportunities to knock down 10 pins. The score for the frame is the total
   number of pins knocked down, plus bonuses for strikes and spares.

   A **spare** is when the player knocks down all 10 pins in two tries. The **bonus** for that frame is the number of pins knocked down by the next roll. ‘/’ represents a spare in the score sheet.
   A **strike** is when the player knocks down all 10 pins on his first try. The **bonus** for that frame is the value of the next two balls rolled. ‘X’ represents a strike in the score sheet.

   In the **tenth frame** a player who rolls a spare or strike is allowed to roll the **extra balls** to complete the frame (so 3 balls can be rolled in tenth frame). "
  (:require [clojure.test :refer :all]
            [bowling.core :refer :all]
            [testit.core :refer :all]))

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

(def game1 {:title      "MCBA ALL - TIME HIGH TEAM GAME & SERIES 794 855 705=2354"
            :location   "STRATHMORE LANES"
            :date #inst "2003-09-25"
            :games  [{"Jack Jackson"   [[:X  ] [:X] [:X] [:X] [:X] [:X] [:X] [:X] [9 :/] [9 :/ :X]]
                      "Scott Reid"     [[9 :/] [:X] [:X] [:X] [:X] [:X] [:X] [:X] [:X  ] [:X :X 7]]
                      "Joe Krajkovich" [[:X  ] [:X] [:X] [:X] [:X] [:X] [:X] [:X] [:X  ] [:X :X :X]]}]})

(deftest score-test
  (testing "Check our bowling game score calculator"
    (fact "only 0 pins down"                        (score (only 0))       => [[0 0 0 0 0 0 0 0 0 0] 0])
      (fact "only 1 pins down"                      (score (only 1))       => [[2 4 6 8 10 12 14 16 18 20] 20])
      (fact "only 1 pins down and 1 spare"          (score spare-ex)       => [[2 4 6 8 19 21 23 25 27 29] 29])
      (fact "only 1 pins down and 1 strike"         (score strike-ex)      => [[2 4 6 8 20 22 24 26 28 30] 30])
      (fact "all strike "                           (score all-strike)     => [[30 60 90 120 150 180 210 240 270 300] 300])
      (fact "a score while in the middle of a game" (score in-middle-game) => [[7 11 17 27 0 0 0 0 0 0] 27])
      (fact "Score Jack Jackson"   (score (get-in game1 [:games 0 "Jack Jackson"]))   => [[30 60 90 120 150 180 209 229 248 268] 268])
      (fact "Score Scott Reid"     (score (get-in game1 [:games 0 "Scott Reid"]))     => [[20 50 80 110 140 170 200 230 260 287] 287])
      (fact "Score Joe Krajkovich" (score (get-in game1 [:games 0 "Joe Krajkovich"])) => [[30 60 90 120 150 180 210 240 270 300] 300])))
