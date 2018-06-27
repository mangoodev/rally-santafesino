(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoCarreraDeleteController',AutoCarreraDeleteController);

    AutoCarreraDeleteController.$inject = ['$uibModalInstance', 'entity', 'AutoCarrera'];

    function AutoCarreraDeleteController($uibModalInstance, entity, AutoCarrera) {
        var vm = this;

        vm.autoCarrera = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AutoCarrera.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
