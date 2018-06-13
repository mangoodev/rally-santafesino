(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Localidad_carreraDeleteController',Localidad_carreraDeleteController);

    Localidad_carreraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Localidad_carrera'];

    function Localidad_carreraDeleteController($uibModalInstance, entity, Localidad_carrera) {
        var vm = this;

        vm.localidad_carrera = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Localidad_carrera.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
