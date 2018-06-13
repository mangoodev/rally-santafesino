'use strict';

describe('Controller Tests', function() {

    describe('Etapa_prueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEtapa_prueba, MockEtapa, MockPruebas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEtapa_prueba = jasmine.createSpy('MockEtapa_prueba');
            MockEtapa = jasmine.createSpy('MockEtapa');
            MockPruebas = jasmine.createSpy('MockPruebas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Etapa_prueba': MockEtapa_prueba,
                'Etapa': MockEtapa,
                'Pruebas': MockPruebas
            };
            createController = function() {
                $injector.get('$controller')("Etapa_pruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:etapa_pruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
