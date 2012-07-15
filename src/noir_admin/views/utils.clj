(ns noir-admin.views.utils
  (:use [hiccup.core]))

(defn include-less
  "creates a less stylesheet link"
  [href]
  [:link {:rel "stylesheet/less" :href href}])

(defn inline-js
  "includes inline js"
  [content]
  [:script {:type "text/javascript"} content])

(defn inline-css
  "includes inline css"
  [content]
  [:style {:type "text/css"} content])