(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraClaseDeleteController',CarreraClaseDeleteController);

    CarreraClaseDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarreraClase'];

    function CarreraClaseDeleteController($uibModalInstance, entity, CarreraClase) {
        var vm = this;

        vm.carreraClase = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CarreraClase.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
