//Modules
angular.module("project", ['ngRoute', 'angularSpinners']);

//Sections
angular.module("project").config(["$routeProvider", function ($routeProvider) {
//Routing
    $routeProvider
        .when("/upload", {
            controller: "uploadCtrl",
            templateUrl: "views/upload.html"
        })
        .when("/process", {
            controller: "processCtrl",
            templateUrl: "views/logCreation.html"
        })
        .otherwise("/upload");
}]);