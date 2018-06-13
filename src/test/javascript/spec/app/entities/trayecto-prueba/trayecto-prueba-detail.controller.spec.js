'use strict';

describe('Controller Tests', function() {

    describe('Trayecto_prueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTrayecto_prueba, MockPruebas, MockTrayecto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTrayecto_prueba = jasmine.createSpy('MockTrayecto_prueba');
            MockPruebas = jasmine.createSpy('MockPruebas');
            MockTrayecto = jasmine.createSpy('MockTrayecto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Trayecto_prueba': MockTrayecto_prueba,
                'Pruebas': MockPruebas,
                'Trayecto': MockTrayecto
            };
            createController = function() {
                $injector.get('$controller')("Trayecto_pruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:trayecto_pruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
