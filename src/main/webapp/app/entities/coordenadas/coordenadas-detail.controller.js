(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CoordenadasDetailController', CoordenadasDetailController);

    CoordenadasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Coordenadas'];

    function CoordenadasDetailController($scope, $rootScope, $stateParams, previousState, entity, Coordenadas) {
        var vm = this;

        vm.coordenadas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:coordenadasUpdate', function(event, result) {
            vm.coordenadas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
