(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('LocalidadDeleteController',LocalidadDeleteController);

    LocalidadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Localidad'];

    function LocalidadDeleteController($uibModalInstance, entity, Localidad) {
        var vm = this;

        vm.localidad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Localidad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
