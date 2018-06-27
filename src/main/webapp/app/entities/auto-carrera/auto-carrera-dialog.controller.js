(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoCarreraDialogController', AutoCarreraDialogController);

    AutoCarreraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AutoCarrera', 'Auto', 'Carrera'];

    function AutoCarreraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AutoCarrera, Auto, Carrera) {
        var vm = this;

        vm.autoCarrera = entity;
        vm.clear = clear;
        vm.save = save;
        vm.autos = Auto.query();
        vm.carreras = Carrera.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.autoCarrera.id !== null) {
                AutoCarrera.update(vm.autoCarrera, onSaveSuccess, onSaveError);
            } else {
                AutoCarrera.save(vm.autoCarrera, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:autoCarreraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
