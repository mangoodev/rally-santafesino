'use strict';

describe('Controller Tests', function() {

    describe('AutoCarrera Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAutoCarrera, MockAuto, MockCarrera;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAutoCarrera = jasmine.createSpy('MockAutoCarrera');
            MockAuto = jasmine.createSpy('MockAuto');
            MockCarrera = jasmine.createSpy('MockCarrera');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AutoCarrera': MockAutoCarrera,
                'Auto': MockAuto,
                'Carrera': MockCarrera
            };
            createController = function() {
                $injector.get('$controller')("AutoCarreraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:autoCarreraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
