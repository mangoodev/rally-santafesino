(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('EtapaPruebaDeleteController',EtapaPruebaDeleteController);

    EtapaPruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'EtapaPrueba'];

    function EtapaPruebaDeleteController($uibModalInstance, entity, EtapaPrueba) {
        var vm = this;

        vm.etapaPrueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EtapaPrueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
