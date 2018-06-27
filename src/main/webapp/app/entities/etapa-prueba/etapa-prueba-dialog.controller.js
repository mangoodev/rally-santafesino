(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('EtapaPruebaDialogController', EtapaPruebaDialogController);

    EtapaPruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EtapaPrueba', 'Etapa', 'Pruebas'];

    function EtapaPruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EtapaPrueba, Etapa, Pruebas) {
        var vm = this;

        vm.etapaPrueba = entity;
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
            if (vm.etapaPrueba.id !== null) {
                EtapaPrueba.update(vm.etapaPrueba, onSaveSuccess, onSaveError);
            } else {
                EtapaPrueba.save(vm.etapaPrueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:etapaPruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
