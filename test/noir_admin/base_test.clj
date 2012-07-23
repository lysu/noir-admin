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

(deftest should-build-admin
  (testing "should build admin"
    (let [expect-name "name"
          url "abc.htm"
          v nil
          amin (admin expect-name url v)]
      (is (= nil amin)))))

(deftest should-add-view-to-menu
  (testing "should add view to menu"
    (let [view-1 (view "test-name" "test-category" "endpoint" "url://sf" "/abc")
          view-2 (view "test-name" "test-category" "endpoint" "url://sf" "/abc")
          admin (admin "test" "t.cn" view-1)]
      (is (= (+ 1 (count (:views admin))) (count (:views (add-view admin view-2))))))))


(deftest should-add-to-admin-app
  (testing "should add menu to app"
    (let [view-1 (view "test-name-1" "test-category" "endpoint" "url://sf" "/abc1")
          view-2 (view "test-name-2" "test-category-2" "endpoint" "url://sf" "/abc2")
          admin (admin "test" "t.cn" view-1)]
      (is (= 2 (count (:menu-categories (add-view admin view-2))))))))