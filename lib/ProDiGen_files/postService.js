angular.module("project").service("postService", ["$http", function($http){

    this.post = function(uploadUrl, data){

        var config = {
            headers : {
                'Content-Type': undefined
            },
            transformRequest: angular.identity
        };

        var fd = new FormData();
        fd.append('file', data);

        return $http.post(uploadUrl, fd, config)
    };

    this.postData = function(uploadUrl, data){

        console.log(data);

        //var parameters = JSON.stringify({columns:data});
        //var params = {'columns': data};

        //var greet = {"content": "Yago", "age": "21"};

        var config = {
            headers : {
                'Content-Type': undefined
            },
            transformRequest: angular.identity
        };

        var fd = new FormData();
        fd.append('data', data);

        return $http.post(uploadUrl, fd, config)
    };

    this.postData2 = function(uploadUrl, data){

        console.log(data);

        //var parameters = JSON.stringify({columns:data});
        //var params = {'columns': data};

        //var greet = {"content": "Yago", "age": "21"};

        var config = {
            headers : {
                'Content-Type': undefined
            },
            transformRequest: angular.identity
        };

        var fd = new FormData();
        fd.append('hierarchies', data);

        return $http.post(uploadUrl, fd, config)
    };



}]);