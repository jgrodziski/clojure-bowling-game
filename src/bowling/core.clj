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
       (= (get frame 0) 10)))

(defn spare? [frame]
  (and (not (empty? frame))
       (not= (get frame 0) 10)
       (= (count frame) 2)
       (= (+ (get frame 0) (get frame 1)) 10)))

(defn- calculate-bonus [frame next-rolls]
  (cond (or (nil? next-rolls)
            (empty? next-rolls)) (if (strike? frame)
                                   (if (= (count frame) 2)
                                     (second frame)
                                     0)
                                   0);tenth frame case
        (spare? frame) (first next-rolls)
        (strike? frame) (+ (first next-rolls) (second next-rolls))
        :else 0))

(defn spare-and-strike->integer
  "Replace the keywords :X and :/ by their value, respectively 10 and the value needed to go to ten from the first ball knocked-down pins"
  [frame]
  (cond
    (= (count frame) 3) (conj (spare-and-strike->integer (take 2 frame)) (if (or (= (last frame) :X) (= (last frame) :x)) 10 (last frame)))   ;tenth frame case
    (and  (or (= (first frame) :X)
              (= (first frame) :x))
          (or (= (second frame) :X)
              (= (second frame) :x))) [10 10]
    (or (= (first frame) :X)
        (= (first frame) :x)) [10]
    (= (second frame) :/)     [(first frame) (- 10 (first frame))]
    ;(not (and (int? (first frame)) (int? (second frame)))) (throw (Exception.))
    :default frame))

(defn score
  "return a vector with first element the intermediate scores and second the total score"
  [game]
  (let [game (vec (map spare-and-strike->integer game))]
    (println "Game" game)
    (loop [frames-to-process game
           i                 1
           intermediate      []
           total             0]
      (if (empty? frames-to-process) [intermediate total]
          (let [frame (first frames-to-process)
                score (reduce + frame)
                bonus (calculate-bonus frame (flatten (subvec game i)))]
            (println frame score bonus intermediate total)
            (recur
             (rest frames-to-process)
             (inc i)
             (conj intermediate (if (or (> score 0) (> bonus 0)) (+ total score bonus) 0))
             ;(flatten (subvec game (.indexOf game frame)))
             (+ total score bonus)))))))
