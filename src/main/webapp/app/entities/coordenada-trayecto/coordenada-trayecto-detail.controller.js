(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Coordenada_trayectoDetailController', Coordenada_trayectoDetailController);

    Coordenada_trayectoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Coordenada_trayecto', 'Coordenadas', 'Trayecto'];

    function Coordenada_trayectoDetailController($scope, $rootScope, $stateParams, previousState, entity, Coordenada_trayecto, Coordenadas, Trayecto) {
        var vm = this;

        vm.coordenada_trayecto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:coordenada_trayectoUpdate', function(event, result) {
            vm.coordenada_trayecto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
