(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TrayectoPruebaDetailController', TrayectoPruebaDetailController);

    TrayectoPruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TrayectoPrueba', 'Pruebas', 'Trayecto'];

    function TrayectoPruebaDetailController($scope, $rootScope, $stateParams, previousState, entity, TrayectoPrueba, Pruebas, Trayecto) {
        var vm = this;

        vm.trayectoPrueba = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:trayectoPruebaUpdate', function(event, result) {
            vm.trayectoPrueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
