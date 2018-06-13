(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Carrera_etapaDialogController', Carrera_etapaDialogController);

    Carrera_etapaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Carrera_etapa', 'Carrera', 'Etapa'];

    function Carrera_etapaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Carrera_etapa, Carrera, Etapa) {
        var vm = this;

        vm.carrera_etapa = entity;
        vm.clear = clear;
        vm.save = save;
        vm.carreras = Carrera.query();
        vm.etapas = Etapa.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carrera_etapa.id !== null) {
                Carrera_etapa.update(vm.carrera_etapa, onSaveSuccess, onSaveError);
            } else {
                Carrera_etapa.save(vm.carrera_etapa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:carrera_etapaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
