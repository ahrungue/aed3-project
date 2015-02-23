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
  $scope.question = {};
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
    }//fim for(i)

    if( $scope.question.id ){
      Question.update( $scope.question, function(data){
        if(data.errors){
          $scope.showErrorMessage('Pergunta não cadastrada!');
        }else{
          $scope.showSuccessMessage('Pergunta Cadastrada!');
          $scope.cancel();
        }
      });
    }else{

      if( !$scope.question.answer ){
        $scope.showErrorMessage('Pergunta não cadastrada! Verificar se os campos foram preenchidos corretamente!');
      }else{
        $scope.question.$save( function(data){
          if(data.errors){
            $scope.showErrorMessage('Pergunta não cadastrada!');
          }else{
            $scope.showSuccessMessage('Pergunta Cadastrada!');
            $scope.cancel();
          }//fim if-else
        });
      }
    }
  };

  $scope.edit = function( pos ){
    $scope.question = $scope.questions.questions[pos];
    var options = $scope.question.options.split(';');
    $scope.options = [
      {'text': options[0], 'checked': false},
      {'text': options[1], 'checked': false},
      {'text': options[2], 'checked': false},
      {'text': options[3], 'checked': false}
    ];
    $scope.options[$scope.question.answer].checked = true;
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

  $scope.cancel = function(){
    $scope.questions = Question.get();
    $scope.question = $scope.getNewQuestion();
  };

}]);//fim DashBoardCtrl
