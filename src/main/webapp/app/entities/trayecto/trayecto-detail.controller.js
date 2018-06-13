(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TrayectoDetailController', TrayectoDetailController);

    TrayectoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trayecto'];

    function TrayectoDetailController($scope, $rootScope, $stateParams, previousState, entity, Trayecto) {
        var vm = this;

        vm.trayecto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:trayectoUpdate', function(event, result) {
            vm.trayecto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
