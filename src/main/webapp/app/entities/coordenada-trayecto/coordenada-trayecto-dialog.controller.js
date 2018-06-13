(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Coordenada_trayectoDialogController', Coordenada_trayectoDialogController);

    Coordenada_trayectoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Coordenada_trayecto', 'Coordenadas', 'Trayecto'];

    function Coordenada_trayectoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Coordenada_trayecto, Coordenadas, Trayecto) {
        var vm = this;

        vm.coordenada_trayecto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.coordenadas = Coordenadas.query();
        vm.trayectos = Trayecto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.coordenada_trayecto.id !== null) {
                Coordenada_trayecto.update(vm.coordenada_trayecto, onSaveSuccess, onSaveError);
            } else {
                Coordenada_trayecto.save(vm.coordenada_trayecto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:coordenada_trayectoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
