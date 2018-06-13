(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Trayecto_pruebaDialogController', Trayecto_pruebaDialogController);

    Trayecto_pruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trayecto_prueba', 'Pruebas', 'Trayecto'];

    function Trayecto_pruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trayecto_prueba, Pruebas, Trayecto) {
        var vm = this;

        vm.trayecto_prueba = entity;
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
            if (vm.trayecto_prueba.id !== null) {
                Trayecto_prueba.update(vm.trayecto_prueba, onSaveSuccess, onSaveError);
            } else {
                Trayecto_prueba.save(vm.trayecto_prueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:trayecto_pruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
