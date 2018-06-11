(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraDialogController', CarreraDialogController);

    CarreraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Carrera'];

    function CarreraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Carrera) {
        var vm = this;

        vm.carrera = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carrera.id !== null) {
                Carrera.update(vm.carrera, onSaveSuccess, onSaveError);
            } else {
                Carrera.save(vm.carrera, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:carreraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
