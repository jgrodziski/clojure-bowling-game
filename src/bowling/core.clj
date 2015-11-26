(ns bowling.core)

(def game [[] [] [] [] [] [] [] [] [] []])

;; first build a function that take a predicate and return all the
;; element's index
(defn indices [pred coll]
     (keep-indexed #(when (pred %2) %1) coll))

(defn roll [game pins-down]
  (let [first-free-index (first (indices #(and (< (count %) 2) (not= (first %) 10)) game))
        frame (get game first-free-index)]
    (if (not (<= 0 pins-down 10)) (throw (RuntimeException. (str  "pins down between  0 and 10, not " pins-down))))
    (assoc-in game [first-free-index] (conj frame pins-down))))

(defn prepare-rolls [game]
  (let [game-with-10-0 (mapcat (fn [n] (if (= n 10) [10 0] [n])) game)]
    (if (odd? (count game-with-10-0))
      (assoc game-with-10-0 0)
      game-with-10-0)))


(defn strike? [frame]
  (and (not (empty? frame))
       (= (frame 0) 10)))

(defn spare? [frame]
  (and (not (empty? frame))
       (not= (frame 0) 10)
       (= (count frame) 2)
       (= (+ (frame 0) (frame 1)) 10)))

(defn- calculate-bonus [frame next-rolls]
  (cond (or (nil? next-rolls)
            (empty? next-rolls))
        (if (strike? frame)
          (if (= (count frame) 2) (second frame) 0) 0);tenth frame case
        (spare? frame)
        (first next-rolls)
        (strike? frame)
        (+ (first next-rolls) (second next-rolls))
        :else 0))

(defn score [game]
  (loop [frames-to-process game
         next-rolls nil
         acc-score 0]
    (if (empty? frames-to-process) acc-score
        (let [frame (last frames-to-process)
              score (reduce + frame)
              bonus (calculate-bonus frame next-rolls)]
          ;(println frame score bonus previous-rolls)
          (recur
           (butlast frames-to-process)
           (flatten (subvec game (.indexOf game frame)))
           (+ acc-score score bonus))))))
