(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('PruebasDetailController', PruebasDetailController);

    PruebasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pruebas'];

    function PruebasDetailController($scope, $rootScope, $stateParams, previousState, entity, Pruebas) {
        var vm = this;

        vm.pruebas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:pruebasUpdate', function(event, result) {
            vm.pruebas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
