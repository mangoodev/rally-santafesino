(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('CarreraClaseDialogController', CarreraClaseDialogController);

    CarreraClaseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarreraClase', 'Carrera', 'Clase'];

    function CarreraClaseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CarreraClase, Carrera, Clase) {
        var vm = this;

        vm.carreraClase = entity;
        vm.clear = clear;
        vm.save = save;
        vm.carreras = Carrera.query();
        vm.clases = Clase.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carreraClase.id !== null) {
                CarreraClase.update(vm.carreraClase, onSaveSuccess, onSaveError);
            } else {
                CarreraClase.save(vm.carreraClase, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:carreraClaseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
