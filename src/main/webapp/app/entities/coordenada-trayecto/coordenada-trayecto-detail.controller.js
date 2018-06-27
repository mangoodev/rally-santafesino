(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CoordenadaTrayectoDetailController', CoordenadaTrayectoDetailController);

    CoordenadaTrayectoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CoordenadaTrayecto', 'Coordenadas', 'Trayecto'];

    function CoordenadaTrayectoDetailController($scope, $rootScope, $stateParams, previousState, entity, CoordenadaTrayecto, Coordenadas, Trayecto) {
        var vm = this;

        vm.coordenadaTrayecto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:coordenadaTrayectoUpdate', function(event, result) {
            vm.coordenadaTrayecto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
