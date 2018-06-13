(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Localidad_carreraDialogController', Localidad_carreraDialogController);

    Localidad_carreraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Localidad_carrera', 'Carrera', 'Localidad'];

    function Localidad_carreraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Localidad_carrera, Carrera, Localidad) {
        var vm = this;

        vm.localidad_carrera = entity;
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
            if (vm.localidad_carrera.id !== null) {
                Localidad_carrera.update(vm.localidad_carrera, onSaveSuccess, onSaveError);
            } else {
                Localidad_carrera.save(vm.localidad_carrera, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:localidad_carreraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
