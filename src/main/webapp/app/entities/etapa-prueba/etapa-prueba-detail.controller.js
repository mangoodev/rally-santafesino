(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('EtapaPruebaDetailController', EtapaPruebaDetailController);

    EtapaPruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EtapaPrueba', 'Etapa', 'Pruebas'];

    function EtapaPruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, EtapaPrueba, Etapa, Pruebas) {
        var vm = this;

        vm.etapaPrueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:etapaPruebaUpdate', function(event, result) {
            vm.etapaPrueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
