(ns noir-admin.views.common
  (:use [noir.core :only [defpartial]] 
        [hiccup.page]
        [noir-admin.views.utils]))

(def includes {:jquery (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js")
                 :chosen-js (include-js "/chosen/chosen.jquery.min.js")
                 :bootstrap-js (include-js "/bootstrap/js/bootstrap.js")
                 :bootstrap-less (include-less "/bootstrap/css/bootstrap.less")
                 :less-js (include-js "/js/less-1.3.0.min.js")
                 :admin-css (include-css "/css/admin.css")})

(defpartial includes-res [& incls]
  (map #(get includes %) incls))

(defpartial menu-item [item]
  [:li (if (:active? item) {:class "active"} {}) 
    [:a {:href (:url item)} (:name item)]])

(defpartial nav-item [item]
  (if (:category? item)
    (if-let [children (:children item)]
      [:li (if (:active? item) {:class "active dropdown"} {:class "dropdown"})
        [:a {:class "dropdown-toggle" :data-toggle "dropdown" :href "#"} (:name item) [:b.caret]]
        [:ul.dropdown-menu
          (map menu-item children)]])
    (if (:accessiable? item) 
      [:li (if (:active? item) {:class "active"} {}) 
        [:a {:href (:url item)} (:name item)]])))

(defpartial nav [app-name menu]
  [:div.navbar.navbar-fixed-top
     [:div.navbar-inner
      [:div.container
       [:span.brand app-name]
       [:ul.nav
        (map nav-item menu)]]]])

(defpartial notice-item [item]
  [:div (if (= "error" (:category item)) {:class "alert alert-error"} {:class "alert"}) 
      [:a {:href "#" :class "close" :data-dismiss "alert"} "x"] (:message item)])

(defpartial notice-bar [notices]
  (if notices
    (map notice-item notices)))

(defpartial layout [title nav notice-bar content]
  (html5
   [:head
    [:title title]
    (includes-res :bootstrap-less :less-js :admin-css)]
   [:body
    nav
    notice-bar
    [:div.container content]
    (includes-res :jquery :chosen-js :bootstrap-js)]))