(ns outlier.analyzer)

(defn frequency-map
  [coll]
  (reduce (fn [m elm] (assoc m elm (inc (get m elm 0))))
          {}
          coll))

(defn expected-count
  [sample-size reference-count reference-total]
  (let [fraction (when reference-count
                   (/ reference-count reference-total))]
    (if fraction
      (* sample-size fraction)
      0)))

(defn safe-div
  [dividend divisor]
  (if (= divisor 0)
    (cond
     (< dividend 0) Double/NEGATIVE_INFINITY
     (> dividend 0) Double/POSITIVE_INFINITY
     :else Double/NaN)
    (/ dividend divisor)))

(defn summary
  [reference-frequencies actual-frequencies]
  (let [reference-total (apply + (vals reference-frequencies))
        actual-total (apply + (vals actual-frequencies))]
    (for [[elm actual-count] actual-frequencies]
      (let [reference-count (get reference-frequencies elm)
            expected-count (expected-count actual-total reference-count reference-total)]
        {:element elm,
         :actual-count actual-count
         :reference-count reference-count
         :expected-count expected-count
         :index (safe-div actual-count expected-count)}))))
