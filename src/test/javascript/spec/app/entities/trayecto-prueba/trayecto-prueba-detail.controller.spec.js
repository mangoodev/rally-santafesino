'use strict';

describe('Controller Tests', function() {

    describe('TrayectoPrueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTrayectoPrueba, MockPruebas, MockTrayecto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTrayectoPrueba = jasmine.createSpy('MockTrayectoPrueba');
            MockPruebas = jasmine.createSpy('MockPruebas');
            MockTrayecto = jasmine.createSpy('MockTrayecto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TrayectoPrueba': MockTrayectoPrueba,
                'Pruebas': MockPruebas,
                'Trayecto': MockTrayecto
            };
            createController = function() {
                $injector.get('$controller')("TrayectoPruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:trayectoPruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
