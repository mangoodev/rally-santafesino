(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoCarreraDetailController', AutoCarreraDetailController);

    AutoCarreraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AutoCarrera', 'Auto', 'Carrera'];

    function AutoCarreraDetailController($scope, $rootScope, $stateParams, previousState, entity, AutoCarrera, Auto, Carrera) {
        var vm = this;

        vm.autoCarrera = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:autoCarreraUpdate', function(event, result) {
            vm.autoCarrera = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
