(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('EtapaDetailController', EtapaDetailController);

    EtapaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Etapa'];

    function EtapaDetailController($scope, $rootScope, $stateParams, previousState, entity, Etapa) {
        var vm = this;

        vm.etapa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:etapaUpdate', function(event, result) {
            vm.etapa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
