angular.module('antiplag', [])
.controller('cmpController', function($http, $scope, $window) {
    $http.get("/api/js/pastedContent")
        .then(function(response) {
            $scope.pastedCode=(response.data.code);
            setTimeout(function() {
                $('pre code').each(function(i, block) {
                    hljs.highlightBlock(block);
                });
            }, 500);
        });


    $http.get("/api/js/listPlagiarisms")
        .then(function(response) {
            $scope.plagiarisms = response.data;
        });

    $scope.compare = function(filename) {
        $http.get("/api/js/compare?filename="+filename)
    }

});