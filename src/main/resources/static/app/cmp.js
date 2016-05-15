angular.module('antiplag', [])
.controller('cmpController', function($http, $scope, $window) {
  $http.get("/api/js/pastedContent")
      .then(function(response) {
          $scope.pastedCode=(response.data.code);
          hljs.initHighlightingOnLoad();
      });

});