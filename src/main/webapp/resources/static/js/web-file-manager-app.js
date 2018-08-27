angular.module('fileManagerApp', ['editableTableWidgets', 'frontendServices', 'spring-security-csrf-token-interceptor'])
    .filter('excludeDeleted', function () {
        return function (input) {
            return _.filter(input, function (item) {return item.deleted == undefined || !item.deleted;});
        }
    })
    .controller('FileManagerCtrl', ['$scope' , 'FileService', 'UserService','fileUpload', '$timeout',
        function ($scope, FileService, UserService, fileUpload, $timeout) {

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

            $scope.rename = function(newName, urlFile) {
                FileService.renameFile(newName, urlFile);
            }


            $scope.search = function () {
                loadFileData();
            };

            $scope.add = function () {
               $scope.vm.files.unshift({
                    id: null,
                    nameFile: null,
                    urlFile: null,
                    size: null,
                    info: null,
                    newReplaceFile: null,
                    undoReplaceFile: null,
                    status: null,
                    type: null,
                    selected: false,
                    new: true
                });
            };

            $scope.delete = function () {
                var deletedFileIds = _.chain($scope.vm.files)
                    .filter(function (file) {return file.selected && !file.new;})
                    .map(function (file) {return file.id;})
                    .value();

                FileService.deleteFiles(deletedFileIds)
                    .then(function () {
                            clearMessages();
                            showInfoMessage("deletion successful.");

                            _.remove($scope.vm.files, function(file) {return file.selected;});

                            $scope.selectionChanged();
                            updateUserInfo();
                        },
                        function () {
                            clearMessages();
                            $scope.vm.errorMessages.push({description: "deletion failed."});
                        });
            };

            $scope.addToBasket = function () {
                var addToBasketFileIds = _.chain($scope.vm.files)
                    .filter(function (file) {return file.selected && !file.new;})
                    .map(function (file) {return file.id;})
                    .value();

                FileService.addToBasketFiles(addToBasketFileIds)
                    .then(function () {
                            clearMessages();
                            showInfoMessage("add to basket successful.");
                            _.remove($scope.vm.files, function(file) {return file.selected;});

                            $scope.selectionChanged();
                            updateUserInfo();
                        },
                        function () {
                            clearMessages();
                            $scope.vm.errorMessages.push({description: "add to basket failed."});
                        });
            };

            function popup(el) {
                var t = window.open('', 'New window', 'width=800,height=300').document;
                t.open();
                t.write(el);
            };

            $scope.viewDownloadStatistics = function () {
                FileService.searchFiles()
                    .then(function (data) {
                            var spisok = data.files;
                            var i;
                            var message = "";
                            for (i = 0; i < spisok.length; i++) {
                                message = message + "link: " + spisok[i].urlFile + ", count download: " + spisok[i].count +";<br/>";
                            }
                        popup(message);
                        });
            };

            $scope.returnFromBasket = function() {
                var returnFromBasketFileIds = _.chain($scope.vm.files)
                    .filter(function (file) {return file.selected && !file.new;})
                    .map(function (file) {return file.id;})
                    .value();

                FileService.returnFromBasketFiles(returnFromBasketFileIds)
                    .then(function () {
                            clearMessages();
                            showInfoMessage("add to basket successful.");
                            _.remove($scope.vm.files, function(file) {return file.selected;});

                            $scope.selectionChanged();
                            updateUserInfo();
                        },
                        function () {
                            clearMessages();
                            $scope.vm.errorMessages.push({description: "add to basket failed."});
                        });
            };

            $scope.reset = function () {
                $scope.vm.files = $scope.vm.originalFiles;
            };

            $scope.logout = function () {
                UserService.logout();
            };


            $scope.updateList = function () {
                loadFileData();
            };

            $scope.uploadFile = function(){
                var file = $scope.myFile;
                if (file!=null || file!=undefined) {
                    console.log('file is ');
                    console.dir(file);
                    var uploadUrl = "/file/upload";
                    fileUpload.uploadFileToUrl(file, uploadUrl);
                }
                else
                    alert("Error. File is not select. ")
            };

            $scope.goToUrlFile = function (data) {
                var fileName = data.toString().substring(data.toString().lastIndexOf("/")+1,data.toString().length);
                FileService.goToUrlFile(data)
                    .then(function (value) {
                        if (value!=null) {
                            var blob = new Blob([value], {type: "multipart/form-data"});
                            var fileURL = window.URL.createObjectURL(blob);
                            var a = document.createElement("a");
                            document.body.appendChild(a);
                            a.style = "display: none";
                            a.href = fileURL;
                            a.download = fileName;
                            a.click();
                        }
                        else {
                            alert("File not found. ");
                        }

                    })
            };

            $scope.addFileInfo = function (name) {
                var info = prompt("Window for entering information about the file: ");
                if (info != "") {
                    FileService.addFileInfo(name, info);
                }
                else {
                    alert("Your info text is empty. ")
                }
            };

            $scope.replaceFile = function (name) {
                document.getElementById("avatar").click();
                var input = document.querySelector("input[name=avatar]");

                input.onchange = function () {
                    var file = document.getElementById("avatar").files[0];
                    if (file!=null || file!=undefined) {
                        FileService.replaceFileIn(file,  name);
                    }
                    else alert("Error. File is not select. ")
                }

            };

            $scope.undoReplace = function (name) {
                FileService.undoReplaceFileIn(name);
            }



    }])
    .directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function(){
                    scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                    });

                });
            }
        };
    }]);
