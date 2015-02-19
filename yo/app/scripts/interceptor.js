'use strict';

/**
 * @ngdoc interceptor
 * @name climelApp.interceptors
 * @description
 * # interceptor
 * Interceptor in the climelApp.
 */
angular.module('cornApp').factory('ResponseInterceptor', function($rootScope, $q) {
    return {

        //Response Success
        'response': function(response) {
            return response;
        },//fim function response

        //Response Error
        'responseError': function(rejection) {
            if( (rejection.status === 401) || (rejection.status === 405) ){
                window.location = '/login';
            }
            if(rejection.status === 404){
                $rootScope.showErrorMessage('Página não Encontrada.');
            }
            if(rejection.status === 500){
                $rootScope.showErrorMessage('Erro interno de servidor.');
            }
            return $q.reject(rejection);
        }//fim function responseError

    };//fim return

});//fim factory ResponseInterceptor

angular.module('cornApp').config(['$httpProvider', function($httpProvider){
    $httpProvider.interceptors.push('ResponseInterceptor');
}]);//fim config($httpProvider)