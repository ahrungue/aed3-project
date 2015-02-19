'use strict';

/**
 * @ngdoc function
 * @name cornApp.controller:PlayStartCtrl
 * @description
 * # PlayStartCtrl
 * Controller of the cornApp
 */
angular.module('cornApp').controller('PlayStartCtrl', ['$scope','Play', function ($scope, Play) {
    $scope.play = Play.get();
}]);//fim PlayStartCtrl
