(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('LocalidadCarreraDetailController', LocalidadCarreraDetailController);

    LocalidadCarreraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LocalidadCarrera', 'Carrera', 'Localidad'];

    function LocalidadCarreraDetailController($scope, $rootScope, $stateParams, previousState, entity, LocalidadCarrera, Carrera, Localidad) {
        var vm = this;

        vm.localidadCarrera = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:localidadCarreraUpdate', function(event, result) {
            vm.localidadCarrera = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
