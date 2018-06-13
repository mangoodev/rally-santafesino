(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Carrera_etapaDetailController', Carrera_etapaDetailController);

    Carrera_etapaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Carrera_etapa', 'Carrera', 'Etapa'];

    function Carrera_etapaDetailController($scope, $rootScope, $stateParams, previousState, entity, Carrera_etapa, Carrera, Etapa) {
        var vm = this;

        vm.carrera_etapa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:carrera_etapaUpdate', function(event, result) {
            vm.carrera_etapa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
