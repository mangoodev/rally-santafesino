'use strict';

describe('Controller Tests', function() {

    describe('CarreraClase Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCarreraClase, MockCarrera, MockClase;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCarreraClase = jasmine.createSpy('MockCarreraClase');
            MockCarrera = jasmine.createSpy('MockCarrera');
            MockClase = jasmine.createSpy('MockClase');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CarreraClase': MockCarreraClase,
                'Carrera': MockCarrera,
                'Clase': MockClase
            };
            createController = function() {
                $injector.get('$controller')("CarreraClaseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rallyApp:carreraClaseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
