(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TrayectoPruebaDeleteController',TrayectoPruebaDeleteController);

    TrayectoPruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'TrayectoPrueba'];

    function TrayectoPruebaDeleteController($uibModalInstance, entity, TrayectoPrueba) {
        var vm = this;

        vm.trayectoPrueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TrayectoPrueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
