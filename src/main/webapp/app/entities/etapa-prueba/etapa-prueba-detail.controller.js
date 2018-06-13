(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Etapa_pruebaDetailController', Etapa_pruebaDetailController);

    Etapa_pruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Etapa_prueba', 'Etapa', 'Pruebas'];

    function Etapa_pruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, Etapa_prueba, Etapa, Pruebas) {
        var vm = this;

        vm.etapa_prueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:etapa_pruebaUpdate', function(event, result) {
            vm.etapa_prueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
