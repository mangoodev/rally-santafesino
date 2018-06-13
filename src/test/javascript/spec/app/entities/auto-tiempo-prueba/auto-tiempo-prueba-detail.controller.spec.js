'use strict';

describe('Controller Tests', function() {

    describe('Auto_tiempo_prueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAuto_tiempo_prueba, MockAuto, MockTiempos, MockPruebas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAuto_tiempo_prueba = jasmine.createSpy('MockAuto_tiempo_prueba');
            MockAuto = jasmine.createSpy('MockAuto');
            MockTiempos = jasmine.createSpy('MockTiempos');
            MockPruebas = jasmine.createSpy('MockPruebas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Auto_tiempo_prueba': MockAuto_tiempo_prueba,
                'Auto': MockAuto,
                'Tiempos': MockTiempos,
                'Pruebas': MockPruebas
            };
            createController = function() {
                $injector.get('$controller')("Auto_tiempo_pruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:auto_tiempo_pruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
