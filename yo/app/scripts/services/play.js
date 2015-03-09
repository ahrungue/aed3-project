'use strict';

/**
 * @ngdoc service
 * @name cornApp.Play
 * @description
 * # Play
 * Factory in the cornApp.
 */
angular.module('cornApp').factory('Play', ['$resource',  function($resource){
    return $resource('/rest/play/:action/:questionId',{action:''},{
        update: {
            method: 'PUT'
        }
    });
}]);
