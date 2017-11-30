(ns parsing-with-spec.core
  (:require [clojure.spec.alpha :as s]))

;; TODO: fix indentation according to Clojure norms.

;; Recall the docstring of `s/cat`:

(-> #'s/cat meta :doc)

;; "Takes key+pred pairs, e.g.

;;   `(s/cat :e even? :o odd?)`

;;   Returns a regex op that matches (all) values in sequence,
;;   returning a map containing the keys of each pred and the
;;   corresponding value."

;; Question: what will this evaluate to?
(s/valid?
  (s/cat :e even? :o odd?)
  [0 1])

;; `[0 1]` is a sequence. The first number is 0 which is even, and the
;; second number is 1, which is odd. So, the expression evaluates to
;; `true`.

;; Question: what will this evalutate to?
(s/conform
  (s/cat :e even? :o odd?)
  [0 1])

;; Answer: according to the docstring of `s/cat`, using it to conform
;; yields structured data.
{:e 0, :o 1}

;; Now consider the docstring of `s/*`:
(-> #'s/* meta :doc)

;; "Returns a regex op that matches zero or more values matching
;; pred. Produces a vector of matches iff there is at least one match"

;; Question: what will this evalutate to?
(s/valid?
  (s/* #{\a})
  (seq "aaaaa"))

;; Answer: we use the set `#{\a}` as a predicate function. Recall that
;; `(#{\a} x)` is non-nil only when `x` is `\a`. Also recall that
;; using `seq` on a string yields a sequence of characters. Every
;; character in the string is `\a`, so the expression evaluates to
;; `true`.

;; What about this?
(s/conform
  (s/* (s/cat :e even? :o odd?))
  [0 1 2 3])
;; It evaluates to
[{:e 0, :o 1} {:e 2, :o 3}]

