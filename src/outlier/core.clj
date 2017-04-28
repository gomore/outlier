(ns outlier.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :refer [split-lines]]
            [outlier.analyzer :as analyzer])
  (:gen-class))

(defn freqs
  [filename]
  (-> filename
      slurp
      split-lines
      analyzer/frequency-map))

(defn perform-analysis
  [ref-file target-file]
  (analyzer/summary (freqs ref-file) (freqs target-file)))

(defn analysis-sort-fn
  [{:keys [index actual-count]}]
  (if (Double/isFinite index)
    [(/ 1 index) (/ 1 actual-count)]
    [0 (/ 1 actual-count)]))

(defn print-analysis-line
  [{:keys [element index actual-count expected-count]}]
  (println
   (if (Double/isFinite index)
     (format "%.2f" (double index))
     "   \u221e")
   actual-count
   (format "(vs %.2f)" (double expected-count))
   element))

(def instructions
  "
Usage: outlier [options] reference-file target-file

Loads a reference text file and a target file. The output is lines in
target-file that are over-represented in comparison to their frequency
in reference-file.

The output is ordered according to the index of the text string first,
and the number of occurences second. The index is the number of times
the string was present over how many times it was expected to be
present.

Each line of the output contains

 - the string
 - its index
 - number of occurences
 - number of expected occurences

Example output:

       \u221e 2 (vs 0.00) hej
       \u221e 1 (vs 0.00) verden
    0.60 1 (vs 1.67) world
    0.30 1 (vs 3.33) hello

Options:
")

(def cli-options
  [["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options arguments summary]} (parse-opts args cli-options)
        [ref-file target-file] arguments]
    (if (or (:help options) (not ref-file) (not target-file))
      (do
        (println instructions)
        (println summary)
        (println))
      (->> (perform-analysis ref-file target-file)
           (sort-by analysis-sort-fn)
           (map print-analysis-line)
           doall))))
