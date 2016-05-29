angular.module('antiplag', [])
.controller('cmpController', function($http, $scope, $window) {


    $http.get("/api/js/listPlagiarisms")
        .then(function(response) {
            $scope.plagiarisms = response.data;
        });

    $scope.compare = function(filename) {
        $http.get("/api/js/compare?filename="+filename)
            .then(function(response) {
                $('#codeRight').html(response.data.code.replace(/&quot12;/g, '&#147;'));
                $('#codeRight').each(function(i, block) {
                    hljs.highlightBlock(block);
                });
                $('#codeLeft').html(response.data.original);
                $('#codeLeft').each(function(i, block) {
                    hljs.highlightBlock(block);
                });;

            });

    }

});