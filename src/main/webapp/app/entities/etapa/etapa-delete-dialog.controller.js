(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('EtapaDeleteController',EtapaDeleteController);

    EtapaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Etapa'];

    function EtapaDeleteController($uibModalInstance, entity, Etapa) {
        var vm = this;

        vm.etapa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Etapa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
