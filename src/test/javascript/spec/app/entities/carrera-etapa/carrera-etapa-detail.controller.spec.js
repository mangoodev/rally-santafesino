'use strict';

describe('Controller Tests', function() {

    describe('CarreraEtapa Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCarreraEtapa, MockCarrera, MockEtapa;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCarreraEtapa = jasmine.createSpy('MockCarreraEtapa');
            MockCarrera = jasmine.createSpy('MockCarrera');
            MockEtapa = jasmine.createSpy('MockEtapa');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CarreraEtapa': MockCarreraEtapa,
                'Carrera': MockCarrera,
                'Etapa': MockEtapa
            };
            createController = function() {
                $injector.get('$controller')("CarreraEtapaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:carreraEtapaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
