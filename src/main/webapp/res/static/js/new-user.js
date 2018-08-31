angular.module('newUserApp', ['common', 'spring-security-csrf-token-interceptor', 'editableTableWidgets'])
    .controller('NewUserCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.createUser = function () {
            $scope.vm.submitted = true;

            if ($scope.form.$invalid) {return;}

            var postData = {
                login: $scope.vm.username,
                password: $scope.vm.password,
                mail: $scope.vm.email
            };

            console.log($scope.vm.username);
            $http({
                method: 'POST',
                url: '/user',
                data: postData,
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "text/plain"
                }
            })
                .then(function (response) {
                    if (response.status == 200) {
                        $scope.login($scope.vm.userName, $scope.vm.password);
                    }
                    else {
                        $scope.vm.errorMessages = [];
                        $scope.vm.errorMessages.push({description: response.data});
                        console.log("failed user creation: " + response.data);
                        alert("Error. Failed user creation: " + response.data);
                    }
                });
        }
    }]);