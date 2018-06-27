(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CoordenadaTrayectoDeleteController',CoordenadaTrayectoDeleteController);

    CoordenadaTrayectoDeleteController.$inject = ['$uibModalInstance', 'entity', 'CoordenadaTrayecto'];

    function CoordenadaTrayectoDeleteController($uibModalInstance, entity, CoordenadaTrayecto) {
        var vm = this;

        vm.coordenadaTrayecto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CoordenadaTrayecto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
