(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('EtapaDialogController', EtapaDialogController);

    EtapaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Etapa'];

    function EtapaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Etapa) {
        var vm = this;

        vm.etapa = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.etapa.id !== null) {
                Etapa.update(vm.etapa, onSaveSuccess, onSaveError);
            } else {
                Etapa.save(vm.etapa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:etapaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
