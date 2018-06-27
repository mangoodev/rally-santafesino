(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('LocalidadCarreraDialogController', LocalidadCarreraDialogController);

    LocalidadCarreraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocalidadCarrera', 'Carrera', 'Localidad'];

    function LocalidadCarreraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LocalidadCarrera, Carrera, Localidad) {
        var vm = this;

        vm.localidadCarrera = entity;
        vm.clear = clear;
        vm.save = save;
        vm.carreras = Carrera.query();
        vm.localidads = Localidad.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.localidadCarrera.id !== null) {
                LocalidadCarrera.update(vm.localidadCarrera, onSaveSuccess, onSaveError);
            } else {
                LocalidadCarrera.save(vm.localidadCarrera, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:localidadCarreraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
