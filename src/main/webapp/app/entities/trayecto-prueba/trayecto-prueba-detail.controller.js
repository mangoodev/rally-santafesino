(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Trayecto_pruebaDetailController', Trayecto_pruebaDetailController);

    Trayecto_pruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trayecto_prueba', 'Pruebas', 'Trayecto'];

    function Trayecto_pruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, Trayecto_prueba, Pruebas, Trayecto) {
        var vm = this;

        vm.trayecto_prueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:trayecto_pruebaUpdate', function(event, result) {
            vm.trayecto_prueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
