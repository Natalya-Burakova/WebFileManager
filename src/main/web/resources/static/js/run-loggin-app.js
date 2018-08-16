require.config({
    paths: {
        angular: '../../bower_components/angular/angular',
        angularMessages: '../../bower_components/angular-messages/angular-messages',
        csrfInterceptor: '../../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: "../../bower_components/lodash/dist/lodash",
        editableTableWidgets: 'edit',
        common: 'common',
        loginApp: 'login'
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
        common: {
            deps: ['angular', 'csrfInterceptor', 'angularMessages','edit']
        },
        loginApp: {
            deps: [ 'common']
        }
    }
});

require(['loginApp'], function () {
    angular.bootstrap(document.getElementById('loginApp'), ['loginApp']);

});