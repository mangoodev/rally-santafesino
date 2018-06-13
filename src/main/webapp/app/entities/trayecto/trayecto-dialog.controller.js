(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TrayectoDialogController', TrayectoDialogController);

    TrayectoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trayecto'];

    function TrayectoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trayecto) {
        var vm = this;

        vm.trayecto = entity;
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
            if (vm.trayecto.id !== null) {
                Trayecto.update(vm.trayecto, onSaveSuccess, onSaveError);
            } else {
                Trayecto.save(vm.trayecto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:trayectoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
