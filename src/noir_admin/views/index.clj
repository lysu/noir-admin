(ns noir-admin.views.index
  (:use [noir.core :only [defpage]])
  (:require [noir-admin.views.common :as common] 
             [noir-admin.config :as config]))

(def fakeMenuCfg 
  [{:category? false :accessiable? true :name "test" :url "t.html"}
        {:category? true :name "category-test" 
         :children [
          {:active? false :name "test1" :url "t.html"} 
          {:active? false :name "test2" :url "t.html"}]}])

(defpage "/admin/" []
  (common/layout config/SITE-TITLE 
    (common/nav config/SITE-TITLE fakeMenuCfg)
    (common/notice-bar [])
    "Welcome"))