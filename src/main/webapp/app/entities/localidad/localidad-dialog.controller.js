(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('LocalidadDialogController', LocalidadDialogController);

    LocalidadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Localidad'];

    function LocalidadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Localidad) {
        var vm = this;

        vm.localidad = entity;
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
            if (vm.localidad.id !== null) {
                Localidad.update(vm.localidad, onSaveSuccess, onSaveError);
            } else {
                Localidad.save(vm.localidad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:localidadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
