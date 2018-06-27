(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraEtapaDeleteController',CarreraEtapaDeleteController);

    CarreraEtapaDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarreraEtapa'];

    function CarreraEtapaDeleteController($uibModalInstance, entity, CarreraEtapa) {
        var vm = this;

        vm.carreraEtapa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CarreraEtapa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
