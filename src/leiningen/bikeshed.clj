(ns leiningen.bikeshed
  (:require [clojure.string :as str]
            [clojure.tools.cli :as cli]
            [leiningen.core.eval :as lein]
            [leiningen.core.project :as project]))

(defn help
  "Help text displayed from the command line"
  []
  "Bikesheds your project with totally arbitrary criteria.")

(defn bikeshed
  "Main function called from Leiningen"
  [project & args]
  (let [[opts args banner]
        (cli/cli
         args
         ["-H" "--help-me" "Show help"
          :flag true :default false]
         ["-v" "--verbose" "Display missing doc strings"
          :flag true :default false]
         ["-r" "--redefs-in-tests" "Allow with-redefs in test sources"
          :flag true :default false]
         ["-s" "--shadow-clojure-core" "Allow shadowing names from clojure.core"
          :flag true :default false]
         ["-p" "--skip-private-docs" "Allow private vars to not have docstrings"
          :flag true :default false]
         ["-m" "--max-line-length" "Max line length"
          :parse-fn #(Integer/parseInt %)]
         ["-x" "--exclude-profiles" "Comma-separated profile exclusions"
          :parse-fn #(mapv keyword (str/split % #","))])
        lein-opts (:bikeshed project)
        project (if-let [exclusions (seq (:exclude-profiles opts))]
                  (-> project
                      (project/unmerge-profiles exclusions)
                      (update-in [:profiles] #(apply dissoc % exclusions)))
                  project)
        merged-opts (merge lein-opts opts)]
    (if (:help-me opts)
      (println banner)
      (lein/eval-in-project
       (-> project
           (update-in [:dependencies]
                      conj
                      ['lein-bikeshed-sortable "0.4.2-SNAPSHOT"]))
       `(if (bikeshed.core/bikeshed '~project ~merged-opts)
          (System/exit -1)
          (System/exit 0))
       '(require 'bikeshed.core)))))
