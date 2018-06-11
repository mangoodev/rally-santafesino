(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_carreraDeleteController',Auto_carreraDeleteController);

    Auto_carreraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Auto_carrera'];

    function Auto_carreraDeleteController($uibModalInstance, entity, Auto_carrera) {
        var vm = this;

        vm.auto_carrera = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Auto_carrera.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
