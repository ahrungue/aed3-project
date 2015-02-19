'use strict';

/**
 * @ngdoc service
 * @name cornApp.Question
 * @description
 * # Question
 * Factory in the cornApp.
 */
angular.module('cornApp').factory('Question', ['$resource',  function($resource){
    return $resource('/rest/question/:action/:questionId',{action:''},{
        update: {
            method: 'PUT'
        }
    });
}]);
