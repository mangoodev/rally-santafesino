(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('PersonaDialogController', PersonaDialogController);

    PersonaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Persona'];

    function PersonaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Persona) {
        var vm = this;

        vm.persona = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.persona.id !== null) {
                Persona.update(vm.persona, onSaveSuccess, onSaveError);
            } else {
                Persona.save(vm.persona, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:personaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, persona) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        persona.foto = base64Data;
                        persona.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
