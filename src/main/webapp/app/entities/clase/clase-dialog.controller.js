(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('ClaseDialogController', ClaseDialogController);

    ClaseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Clase'];

    function ClaseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Clase) {
        var vm = this;

        vm.clase = entity;
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
            if (vm.clase.id !== null) {
                Clase.update(vm.clase, onSaveSuccess, onSaveError);
            } else {
                Clase.save(vm.clase, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:claseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
