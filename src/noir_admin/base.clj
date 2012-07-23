(ns noir-admin.base)

(defn view
  "build view"
  [name category endpoint url static-folder]
  {:name name
   :category category
   :endpoint endpoint
   :url url
   :static-folder static-folder})

(defn is-view-accessible?
  "check view accessible"
  [view]
  true)

(defn menu-item
  "build-menu-item"
  [name view]
  {:name name :view view
   :children []
   :children-urls #{}})

(defn add-menu-child
  "add menu child"
  [menu view]
  (-> menu
      (update-in [:children] conj view)
      (update-in [:children-urls] conj (:url view))))

(defn get-menu-url
  "get url attached in menu"
  [menu]
  (when-not (:view menu)
    nil)
  (str (get-in menu [:view :endpoint]) "/"))

(defn is-menu-active?
  "is a active menu"
  [menu]
  (when-not (:view menu)
    nil)
  (is-view-accessible? (:view menu)))

(defn is-menu-category?
  "is a category menu"
  [menu]
  (when-not (:view menu)
    nil)
  (nil? (:view menu)))

(defn get-menu-children
  "get menu children"
  [menu]
  (filter #(is-view-accessible? %) (:children menu)))

(defn add-view-to-menu
  "add view to menu list"
  [admin view]
  (if-let [category (:category view)]
    (if-let [exist-category
             ((:menu-categories admin) category)]
      (update-in admin [:menu-categories category]
                 add-menu-child exist-category)
      (let [new-category (menu-item category nil)]
        (-> admin
            (assoc-in [:menu-categories category] new-category)
            (update-in [:menu] conj new-category)
            (update-in [:menu-categories category]
                       add-menu-child new-category))))))

(defn add-view 
  "add view to exist admin"
  [admin view]
  (let [view-added-admin (update-in admin [:views] conj view)]
    (add-view-to-menu view-added-admin view)))


(defn admin
  "build admin struct"
  [name url index-view]
  (let [new-admin {:name (or name "Admin")
                   :url (or url "/admin")
                   :index-view (or index-view nil)
                   :views [index-view]
                   :menu []
                   :menu-categories {}}]
    (add-view new-admin index-view)))
