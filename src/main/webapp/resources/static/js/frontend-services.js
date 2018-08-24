
angular.module('frontendServices', [])
    .service('FileService', ['$http', '$q', function($http, $q) {
        return {
            searchFiles: function() {
                var deferred = $q.defer();

                $http.get('/file/')
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            alert("Error. It is not possible to download ");
                            deferred.reject('Error');
                        }
                    });

                return deferred.promise;
            },

            addFileInfo: function(name) {
                var deferred = $q.defer();

                $http({
                    method: 'PUT',
                    url: '/upload',
                    data: name,
                    headers: {
                        "Content-Type": "application/json"
                    }
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve();
                            window.location.replace('/resources/start-page-web-file-manager.html');
                        }
                        else {
                            alert("Error. It is not possible to upload file. ");
                            deferred.reject('Error');
                        }
                    });

                return deferred.promise;


            },
            goToUrlFile: function(name) {
                var deferred = $q.defer();

                $http.get(name,{ responseType: 'arraybuffer' })
                    .then(function (response) {
                        if (response.status == 200 && response.data!=null) {
                            deferred.resolve(response.data);
                            return response;
                        }
                        else {
                            alert("File does not exist.");
                            deferred.reject('Error');
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
                    headers: {
                        "Content-Type": "application/json"
                    }
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve();
                        }
                        else {
                            alert("Error. It is not possible to delete. ");
                            deferred.reject('Error');
                        }
                    });

                return deferred.promise;
            },
            addToBasketFiles: function(addToBasketFileIds) {
                var deferred = $q.defer();

                $http({
                    method: 'POST',
                    url: '/file',
                    data: addToBasketFileIds,
                    headers: {
                        "Content-Type": "application/json"
                    }
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve();
                            window.location.replace('/resources/start-page-web-file-manager.html');
                        }
                        else {
                            alert("Error. It is not possible to add to basket. ");
                            deferred.reject('Error');
                        }
                    });

                return deferred.promise;
            },

            viewDownloadStatisticsFiles: function() {
                var deferred = $q.defer();
                $http.get("/statistics",{ responseType: 'arraybuffer' })
                    .then(function (response) {
                        if (response.status == 200 && response.data!=null) {
                            deferred.resolve(response.data);
                            return response;
                        }
                        else {
                            alert("Statistics is empty.");
                            deferred.reject('Error');
                        }
                    });

                return deferred.promise;
            },

            returnFromBasketFiles: function (returnFromBasketFilesIds) {
                var deferred = $q.defer();

                $http({
                    method: 'PUT',
                    url: '/file',
                    data: returnFromBasketFilesIds,
                    headers: {
                        "Content-Type": "application/json"
                    }
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve();
                            window.location.replace('/resources/start-page-web-file-manager.html');
                        }
                        else {
                            alert("Error. It is not possible to return from basket. ");
                            deferred.reject('Error');
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
                            alert("Error. It is not possible to download user info. ");
                            deferred.reject('Error');
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
                            window.location.replace('/resources/start-page-web-file-manager.html');
                        }
                        else {
                            alert("Error. It is not possible to log out. ");
                            console.log("Logout failed!");
                        }
                    });
            }
        };
    }])
    .service('fileUpload', ['$http', '$q', function ($http, $q) {
        this.uploadFileToUrl = function(file, uploadUrl){
            var deferred = $q.defer();
            var fd = new FormData();
            fd.append('file', file);
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}})
                .then(function (response) {
                    if (response.status == 200) {
                        deferred.resolve(response.data);
                        alert("Your file download successful.");
                        window.location.assign('/resources/start-page-web-file-manager.html');
                    }
                    else if (response.status == 400) {
                        deferred.reject('Error. File with name already exist');
                        alert('Error. File with name already exist.');
                    }
                    else {
                        deferred.reject('Error. File was not uploaded.');
                        alert('Error. File was not uploaded.');
                    }
                });
            return deferred.promise;

        }
    }]);
