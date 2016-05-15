angular.module('antiplag', [])
.controller('cmpController', function($http, $scope, $window) {
    $http.get("/api/js/pastedContent")
        .then(function(response) {
            $('#codeLeft').html(response.data.code);
            $('#codeLeft').each(function(i, block) {
                hljs.highlightBlock(block);
            });
        });


    $http.get("/api/js/listPlagiarisms")
        .then(function(response) {
            $scope.plagiarisms = response.data;
        });

    $scope.compare = function(filename) {
        $http.get("/api/js/compare?filename="+filename)
            .then(function(response) {
                $('#codeRight').html(response.data.code);
                $('#codeRight').each(function(i, block) {
                    hljs.highlightBlock(block);
                });
            });
    }

});