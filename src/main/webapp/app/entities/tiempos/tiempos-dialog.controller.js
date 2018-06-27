(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TiemposDialogController', TiemposDialogController);

    TiemposDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tiempos', 'AutoTiempoPrueba'];

    function TiemposDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tiempos, AutoTiempoPrueba) {
        var vm = this;

        vm.tiempos = entity;
        vm.clear = clear;
        vm.save = save;
        vm.autotiempopruebas = AutoTiempoPrueba.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tiempos.id !== null) {
                Tiempos.update(vm.tiempos, onSaveSuccess, onSaveError);
            } else {
                Tiempos.save(vm.tiempos, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:tiemposUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
