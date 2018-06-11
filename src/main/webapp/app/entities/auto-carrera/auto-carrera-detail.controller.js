(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_carreraDetailController', Auto_carreraDetailController);

    Auto_carreraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Auto_carrera', 'Auto', 'Carrera'];

    function Auto_carreraDetailController($scope, $rootScope, $stateParams, previousState, entity, Auto_carrera, Auto, Carrera) {
        var vm = this;

        vm.auto_carrera = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:auto_carreraUpdate', function(event, result) {
            vm.auto_carrera = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
