(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_carrearDeleteController',Auto_carrearDeleteController);

    Auto_carrearDeleteController.$inject = ['$uibModalInstance', 'entity', 'Auto_carrear'];

    function Auto_carrearDeleteController($uibModalInstance, entity, Auto_carrear) {
        var vm = this;

        vm.auto_carrear = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Auto_carrear.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
