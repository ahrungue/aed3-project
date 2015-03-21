'use strict';

/**
 * @ngdoc function
 * @name cornApp.controller:ScoreIndexCtrl
 * @description
 * # ScoreIndexCtrl
 * Controller of the cornApp
 */
angular.module('cornApp').controller('ScoreIndexCtrl', ['$scope','Score', function ($scope, Score) {

    //Get the list of scores
    $scope.scores = Score.get();

}]);//fim PlayStartCtrl
