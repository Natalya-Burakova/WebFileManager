require.config({
    paths: {
        angular: '../../bower_components/angular/angular',
        angularMessages: '../../bower_components/angular-messages/angular-messages',
        csrfInterceptor: '../../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: "../../bower_components/lodash/dist/lodash",
        editableTableWidgets: '../../public/js/editable-table-widgets',
        frontendServices: './frontend-services',
        fileManagerApp: "web-file-manager-app"
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
        fileManagerApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'editableTableWidgets' , 'frontendServices']
        }
    }
});

require(['fileManagerApp'], function () {
    angular.bootstrap(document.getElementById('fileManagerApp'), ['fileManagerApp']);
});