(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraEtapaDetailController', CarreraEtapaDetailController);

    CarreraEtapaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CarreraEtapa', 'Carrera', 'Etapa'];

    function CarreraEtapaDetailController($scope, $rootScope, $stateParams, previousState, entity, CarreraEtapa, Carrera, Etapa) {
        var vm = this;

        vm.carreraEtapa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:carreraEtapaUpdate', function(event, result) {
            vm.carreraEtapa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
