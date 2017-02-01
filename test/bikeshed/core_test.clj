(ns bikeshed.core-test
  (:use clojure.test
        bikeshed.core))

(deftest a-test	
  (testing "FIXME, I fail, and I have trailing whitespace!"   
    (is (= 0 1))))


(def this-thing-is-over-eighty-characters-long "yep, it certainly is over eighty characters long")

(def should-not-complain-about-this-long-line "yep, it certainly is over eighty characters long... but has an exception comment") ;bikeshed:  allow-long

(def a "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡")

;; lot's of extra newlines:



















