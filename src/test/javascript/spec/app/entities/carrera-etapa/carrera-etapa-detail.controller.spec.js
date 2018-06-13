'use strict';

describe('Controller Tests', function() {

    describe('Carrera_etapa Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCarrera_etapa, MockCarrera, MockEtapa;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCarrera_etapa = jasmine.createSpy('MockCarrera_etapa');
            MockCarrera = jasmine.createSpy('MockCarrera');
            MockEtapa = jasmine.createSpy('MockEtapa');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Carrera_etapa': MockCarrera_etapa,
                'Carrera': MockCarrera,
                'Etapa': MockEtapa
            };
            createController = function() {
                $injector.get('$controller')("Carrera_etapaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:carrera_etapaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
