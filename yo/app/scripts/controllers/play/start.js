'use strict';

/**
 * @ngdoc function
 * @name cornApp.controller:PlayStartCtrl
 * @description
 * # PlayStartCtrl
 * Controller of the cornApp
 */
angular.module('cornApp').controller('PlayStartCtrl', ['$scope','Play', 'Score', function ($scope, Play, Score) {

    //Back-engine call to get questions for the game
    $scope.play = Play.get();

    $scope.totalPrize = 0;
    $scope.currentQuestionIndex = 0;

    $scope.questionsValue = [
        {'correct': 1000, 'stop': 0,    'wrong': 0},
        {'correct': 2000, 'stop': 1000, 'wrong': 500},
        {'correct': 3000, 'stop': 2000, 'wrong': 1000},
        {'correct': 4000, 'stop': 3000, 'wrong': 1500},
        {'correct': 5000, 'stop': 4000, 'wrong': 2000},

        {'correct': 10000, 'stop': 5000,  'wrong': 2500},
        {'correct': 20000, 'stop': 10000, 'wrong': 5000},
        {'correct': 30000, 'stop': 20000, 'wrong': 10000},
        {'correct': 40000, 'stop': 30000, 'wrong': 15000},
        {'correct': 50000, 'stop': 40000, 'wrong': 20000},

        {'correct': 100000, 'stop': 50000,  'wrong': 25000},
        {'correct': 200000, 'stop': 100000, 'wrong': 50000},
        {'correct': 300000, 'stop': 200000, 'wrong': 100000},
        {'correct': 400000, 'stop': 300000, 'wrong': 150000},
        {'correct': 500000, 'stop': 400000, 'wrong': 200000},

        {'correct': 1000000, 'stop': 500000, 'wrong': 0}
    ];//end questionsValue

    $scope.checkAnswer =  function( answerIndex ){
        if( answerIndex === $scope.play.questions[[$scope.currentQuestionIndex]].answer ){

            $scope.totalPrize += $scope.questionsValue[$scope.currentQuestionIndex].correct;

            //next question
            ++$scope.currentQuestionIndex;

            $scope.showSuccessMessage('Certa resposta!');
        }else{
            $scope.totalPrize = $scope.questionsValue[$scope.currentQuestionIndex].wrong;
            $scope.showErrorMessage('Que pena você errou.');
            $scope.endGame($scope.totalPrize);
        }//end if-else
    };//end checkAnswer()


    $scope.endGame = function( prize ){
        var score =  new Score();
        score.prizeValue = prize;

        score.$save( function(data){
            if(data.errors){
                $scope.showErrorMessage('Resultado não cadastrado!');
            }else{
                $scope.showSuccessMessage('Resultado Cadastrado!');
                $scope.locationPath('/scores');
            }//fim if-else
        });//fim $save()

    };//end endGame()

}]);//fim PlayStartCtrl
