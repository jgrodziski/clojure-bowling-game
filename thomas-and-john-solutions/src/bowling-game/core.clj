(ns bowling_game.core)
​
(defn return-value [[a y]]
  (case y :x 10
          :s (- 10 a)
          y))
​
(defn type-detection-and-return-value [[[z x]b c]]
  (case x :x (+ 10 (return-value b) (return-value c))
          :s (+ (return-value [z x]) (return-value b))
          x))
​
(defn score [frames]
  (reduce + (map type-detection-and-return-value 
  	(partition 3 1
  		(partition 2 1 (flatten (cons 0 frames)))))))