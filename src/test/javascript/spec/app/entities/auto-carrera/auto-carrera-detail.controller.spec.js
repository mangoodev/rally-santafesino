'use strict';

describe('Controller Tests', function() {

    describe('Auto_carrera Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAuto_carrera, MockAuto, MockCarrera;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAuto_carrera = jasmine.createSpy('MockAuto_carrera');
            MockAuto = jasmine.createSpy('MockAuto');
            MockCarrera = jasmine.createSpy('MockCarrera');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Auto_carrera': MockAuto_carrera,
                'Auto': MockAuto,
                'Carrera': MockCarrera
            };
            createController = function() {
                $injector.get('$controller')("Auto_carreraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:auto_carreraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
