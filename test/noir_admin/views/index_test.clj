(ns noir-admin.views.index-test
  (:use [clojure.test]
        [noir-admin.views.index]
        [noir.util.test]))

(deftest route-test
  (-> (send-request "/admin/" {})
      (has-status 200)))