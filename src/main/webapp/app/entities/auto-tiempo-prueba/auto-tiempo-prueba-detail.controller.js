(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_tiempo_pruebaDetailController', Auto_tiempo_pruebaDetailController);

    Auto_tiempo_pruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Auto_tiempo_prueba', 'Auto', 'Tiempos', 'Pruebas'];

    function Auto_tiempo_pruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, Auto_tiempo_prueba, Auto, Tiempos, Pruebas) {
        var vm = this;

        vm.auto_tiempo_prueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:auto_tiempo_pruebaUpdate', function(event, result) {
            vm.auto_tiempo_prueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
