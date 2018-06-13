(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TrayectoDeleteController',TrayectoDeleteController);

    TrayectoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Trayecto'];

    function TrayectoDeleteController($uibModalInstance, entity, Trayecto) {
        var vm = this;

        vm.trayecto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Trayecto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
