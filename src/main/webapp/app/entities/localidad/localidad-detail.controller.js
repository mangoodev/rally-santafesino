(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('LocalidadDetailController', LocalidadDetailController);

    LocalidadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Localidad'];

    function LocalidadDetailController($scope, $rootScope, $stateParams, previousState, entity, Localidad) {
        var vm = this;

        vm.localidad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:localidadUpdate', function(event, result) {
            vm.localidad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
