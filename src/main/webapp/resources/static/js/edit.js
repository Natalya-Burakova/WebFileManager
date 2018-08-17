angular.module('edit', [])
    .directive('ttErrorMessages', function() {
        return {
            restrict: 'E',
            link: function(scope, element, attrs) {
                scope.extraStyles = attrs.extraStyles;
            },
            templateUrl: '/resources/public/partials/error.html'
        }
    }) /*директива для ошибок*/