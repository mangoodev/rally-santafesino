(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoTiempoPruebaDetailController', AutoTiempoPruebaDetailController);

    AutoTiempoPruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AutoTiempoPrueba', 'Auto', 'Tiempos', 'Pruebas'];

    function AutoTiempoPruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, AutoTiempoPrueba, Auto, Tiempos, Pruebas) {
        var vm = this;

        vm.autoTiempoPrueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:autoTiempoPruebaUpdate', function(event, result) {
            vm.autoTiempoPrueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
