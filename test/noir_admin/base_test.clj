(ns noir-admin.base-test
  (:use clojure.test
        noir-admin.base))

(deftest should-build-view-struct
  (testing "build view struct"
    (let [view (view "test-name" "test-category" "endpoint" "url://sf" "/abc")]
      (is (= "endpoint" (:endpoint view))))))

(deftest should-view-accessible-default
  (testing "view accessible default"
    (is (is-view-accessible?
         (view "test-name" "test-category" "endpoint" "url://sf" "/abc")))))

(deftest should-build-menu-view-attached
  (testing "menu with view attached"
    (let [view (view "test-name" "test-category" "endpoint" "url://sf" "/abc")
          expect-name "test-menu"
          menu (menu-item expect-name view)]
      (is (= expect-name (:name menu)))
      (is (= false (nil? (:view menu)))))))

(deftest should-add-menu-child
  (testing "add menu child item and view"
    (let [view-p (view "test-name" "test-category" "endpoint" "url://sf" "/abc")
          parent-name "test-p-name"
          parent (menu-item parent-name view-p)
          prev-count (-> parent :children count)
          prev-count-url (-> parent :children-urls count)
          view-c (view "test-name-child" "test-category" "endpoint" "url://sf" "/abc")
          added (add-menu-child parent view-c)
          after-count (-> added :children count)
          after-count-url (-> added :children-urls count)]
      (is (= (+ prev-count 1) after-count))
      (is (= (+ prev-count-url 1) after-count-url)))))

(deftest should-get-menu-url
  (testing "get url in menu"
    (let [menu (menu-item "menu-name" (view "view1" "test category" "endpoint" "url" "/ab"))]
      (is (= "endpoint/" (get-menu-url menu))))))


(deftest should-menu-active
  (testing "is menu actived"
    (let [menu (menu-item "menu-name" (view "view1" "test category" "endpoint" "url" "/ab"))]
      (is (is-menu-active? menu)))))


(deftest should-judge-menu-is-category
  (testing "is menu is category"
    (let [menu (menu-item "menu-name" nil)]
      (is (= true (is-menu-category? menu))))))

(deftest should-get-menu-child-views
  (testing "add menu child item and view"
    (let [view-p (view "test-name" "test-category" "endpoint" "url://sf" "/abc")
          parent-name "test-p-name"
          parent (menu-item parent-name view-p)
          view-c (view "test-name-child" "test-category" "endpoint" "url://sf" "/abc")
          added (add-menu-child parent view-c)]
      (is (= 1 (count (get-menu-children added)))))))
