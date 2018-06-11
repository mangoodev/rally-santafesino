(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoDialogController', AutoDialogController);

    AutoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Auto', 'Persona'];

    function AutoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Auto, Persona) {
        var vm = this;

        vm.auto = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.personas = Persona.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.auto.id !== null) {
                Auto.update(vm.auto, onSaveSuccess, onSaveError);
            } else {
                Auto.save(vm.auto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:autoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, auto) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        auto.foto = base64Data;
                        auto.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
