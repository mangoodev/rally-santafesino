'use strict';

describe('Controller Tests', function() {

    describe('LocalidadCarrera Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLocalidadCarrera, MockCarrera, MockLocalidad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLocalidadCarrera = jasmine.createSpy('MockLocalidadCarrera');
            MockCarrera = jasmine.createSpy('MockCarrera');
            MockLocalidad = jasmine.createSpy('MockLocalidad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LocalidadCarrera': MockLocalidadCarrera,
                'Carrera': MockCarrera,
                'Localidad': MockLocalidad
            };
            createController = function() {
                $injector.get('$controller')("LocalidadCarreraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:localidadCarreraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
