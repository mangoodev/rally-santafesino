(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoDeleteController',AutoDeleteController);

    AutoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Auto'];

    function AutoDeleteController($uibModalInstance, entity, Auto) {
        var vm = this;

        vm.auto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Auto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
