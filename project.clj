(defproject outlier "0.1.0"
  :description "A command-line tool for detecting outliers in text files"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]]
  :main outlier.core
  :plugins [[lein-bin "0.3.4"]]
  :bin {:name "outlier"
        :bin-path "~/bin"
        :bootclasspath true}
  :profiles {:uberjar {:aot :all}})
