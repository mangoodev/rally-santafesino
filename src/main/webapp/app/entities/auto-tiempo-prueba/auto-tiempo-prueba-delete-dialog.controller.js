(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoTiempoPruebaDeleteController',AutoTiempoPruebaDeleteController);

    AutoTiempoPruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'AutoTiempoPrueba'];

    function AutoTiempoPruebaDeleteController($uibModalInstance, entity, AutoTiempoPrueba) {
        var vm = this;

        vm.autoTiempoPrueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AutoTiempoPrueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
