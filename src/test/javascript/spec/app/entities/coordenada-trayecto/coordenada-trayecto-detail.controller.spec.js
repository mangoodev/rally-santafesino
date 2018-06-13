'use strict';

describe('Controller Tests', function() {

    describe('Coordenada_trayecto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCoordenada_trayecto, MockCoordenadas, MockTrayecto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCoordenada_trayecto = jasmine.createSpy('MockCoordenada_trayecto');
            MockCoordenadas = jasmine.createSpy('MockCoordenadas');
            MockTrayecto = jasmine.createSpy('MockTrayecto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Coordenada_trayecto': MockCoordenada_trayecto,
                'Coordenadas': MockCoordenadas,
                'Trayecto': MockTrayecto
            };
            createController = function() {
                $injector.get('$controller')("Coordenada_trayectoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:coordenada_trayectoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
