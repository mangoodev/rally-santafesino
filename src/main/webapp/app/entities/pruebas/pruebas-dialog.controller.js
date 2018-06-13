(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('PruebasDialogController', PruebasDialogController);

    PruebasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pruebas'];

    function PruebasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pruebas) {
        var vm = this;

        vm.pruebas = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pruebas.id !== null) {
                Pruebas.update(vm.pruebas, onSaveSuccess, onSaveError);
            } else {
                Pruebas.save(vm.pruebas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:pruebasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
