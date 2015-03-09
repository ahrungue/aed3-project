'use strict';

/**
 * @ngdoc function
 * @name cornApp.controller:UploadCtrl
 * @description
 * # UploadCtrl
 * Controller of the cornApp
 */
angular.module('cornApp').controller('UploadCtrl', [ '$scope', '$routeParams', '$location', 'FileUploader', function($scope, $routeParams, $location, FileUploader){
    // create a uploader with options
    var uploader = $scope.uploader = new FileUploader({
        scope: $scope,                          // to automatically update the html. Default: $rootScope
        url: 'upload',
        formData: [
            { key: 'value' }
        ],
        filters: [{
            name: 'queueFilter',
            fn: function() {
                return this.queue.length < 11;
            }
        }]
    });

    $scope.cancel = function(){
        $location.path('upload');
    };

    // ADDING FILTERS
    //uploader.filters.push({
    //    name: 'queueFilter',
    //    fn: function(item /*{File|FileLikeObject}*/, options) {
    //        return this.queue.length < 11;
    //    }
    //});//fim Filtro para limitar a quantidade de arquivos

    // CALLBACKS
    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onAfterAddingAll = function(addedFileItems) {
        console.info('onAfterAddingAll', addedFileItems);
    };
    uploader.onBeforeUploadItem = function(item) {
        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function(fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onProgressAll = function(progress) {
        console.info('onProgressAll', progress);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function(fileItem, response, status, headers) {
        console.info('onCancelItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
        $scope.showSuccessMessage('Arquivo enviado com sucesso.');
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
        $scope.showSuccessMessage('Todos os arquivos foram enviados.');
    };

}]);//fim UploadCtrl