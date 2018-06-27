(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('LocalidadCarreraDeleteController',LocalidadCarreraDeleteController);

    LocalidadCarreraDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocalidadCarrera'];

    function LocalidadCarreraDeleteController($uibModalInstance, entity, LocalidadCarrera) {
        var vm = this;

        vm.localidadCarrera = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocalidadCarrera.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
