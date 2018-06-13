(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Carrera_etapaDeleteController',Carrera_etapaDeleteController);

    Carrera_etapaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Carrera_etapa'];

    function Carrera_etapaDeleteController($uibModalInstance, entity, Carrera_etapa) {
        var vm = this;

        vm.carrera_etapa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Carrera_etapa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
