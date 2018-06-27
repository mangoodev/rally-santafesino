(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('TrayectoPruebaDialogController', TrayectoPruebaDialogController);

    TrayectoPruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TrayectoPrueba', 'Pruebas', 'Trayecto'];

    function TrayectoPruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TrayectoPrueba, Pruebas, Trayecto) {
        var vm = this;

        vm.trayectoPrueba = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pruebas = Pruebas.query();
        vm.trayectos = Trayecto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trayectoPrueba.id !== null) {
                TrayectoPrueba.update(vm.trayectoPrueba, onSaveSuccess, onSaveError);
            } else {
                TrayectoPrueba.save(vm.trayectoPrueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:trayectoPruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
