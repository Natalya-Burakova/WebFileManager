angular.module('fileManagerApp', ['editableTableWidgets', 'frontendServices', 'spring-security-csrf-token-interceptor'])
    .filter('excludeDeleted', function () {
        return function (input) {
            return _.filter(input, function (item) {
                return item.deleted == undefined || !item.deleted;
            });
        }
    })
    .controller('FileManagerCtrl', ['$scope' , 'FileService', 'UserService', '$timeout',
        function ($scope, FileService, UserService, $timeout) {

            $scope.vm = {
                originalFiles: [],
                files: [],
                isSelectionEmpty: true,
                errorMessages: [],
                infoMessages: []
            };

            updateUserInfo();
            loadFileData();


            function showErrorMessage(errorMessage) {
                clearMessages();
                $scope.vm.errorMessages.push({description: errorMessage});
            }

            function updateUserInfo() {
                UserService.getUserInfo()
                    .then(function (userInfo) {
                            $scope.vm.userName = userInfo.login;
                        },
                        function (errorMessage) {
                            showErrorMessage(errorMessage);
                        });
            }


            function loadFileData() {

                FileService.searchFiles()
                    .then(function (data) {
                            $scope.vm.files = data.files;
                            $scope.vm.errorMessages = [];

                            $scope.vm.originalFiles = _.map(data.files);
                            $scope.vm.files = _.cloneDeep($scope.vm.originalFiles);

                            _.each($scope.vm.files, function (file) {
                                file.selected = false;
                            });

                            if ($scope.vm.files && $scope.vm.files.length == 0) {
                                showInfoMessage("No results found.");
                            }
                        },
                        function (errorMessage) {
                            showErrorMessage(errorMessage);
                        });
            }

            function clearMessages() {
                $scope.vm.errorMessages = [];
                $scope.vm.infoMessages = [];
            }

            function showInfoMessage(infoMessage) {
                $scope.vm.infoMessages = [];
                $scope.vm.infoMessages.push({description: infoMessage});
                $timeout(function () {
                    $scope.vm.infoMessages = [];
                }, 1000);
            }


            $scope.selectionChanged = function () {
                $scope.vm.isSelectionEmpty = !_.any($scope.vm.files, function (file) {
                    return file.selected && !file.deleted;
                });
            };



            $scope.search = function () {
                loadFileData();
            };

            $scope.add = function () {
                $scope.vm.files.unshift({
                    id: null,
                    urlFile: null,
                    selected: false,
                    new: true
                });
            };

            $scope.down = function () {};

            $scope.delete = function () {
                var deletedFileIds = _.chain($scope.vm.files)
                    .filter(function (file) {
                        return file.selected && !file.new;
                    })
                    .map(function (file) {
                        return file.id;
                    })
                    .value();

                FileService.deleteFiles(deletedFileIds)
                    .then(function () {
                            clearMessages();
                            showInfoMessage("deletion successful.");

                            _.remove($scope.vm.files, function(file) {
                                return file.selected;
                            });

                            $scope.selectionChanged();
                            updateUserInfo();
                        },
                        function () {
                            clearMessages();
                            $scope.vm.errorMessages.push({description: "deletion failed."});
                        });
            };

            $scope.reset = function () {
                $scope.vm.files = $scope.vm.originalFiles;
            };

            function getNotNew(files) {
                return  _.chain(files)
                    .filter(function (file) {
                        return !file.new;
                    })
                    .value();
            }

            function prepareFilesDto(files) {
                return  _.chain(files)
                    .map(function (file) {
                        return {
                            id: file.id,
                            urlFile: file.urlFile
                        }
                    })
                    .value();
            }

            $scope.save = function () {
                var maybeDirty = prepareFilesDto(getNotNew($scope.vm.files));

                var original = prepareFilesDto(getNotNew($scope.vm.originalFiles));

                var dirty = _.filter(maybeDirty).filter(function (file) {

                    var originalFile = _.filter(original, function (orig) {
                        return orig.id === file.id;
                    });

                    if (originalFile.length == 1) {
                        originalFile = originalFile[0];
                    }

                    return originalFile && ( originalFile.urlFile != file.urlFile)
                });

                var newItems = _.filter($scope.vm.files, function (file) {
                    return file.new;
                });

                var saveAll = prepareFilesDto(newItems);
                saveAll = saveAll.concat(dirty);

                $scope.vm.errorMessages = [];

                FileService.saveFiles(saveAll).then(function () {
                        $scope.search();
                        showInfoMessage("Changes saved successfully");
                    },
                    function (errorMessage) {
                        showErrorMessage(errorMessage);
                    });
            };

            $scope.logout = function () {
                UserService.logout();
            };

        }])
    .directive("selectNgFiles", function() {
        return {
            require: "ngModel",
            link: function postLink(scope,elem,attrs,ngModel) {
                elem.on("change", function(e) {
                    var files = elem[0].files;
                    ngModel.$setViewValue(files);
                })
            }
        }
    });