(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraEtapaDialogController', CarreraEtapaDialogController);

    CarreraEtapaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarreraEtapa', 'Carrera', 'Etapa'];

    function CarreraEtapaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CarreraEtapa, Carrera, Etapa) {
        var vm = this;

        vm.carreraEtapa = entity;
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
            if (vm.carreraEtapa.id !== null) {
                CarreraEtapa.update(vm.carreraEtapa, onSaveSuccess, onSaveError);
            } else {
                CarreraEtapa.save(vm.carreraEtapa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:carreraEtapaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
