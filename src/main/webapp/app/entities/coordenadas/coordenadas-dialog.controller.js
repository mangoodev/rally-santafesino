(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CoordenadasDialogController', CoordenadasDialogController);

    CoordenadasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Coordenadas'];

    function CoordenadasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Coordenadas) {
        var vm = this;

        vm.coordenadas = entity;
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
            if (vm.coordenadas.id !== null) {
                Coordenadas.update(vm.coordenadas, onSaveSuccess, onSaveError);
            } else {
                Coordenadas.save(vm.coordenadas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:coordenadasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
