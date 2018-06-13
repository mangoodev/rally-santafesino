(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('PruebasDeleteController',PruebasDeleteController);

    PruebasDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pruebas'];

    function PruebasDeleteController($uibModalInstance, entity, Pruebas) {
        var vm = this;

        vm.pruebas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pruebas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
