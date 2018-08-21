require.config({
    paths: {
        angular: '../../bower_components/angular/angular',
        angularMessages: '../../bower_components/angular-messages/angular-messages',
        csrfInterceptor: '../../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: "../../bower_components/lodash/dist/lodash",
        editableTableWidgets: '../../static/js/editable-table-widgets',
        frontendServices: 'frontend-services',
        webFileManager: "web-file-manager-app"
    },
    shim: {
        angular: {
            exports: "angular"
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            deps: ['angular']
        },
        editableTableWidgets: {
            deps: ['angular', 'lodash']
        },
        frontendServices: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        webFileManager: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'editableTableWidgets' , 'frontendServices']
        }
    }
});

require(['webFileManager'], function () {
    angular.bootstrap(document.getElementById('webFileManager'), ['webFileManager']);
});