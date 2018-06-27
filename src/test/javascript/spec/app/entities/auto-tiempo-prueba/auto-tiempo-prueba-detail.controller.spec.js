'use strict';

describe('Controller Tests', function() {

    describe('AutoTiempoPrueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAutoTiempoPrueba, MockAuto, MockTiempos, MockPruebas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAutoTiempoPrueba = jasmine.createSpy('MockAutoTiempoPrueba');
            MockAuto = jasmine.createSpy('MockAuto');
            MockTiempos = jasmine.createSpy('MockTiempos');
            MockPruebas = jasmine.createSpy('MockPruebas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AutoTiempoPrueba': MockAutoTiempoPrueba,
                'Auto': MockAuto,
                'Tiempos': MockTiempos,
                'Pruebas': MockPruebas
            };
            createController = function() {
                $injector.get('$controller')("AutoTiempoPruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:autoTiempoPruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
