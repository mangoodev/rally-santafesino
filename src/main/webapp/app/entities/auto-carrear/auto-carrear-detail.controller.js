(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_carrearDetailController', Auto_carrearDetailController);

    Auto_carrearDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Auto_carrear'];

    function Auto_carrearDetailController($scope, $rootScope, $stateParams, previousState, entity, Auto_carrear) {
        var vm = this;

        vm.auto_carrear = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rallyApp:auto_carrearUpdate', function(event, result) {
            vm.auto_carrear = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
