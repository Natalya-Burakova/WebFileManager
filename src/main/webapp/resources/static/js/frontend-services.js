angular.module('frontendServices', [])
    .service('FileService', ['$http', '$q', function($http, $q) {
        return {
            searchFile: function(fileName, pageNumber) {
                var deferred = $q.defer();

                $http.get('/file/',{
                    params: {
                        fileName: fileName,
                        pageNumber: pageNumber
                    }
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Error retrieving list of meals');
                        }
                    });

                return deferred.promise;
            },

            deleteFiles: function(deletedFileIds) {
                var deferred = $q.defer();

                $http({
                    method: 'DELETE',
                    url: '/file',
                    data: deletedFileIds,
                    headers: {"Content-Type": "application/json"}
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve();
                        }
                        else {
                            deferred.reject('Error deleting meals');
                        }
                    });

                return deferred.promise;
            },

            saveFiles: function(dirtyFiles) {
                var deferred = $q.defer();

                $http({
                    method: 'POST',
                    url: '/file',
                    data: dirtyFiles,
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "text/plain, application/json"
                    }
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve();
                        }
                        else {
                            deferred.reject("Error saving meals: " + response.data);
                        }
                    });

                return deferred.promise;
            }
        }
    }])
    .service('UserService', ['$http','$q', function($http, $q) {
        return {
            getUserInfo: function() {
                var deferred = $q.defer();

                $http.get('/user')
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Error retrieving user info');
                        }
                    });

                return deferred.promise;
            },
            logout: function () {
                $http({
                    method: 'POST',
                    url: '/logout'
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            window.location.reload();
                        }
                        else {
                            console.log("Logout failed!");
                        }
                    });
            }
        };
    }]);