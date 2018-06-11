(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoDetailController', AutoDetailController);

    AutoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Auto', 'Persona'];

    function AutoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Auto, Persona) {
        var vm = this;

        vm.auto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('rallyApp:autoUpdate', function(event, result) {
            vm.auto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
