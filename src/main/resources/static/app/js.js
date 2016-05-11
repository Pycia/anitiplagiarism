angular.module('antiplag', [])
.controller('jsController', function($http, $scope, $window) {
  $scope.js = {
    library: {
      files: []
    }
  };
  $http.get("/api/js/library/list")
      .then(function(response) {
          $scope.js.library.files=(response.data);
      });


  $scope.addFile = function(){
        var f = document.getElementById('addFile').files[0],
            r = new FileReader();
        r.onloadend = function(e){
            $http.post("/api/js/library/upload", {
              filename: f.name,
              content: e.target.result
            }).then(function(response) {
                $window.location.reload();
            })
        }
        r.readAsBinaryString(f);
      }
});