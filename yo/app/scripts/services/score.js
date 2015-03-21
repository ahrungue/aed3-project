'use strict';

/**
 * @ngdoc service
 * @name cornApp.Score
 * @description
 * # Score
 * Factory in the cornApp.
 */
angular.module('cornApp').factory('Score', ['$resource',  function($resource){
    return $resource('/rest/score/:action/:scoreId',{action:''},{
        update: {
            method: 'PUT'
        }
    });
}]);
