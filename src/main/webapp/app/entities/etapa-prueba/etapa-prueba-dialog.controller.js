(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Etapa_pruebaDialogController', Etapa_pruebaDialogController);

    Etapa_pruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Etapa_prueba', 'Etapa', 'Pruebas'];

    function Etapa_pruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Etapa_prueba, Etapa, Pruebas) {
        var vm = this;

        vm.etapa_prueba = entity;
        vm.clear = clear;
        vm.save = save;
        vm.etapas = Etapa.query();
        vm.pruebas = Pruebas.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.etapa_prueba.id !== null) {
                Etapa_prueba.update(vm.etapa_prueba, onSaveSuccess, onSaveError);
            } else {
                Etapa_prueba.save(vm.etapa_prueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:etapa_pruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
