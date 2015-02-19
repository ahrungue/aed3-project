'use strict';

/**
 * @ngdoc function
 * @name cornApp.controller:QuestionsCtrl
 * @description
 * # QuestionsCtrl
 * Controller of the cornApp
 */
angular.module('cornApp').controller('QuestionsCtrl', ['$scope','Question', function ($scope,Question) {

    $scope.options = [];
    $scope.getNewQuestion = function(){
        var newQuestion = new Question();
        newQuestion.answer = null;
        $scope.options = [];
        $scope.options = [
            {'text': '', 'checked': false},
            {'text': '', 'checked': false},
            {'text': '', 'checked': false},
            {'text': '', 'checked': false}
        ];
        return newQuestion;
    };

    $scope.questions = Question.get();
    $scope.question = $scope.getNewQuestion();

    $scope.save = function(){
        $scope.question.options = $scope.options[0].text;
        for( var i = 1; i < $scope.options.length; ++i){
            $scope.question.options += (';'+$scope.options[i].text);
        }

        $scope.question.$save( function(data){
            if(data.errors){
                $scope.showSuccessMessage('Pergunta Cadastrada!');
            }else{
                $scope.showErrorMessage('Pergunta não cadastrada!');
            }
        });
    };

    $scope.setAnswer = function( pos ){
        if( $scope.options ){
            for( var i = 0; i < $scope.options.length; ++i){
                if( i !== pos){
                    $scope.options[i].checked = undefined;
                }
            }
            $scope.question.answer = pos;
        }else{
            $scope.showErrorMessage('Erro ao definir a resposta');
        }
    };

}]);//fim DashBoardCtrl
