(ns noir-admin.base)

(defn view
  "build view"
  [name category endpoint url static-folder]
  {:name name
   :category category
   :endpoint endpoint
   :url url
   :static-folder static-folder})

(defn admin
  "build admin struct"
  [name url index-view]
  {:name (or name "Admin")
   :url (or url "/admin")
   :index-view (or index-view nil)
   :views [index-view]
   :menu []
   :menu-categories {}})

(defn add-view-to-menu
  "add view to menu list"
  [admin view]
  (if (:category view)
    ))

(defn add-view 
  "add view to exist admin"
  [admin view]
  (update-in admin [:views] conj view))

