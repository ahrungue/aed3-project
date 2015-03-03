'use strict';

/**
 * @ngdoc overview
 * @name cornApp
 * @description
 * # cornApp
 *
 * Main module of the application.
 */
angular.module('cornApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'mgcrea.ngStrap',
    'angularFileUpload'
]).config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardCtrl'
        })
        .when('/play/start', {
            templateUrl: 'views/play/start.html',
            controller: 'PlayStartCtrl'
        })
        .when('/question', {
            templateUrl: 'views/question.html',
            controller: 'QuestionCtrl'
        })
        .when('/question', {
            templateUrl: 'views/questions.html',
            controller: 'QuestionsCtrl'
        })
        .when('/upload', {
            templateUrl: 'views/upload.html',
            controller: 'UploadCtrl'
        })
        .otherwise({
            redirectTo: '/play/start'
        });
});

angular.module('cornApp').run(['$rootScope','MessageFunctions','$location', function($rootScope, MessageFunctions, $location){

    $rootScope.menu = function( menuURL ){
        return $location.$$url === menuURL;
    };

    //******************************************************************************************************************//
    $rootScope.showErrorMessage   = MessageFunctions.showErrorMessage;                                                  //
    $rootScope.showSuccessMessage = MessageFunctions.showSuccessMessage;                                                //
    //******************************************************************************************************************//

}]);//fim run($rootScope)
