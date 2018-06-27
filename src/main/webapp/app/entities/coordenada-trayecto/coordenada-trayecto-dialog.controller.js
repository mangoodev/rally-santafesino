(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CoordenadaTrayectoDialogController', CoordenadaTrayectoDialogController);

    CoordenadaTrayectoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CoordenadaTrayecto', 'Coordenadas', 'Trayecto'];

    function CoordenadaTrayectoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CoordenadaTrayecto, Coordenadas, Trayecto) {
        var vm = this;

        vm.coordenadaTrayecto = entity;
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
            if (vm.coordenadaTrayecto.id !== null) {
                CoordenadaTrayecto.update(vm.coordenadaTrayecto, onSaveSuccess, onSaveError);
            } else {
                CoordenadaTrayecto.save(vm.coordenadaTrayecto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:coordenadaTrayectoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
