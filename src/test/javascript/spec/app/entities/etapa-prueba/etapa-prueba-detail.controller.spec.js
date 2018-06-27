'use strict';

describe('Controller Tests', function() {

    describe('EtapaPrueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEtapaPrueba, MockEtapa, MockPruebas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEtapaPrueba = jasmine.createSpy('MockEtapaPrueba');
            MockEtapa = jasmine.createSpy('MockEtapa');
            MockPruebas = jasmine.createSpy('MockPruebas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EtapaPrueba': MockEtapaPrueba,
                'Etapa': MockEtapa,
                'Pruebas': MockPruebas
            };
            createController = function() {
                $injector.get('$controller')("EtapaPruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:etapaPruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
