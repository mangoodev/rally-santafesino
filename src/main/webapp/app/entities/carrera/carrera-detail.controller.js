(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraDetailController', CarreraDetailController);

    CarreraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Carrera'];

    function CarreraDetailController($scope, $rootScope, $stateParams, previousState, entity, Carrera) {
        var vm = this;

        vm.carrera = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:carreraUpdate', function(event, result) {
            vm.carrera = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
