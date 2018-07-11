(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('ClaseDeleteController',ClaseDeleteController);

    ClaseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Clase'];

    function ClaseDeleteController($uibModalInstance, entity, Clase) {
        var vm = this;

        vm.clase = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Clase.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
