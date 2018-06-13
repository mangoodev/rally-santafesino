'use strict';

describe('Controller Tests', function() {

    describe('Tiempos Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTiempos, MockAuto_tiempo_prueba;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTiempos = jasmine.createSpy('MockTiempos');
            MockAuto_tiempo_prueba = jasmine.createSpy('MockAuto_tiempo_prueba');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tiempos': MockTiempos,
                'Auto_tiempo_prueba': MockAuto_tiempo_prueba
            };
            createController = function() {
                $injector.get('$controller')("TiemposDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:tiemposUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
