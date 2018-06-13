(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Localidad_carreraDetailController', Localidad_carreraDetailController);

    Localidad_carreraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Localidad_carrera', 'Carrera', 'Localidad'];

    function Localidad_carreraDetailController($scope, $rootScope, $stateParams, previousState, entity, Localidad_carrera, Carrera, Localidad) {
        var vm = this;

        vm.localidad_carrera = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:localidad_carreraUpdate', function(event, result) {
            vm.localidad_carrera = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
