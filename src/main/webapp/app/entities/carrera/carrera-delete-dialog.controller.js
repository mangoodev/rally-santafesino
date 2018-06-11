(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraDeleteController',CarreraDeleteController);

    CarreraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Carrera'];

    function CarreraDeleteController($uibModalInstance, entity, Carrera) {
        var vm = this;

        vm.carrera = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Carrera.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
