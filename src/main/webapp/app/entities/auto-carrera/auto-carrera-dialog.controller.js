(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_carreraDialogController', Auto_carreraDialogController);

    Auto_carreraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Auto_carrera', 'Auto', 'Carrera'];

    function Auto_carreraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Auto_carrera, Auto, Carrera) {
        var vm = this;

        vm.auto_carrera = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.auto_carrera.id !== null) {
                Auto_carrera.update(vm.auto_carrera, onSaveSuccess, onSaveError);
            } else {
                Auto_carrera.save(vm.auto_carrera, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:auto_carreraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.creation_date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
