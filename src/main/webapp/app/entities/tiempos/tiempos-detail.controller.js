(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TiemposDetailController', TiemposDetailController);

    TiemposDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tiempos', 'Auto_tiempo_prueba'];

    function TiemposDetailController($scope, $rootScope, $stateParams, previousState, entity, Tiempos, Auto_tiempo_prueba) {
        var vm = this;

        vm.tiempos = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:tiemposUpdate', function(event, result) {
            vm.tiempos = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
