angular.module('webFileManager', ['editableTableWidgets', 'frontendServices', 'spring-security-csrf-token-interceptor'])
    .filter('excludeDeleted', function () {
        return function (input) {
            return _.filter(input, function (item) {return item.deleted == undefined || !item.deleted;});
        }
    })
    .controller('WebFileCtrl', ['$scope' , 'FileService', 'UserService', '$timeout',
        function ($scope, FileService, UserService, $timeout) {

            $scope.vm = {
                currentPage: 1,
                totalPages: 0,
                files: [],
                isSelectionEmpty: true,
                errorMessages: [],
                infoMessages: []
            };

            updateFiles();
            loadFileData(null, 1);


            function showErrorMessage(errorMessage) {
                clearMessages();
                $scope.vm.errorMessages.push({description: errorMessage});
            }

            function updateFiles() {
                FileService.getFiles()
                    .then(function (userInfo) {
                            $scope.vm.userName = userInfo.userName;

                        },
                        function (errorMessage) {showErrorMessage(errorMessage);});
            }

            function markAppAsInitialized() {
                if ($scope.vm.appReady == undefined) {
                    $scope.vm.appReady = true;
                }
            }

            function loadFileData(fileName, pageNumber) {
                FileService.searchFile(fileName, pageNumber)
                    .then(function (data) {

                            $scope.vm.errorMessages = [];
                            $scope.vm.currentPage = data.currentPage;
                            $scope.vm.totalPages = data.totalPages;

                            $scope.vm.originalFiles = _.map(data.files, function (file) {
                                return file; });

                            $scope.vm.files = _.cloneDeep($scope.vm.originalFiles);

                            _.each($scope.vm.files, function (file) {
                                file.selected = false;
                            });

                            markAppAsInitialized();

                            if ($scope.vm.files && $scope.vm.files.length == 0) {
                                showInfoMessage("No results found.");
                            }
                        },
                        function (errorMessage) {
                            showErrorMessage(errorMessage);
                            markAppAsInitialized();
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
                $scope.vm.isSelectionEmpty = !_.any($scope.vm.files, function (meal) {
                    return files.selected && !files.deleted;
                });
            };

            $scope.pages = function () {
                return _.range(1, $scope.vm.totalPages + 1);
            };

            $scope.search = function (page) {
                    loadFileData($scope.vm.fileName, page == undefined ? 1 : page);
            };

            $scope.previous = function () {
                if ($scope.vm.currentPage > 1) {
                    $scope.vm.currentPage-= 1;
                    loadFileData($e.vm.fileName, $scope.vm.currentPage)}
            };

            $scope.next = function () {
                if ($scope.vm.currentPage < $scope.vm.totalPages) {
                    $scope.vm.currentPage += 1;
                    loadFileData($scope.vm.fileName, $scope.vm.currentPage);
                }
            };

            $scope.goToPage = function (pageNumber) {
                if (pageNumber > 0 && pageNumber <= $scope.vm.totalPages) {
                    $scope.vm.currentPage = pageNumber;
                    loadFileData($scope.vm.fileName, pageNumber);
                }
            };

            $scope.add = function () {
                $scope.vm.files.unshift({
                    id: null,
                    datetime: null,
                    description: null,
                    calories: null,
                    selected: false,
                    new: true
                });
            };

            $scope.delete = function () {
                var deletedMealIds = _.chain($scope.vm.meals)
                    .filter(function (meal) {
                        return meal.selected && !meal.new;
                    })
                    .map(function (meal) {
                        return meal.id;
                    })
                    .value();

                MealService.deleteMeals(deletedMealIds)
                    .then(function () {
                            clearMessages();
                            showInfoMessage("deletion successful.");

                            _.remove($scope.vm.meals, function(meal) {
                                return meal.selected;
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
                $scope.vm.meals = $scope.vm.originalMeals;
            };

            function getNotNew(meals) {
                return  _.chain(meals)
                    .filter(function (meal) {
                        return !meal.new;
                    })
                    .value();
            }

            function prepareMealsDto(meals) {
                return  _.chain(meals)
                    .each(function (meal) {
                        if (meal.datetime) {
                            var dt = meal.datetime.split(" ");
                            meal.date = dt[0];
                            meal.time = dt[1];
                        }
                    })
                    .map(function (meal) {
                        return {
                            id: meal.id,
                            date: meal.date,
                            time: meal.time,
                            description: meal.description,
                            calories: meal.calories,
                            version: meal.version
                        }
                    })
                    .value();
            }

            $scope.save = function () {

                var maybeDirty = prepareMealsDto(getNotNew($scope.vm.meals));

                var original = prepareMealsDto(getNotNew($scope.vm.originalMeals));

                var dirty = _.filter(maybeDirty).filter(function (meal) {

                    var originalMeal = _.filter(original, function (orig) {
                        return orig.id === meal.id;
                    });

                    if (originalMeal.length == 1) {
                        originalMeal = originalMeal[0];
                    }

                    return originalMeal && ( originalMeal.date != meal.date ||
                        originalMeal.time != meal.time || originalMeal.description != meal.description ||
                        originalMeal.calories != meal.calories)
                });

                var newItems = _.filter($scope.vm.meals, function (meal) {
                    return meal.new;
                });

                var saveAll = prepareMealsDto(newItems);
                saveAll = saveAll.concat(dirty);

                $scope.vm.errorMessages = [];

                /
                MealService.saveMeals(saveAll).then(function () {
                        $scope.search($scope.vm.currentPage);
                        showInfoMessage("Changes saved successfully");
                        updateUserInfo();
                    },
                    function (errorMessage) {
                        showErrorMessage(errorMessage);
                    });

            };

            $scope.logout = function () { FileService.logout();}


        }]);