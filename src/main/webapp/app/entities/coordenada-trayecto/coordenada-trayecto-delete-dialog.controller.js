(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Coordenada_trayectoDeleteController',Coordenada_trayectoDeleteController);

    Coordenada_trayectoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Coordenada_trayecto'];

    function Coordenada_trayectoDeleteController($uibModalInstance, entity, Coordenada_trayecto) {
        var vm = this;

        vm.coordenada_trayecto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Coordenada_trayecto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
