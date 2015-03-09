'use strict';

describe('Controller: UploadCtrl', function () {

    // load the controller's module
    beforeEach(module('cornApp'));

    var UploadCtrl, scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        UploadCtrl = $controller('UploadCtrl', {
            $scope: scope
        });
    }));

    it('Uploader must be not null.', function () {
        expect(scope.uploader).toBeDefined();
    });

});
