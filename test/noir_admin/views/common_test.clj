(ns noir-admin.views.common-test
  (:use clojure.test
    noir-admin.views.common))

(deftest should-show-active-menu-item-test
  (testing "show menu item"
    (let [data {:active? true :name "test" :url "t.html"}
    		 expect "<li class=\"active\"><a href=\"t.html\">test</a></li>"]
    		 (is (= expect (menu-item data))))))

(deftest should-show-not-active-menu-item-test
  (testing "show menu item"
    (let [data {:active? false :name "test" :url "t.html"}
    		 expect "<li><a href=\"t.html\">test</a></li>"]
    		 (is (= expect (menu-item data))))))

(deftest should-show-nav-item-with-menu-test
  (testing "show nav item"
	(let [data {:category? true :name "category-test" 
				:children [
					{:active? false :name "test1" :url "t.html"} 
					{:active? false :name "test2" :url "t.html"}]}
    		 expect (str "<li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">"
    		 				"category-test<b class=\"caret\"></b></a><ul class=\"dropdown-menu\">"
    		 				"<li><a href=\"t.html\">test1</a></li>"
    		 				"<li><a href=\"t.html\">test2</a></li></ul>"
    		 			"</li>")]
    		 (is (= expect (nav-item data))))))

(deftest should-direct-nav-item-test
  (testing "show nav item"
	(let [data {:category? false :accessiable? true :name "test" :url "t.html"}
    		 expect "<li><a href=\"t.html\">test</a></li>"]
    		 (is (= expect (nav-item data))))))

(deftest should-show-full-nav-test
  (testing "show nav all")
	(let [data [{:category? false :accessiable? true :name "test" :url "t.html"}
				{:category? true :name "category-test" 
				 :children [
					{:active? false :name "test1" :url "t.html"} 
					{:active? false :name "test2" :url "t.html"}]}]
		   expect (str "<div class=\"navbar navbar-fixed-top\"><div class=\"navbar-inner\"><div class=\"container\">" 
		   			"<span class=\"brand\">admin-test</span>"
		   			"<ul class=\"nav\">"
		   				"<li><a href=\"t.html\">test</a></li>"
		   				"<li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">"
		   					"category-test<b class=\"caret\"></b></a>"
		   				"<ul class=\"dropdown-menu\">"
		   					"<li><a href=\"t.html\">test1</a></li>"
		   					"<li><a href=\"t.html\">test2</a></li>"
		   				"</ul></li>"
		   			"</ul></div></div></div>")]
		(is (= expect (nav "admin-test" data)))))

(deftest should-show-notice-bar-test
  (testing "show notice bar")
	(let [data [{:category "error" :message "msg"} {:message "msg2"}]
		   expect "<div class=\"alert alert-error\"><a class=\"close\" data-dismiss=\"alert\" href=\"#\">x</a>msg</div><div class=\"alert\"><a class=\"close\" data-dismiss=\"alert\" href=\"#\">x</a>msg2</div>"]
		(is (= expect (notice-bar data)))))