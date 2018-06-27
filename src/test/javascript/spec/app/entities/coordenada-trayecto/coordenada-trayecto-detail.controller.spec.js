'use strict';

describe('Controller Tests', function() {

    describe('CoordenadaTrayecto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCoordenadaTrayecto, MockCoordenadas, MockTrayecto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCoordenadaTrayecto = jasmine.createSpy('MockCoordenadaTrayecto');
            MockCoordenadas = jasmine.createSpy('MockCoordenadas');
            MockTrayecto = jasmine.createSpy('MockTrayecto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CoordenadaTrayecto': MockCoordenadaTrayecto,
                'Coordenadas': MockCoordenadas,
                'Trayecto': MockTrayecto
            };
            createController = function() {
                $injector.get('$controller')("CoordenadaTrayectoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:coordenadaTrayectoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
