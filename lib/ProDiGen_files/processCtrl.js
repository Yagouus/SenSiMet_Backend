angular.module("project").controller("processCtrl", ["$scope", "$http", '$filter', 'restService', 'postService', 'spinnerService', function ($scope, $http, $filter, restService, postService, spinnerService) {

    $scope.columns = restService.uniques;
    $scope.file = restService.file;
    $scope.increment = 0;

    //Adds or removes header by selection
    $scope.selectedHeaders = [];
    $scope.createdFilters = [];

    //Adds or removes a value to a hierarchy
    $scope.selectFunc = function (key, header) {
        var i = $scope.selectedHeaders.indexOf(key + ":" + header);

        // Is currently selected
        if (i > -1) {
            $scope.selectedHeaders.splice(i, 1);
        }

        // Is newly selected
        else {
            $scope.selectedHeaders.push(key + ":" + header);
        }

        //console.log($scope.selectedHeaders);
    };

    //Adds a hierarchy to the array
    $scope.addHierarchy = function () {
        $scope.processShow = true;
        $scope.selectedHeaders = [];
        $scope.currentHierarchy = $scope.increment;

        /*var e = false;

         for (var i = 0; i < $scope.createdFilters.length; i++) {
         if ($scope.createdFilters[i].id === $scope.increment) {
         e = true;
         }
         }

         if (e) {
         $scope.currentHierarchy = ++$scope.increment;
         } else {
         $scope.currentHierarchy = $scope.increment;
         }*/


        //$scope.createdFilters.push({"id": $scope.createdFilters.length, "headers": $scope.selectedHeaders});
        //$scope.selectedHeaders = [];
    };

    //Save the recently created hierarchy
    $scope.saveHierarchy = function () {

        //Find hierarchy
        var e = false;
        var index;
        for (var i = 0; i < $scope.createdFilters.length; i++) {
            if ($scope.createdFilters[i].id === $scope.currentHierarchy) {
                e = true;
                index = i;
            }
        }

        //If hierarchy exists, override
        if (e) {
            console.log("Override");
            $scope.createdFilters[index] = {
                "id": $scope.currentHierarchy,
                "headers": $scope.selectedHeaders
            };
            //If not, insert
        } else {
            $scope.createdFilters.push({
                "id": $scope.currentHierarchy,
                "headers": $scope.selectedHeaders
            });
        }

        $scope.selectedHeaders = [];
        $scope.processShow = false;
        $scope.increment++;
    };

    //Get a hierarchy with its id
    $scope.getHierarchy = function (id) {
        $scope.processShow = true;
        //$scope.selectedHeaders = $scope.createdFilters[id].headers;
        for (var i = 0; i < $scope.createdFilters.length; i++) {
            if ($scope.createdFilters[i].id === id) {
                $scope.selectedHeaders = $scope.createdFilters[i].headers;
            }
        }

        $scope.currentHierarchy = id;
    };

    //Checks if certain value is selected
    $scope.selectionContains = function (key, value) {
        return $scope.selectedHeaders.indexOf(key + ":" + value);
    };

    //Resets data selected for a hierarchy
    $scope.resetData = function () {
        $scope.selectedHeaders = [];
    };

    //Deletes created hierarchy
    $scope.deleteHierarchy = function () {
        $scope.processShow = false;

        for (var i = 0; i < $scope.createdFilters.length; i++) {
            if ($scope.createdFilters[i].id === $scope.currentHierarchy) {
                var p = i;
            }
        }

        $scope.createdFilters.splice(p, 1);


    };

    //Posts hierarchies to server
    $scope.postHierarchies = function () {

        var hierarchies = $scope.createdFilters[0].headers;

        /*for (var i = 0; i < $scope.createdFilters.length; i++) {
           hierarchies.push($scope.createdFilters[i].headers);
        }*/

        //Show spinner //Hide title //Close modal
        spinnerService.show('processSpinner');
        $scope.processing = true;
        //$('#filterModal').modal('close');

        var uploadUrl = 'http://localhost:8080/hierarchy' + "?file=" + $scope.file;

        postService.postData(uploadUrl, hierarchies)
            .then(function success(response) {
                restService.uniques = response.data;
                restService.file = $scope.File;
                //$location.path('/process');
            }, function error(response) {
                swal('Dang!', 'An error ocurred :(', 'error');
            }).finally(function () {
            //Close modal
            spinnerService.hide('processSpinner');
        });
    };


}]);




