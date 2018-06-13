(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_tiempo_pruebaDeleteController',Auto_tiempo_pruebaDeleteController);

    Auto_tiempo_pruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Auto_tiempo_prueba'];

    function Auto_tiempo_pruebaDeleteController($uibModalInstance, entity, Auto_tiempo_prueba) {
        var vm = this;

        vm.auto_tiempo_prueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Auto_tiempo_prueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
