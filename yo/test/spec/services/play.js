'use strict';

describe('Service: play', function () {

  // load the service's module
  beforeEach(module('cornApp'));

  // instantiate service
  var play;
  beforeEach(inject(function (_Play_) {
    play = _Play_;
  }));

  it('should do something', function () {
    expect(!!play).toBe(true);
  });

});
