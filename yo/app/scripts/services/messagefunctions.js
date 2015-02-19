'use strict';

/**
 * @ngdoc service
 * @name climelApp.MessageFunctions
 * @description
 * # MessageFunctions
 * Factory in the climelApp.
 */
angular.module('cornApp').factory('MessageFunctions', ['$alert', function ($alert) {
  return {
    showErrorMessage: function (messageContent) {
      $alert({content: messageContent, type: 'danger', show: true, container: '#alerts-container', duration: 3});
    },
    showSuccessMessage: function (messageContent) {
      $alert({content: messageContent, type: 'success', show: true, container: '#alerts-container', duration: 3});
    }
  };
}]);//fim MessageFunctions(factory)
