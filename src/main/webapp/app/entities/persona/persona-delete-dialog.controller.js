(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('PersonaDeleteController',PersonaDeleteController);

    PersonaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Persona'];

    function PersonaDeleteController($uibModalInstance, entity, Persona) {
        var vm = this;

        vm.persona = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Persona.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
