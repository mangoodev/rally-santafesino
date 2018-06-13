(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TiemposDeleteController',TiemposDeleteController);

    TiemposDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tiempos'];

    function TiemposDeleteController($uibModalInstance, entity, Tiempos) {
        var vm = this;

        vm.tiempos = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tiempos.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
