angular.module("project").controller("uploadCtrl", ["$scope", "$http", "$location", 'restService', 'postService', 'spinnerService', function ($scope, $http, $location, restService, postService, spinnerService) {

    //Activate modals
    $('.modal').modal();
    $scope.loading = false;

    //Posts file to server
    $scope.submit = function () {

        //Show spinner
        spinnerService.show('booksSpinner');
        $scope.loading = true;

        var uploadUrl = 'http://localhost:8080/fileUpload';
        postService.post(uploadUrl, $scope.data.file)
            .then(function success(response) {
                swal('Done!', 'Your file was uploaded!', 'success')
                    .then(function () {
                        //Open files modal
                        $('#selectModal').modal('open');
                        $scope.getFiles();
                    });

            }, function error(response) {
                swal('Dang!', 'An error ocurred :(', 'error');

            }).finally(function () {

            //Close modal
            $('#uploadModal').modal('close');
            $scope.loading = false;
            spinnerService.hide('booksSpinner');

        });
    };

    //Gets all files from server
    $scope.getFiles = function () {
        restService.get("http://localhost:8080/", "archivos")
            .then(function (response) {
                $scope.files = response.data;
            });
    };

    $scope.getDB = function () {
        $scope.dbshow = !$scope.dbshow;
        restService.get("http://localhost:8080/", "dbs")
            .then(function (response) {
                $scope.dbs = response.data;
            });
    };

    $scope.getContent = function (db) {

        //Close modal and show spinner
        $('#selectModal').modal('close');
        spinnerService.show('processSpinner');
        $scope.processing = true;

        restService.get("http://localhost:8080/", "db?db=" + db)
            .then(function (response) {
                restService.uniques = response.data;
                restService.file = db;
                $location.path('/process');
            }).finally(function () {
                //Close modal
                spinnerService.hide('processSpinner');
            });
    };

    //Gets headers from the file choosed
    $scope.getHeaders = function (File) {
        $scope.File = File;
        restService.get("http://localhost:8080/", "headers?file=" + File)
            .then(function (response) {
                $scope.headers = response.data;
                //Close modal
                $('#selectModal').modal('close');

            });
    };

    //Adds or removes header by selection
    $scope.selectedHeaders = [];

    $scope.selectFunc = function (header) {
        var i = $scope.selectedHeaders.indexOf(header);

        // Is currently selected
        if (i > -1) {
            $scope.selectedHeaders.splice(i, 1);
        }

        // Is newly selected
        else {
            $scope.selectedHeaders.push(header);
        }
    };

    //Posts headers to server
    $scope.postHeaders = function () {

        //Show spinner //Hide title //Close modal
        spinnerService.show('processSpinner');
        $scope.processing = true;
        $('#filterModal').modal('close');

        var uploadUrl = 'http://localhost:8080/filterLog' + "?file=" + $scope.File;

        postService.postData(uploadUrl, $scope.selectedHeaders)
            .then(function success(response) {
                restService.uniques = response.data;
                restService.file = $scope.File;
                $location.path('/process');
            }, function error(response) {
                swal('Dang!', 'An error ocurred :(', 'error');
            }).finally(function () {
            //Close modal
            spinnerService.hide('processSpinner');
        });
    };

}]);