(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraClaseDetailController', CarreraClaseDetailController);

    CarreraClaseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CarreraClase', 'Carrera', 'Clase'];

    function CarreraClaseDetailController($scope, $rootScope, $stateParams, previousState, entity, CarreraClase, Carrera, Clase) {
        var vm = this;

        vm.carreraClase = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:carreraClaseUpdate', function(event, result) {
            vm.carreraClase = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
