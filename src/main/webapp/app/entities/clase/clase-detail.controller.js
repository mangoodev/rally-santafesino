(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('ClaseDetailController', ClaseDetailController);

    ClaseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Clase'];

    function ClaseDetailController($scope, $rootScope, $stateParams, previousState, entity, Clase) {
        var vm = this;

        vm.clase = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:claseUpdate', function(event, result) {
            vm.clase = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
