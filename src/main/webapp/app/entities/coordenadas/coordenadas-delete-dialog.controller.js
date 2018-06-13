(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CoordenadasDeleteController',CoordenadasDeleteController);

    CoordenadasDeleteController.$inject = ['$uibModalInstance', 'entity', 'Coordenadas'];

    function CoordenadasDeleteController($uibModalInstance, entity, Coordenadas) {
        var vm = this;

        vm.coordenadas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Coordenadas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
