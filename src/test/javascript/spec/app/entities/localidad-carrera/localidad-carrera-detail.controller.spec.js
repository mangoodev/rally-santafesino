'use strict';

describe('Controller Tests', function() {

    describe('Localidad_carrera Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLocalidad_carrera, MockCarrera, MockLocalidad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLocalidad_carrera = jasmine.createSpy('MockLocalidad_carrera');
            MockCarrera = jasmine.createSpy('MockCarrera');
            MockLocalidad = jasmine.createSpy('MockLocalidad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Localidad_carrera': MockLocalidad_carrera,
                'Carrera': MockCarrera,
                'Localidad': MockLocalidad
            };
            createController = function() {
                $injector.get('$controller')("Localidad_carreraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:localidad_carreraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
