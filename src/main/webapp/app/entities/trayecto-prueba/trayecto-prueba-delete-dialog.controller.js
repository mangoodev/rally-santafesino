(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Trayecto_pruebaDeleteController',Trayecto_pruebaDeleteController);

    Trayecto_pruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Trayecto_prueba'];

    function Trayecto_pruebaDeleteController($uibModalInstance, entity, Trayecto_prueba) {
        var vm = this;

        vm.trayecto_prueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Trayecto_prueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
