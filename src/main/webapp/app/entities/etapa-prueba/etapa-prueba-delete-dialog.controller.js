(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Etapa_pruebaDeleteController',Etapa_pruebaDeleteController);

    Etapa_pruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Etapa_prueba'];

    function Etapa_pruebaDeleteController($uibModalInstance, entity, Etapa_prueba) {
        var vm = this;

        vm.etapa_prueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Etapa_prueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
