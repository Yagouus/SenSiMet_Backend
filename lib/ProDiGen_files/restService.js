angular.module("project").service("restService", ["$http", function($http){

    this.get = function(url, service){
        return $http.get(url + service);
    };

    this.uniques = '';
    this.file = '';

}]);