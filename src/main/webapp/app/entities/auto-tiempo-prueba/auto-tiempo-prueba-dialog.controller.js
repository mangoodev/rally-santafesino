(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_tiempo_pruebaDialogController', Auto_tiempo_pruebaDialogController);

    Auto_tiempo_pruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Auto_tiempo_prueba', 'Auto', 'Tiempos', 'Pruebas'];

    function Auto_tiempo_pruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Auto_tiempo_prueba, Auto, Tiempos, Pruebas) {
        var vm = this;

        vm.auto_tiempo_prueba = entity;
        vm.clear = clear;
        vm.save = save;
        vm.autos = Auto.query();
        vm.id_tiempos = Tiempos.query({filter: 'auto_tiempo_prueba-is-null'});
        $q.all([vm.auto_tiempo_prueba.$promise, vm.id_tiempos.$promise]).then(function() {
            if (!vm.auto_tiempo_prueba.id_tiemposId) {
                return $q.reject();
            }
            return Tiempos.get({id : vm.auto_tiempo_prueba.id_tiemposId}).$promise;
        }).then(function(id_tiempos) {
            vm.id_tiempos.push(id_tiempos);
        });
        vm.pruebas = Pruebas.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.auto_tiempo_prueba.id !== null) {
                Auto_tiempo_prueba.update(vm.auto_tiempo_prueba, onSaveSuccess, onSaveError);
            } else {
                Auto_tiempo_prueba.save(vm.auto_tiempo_prueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:auto_tiempo_pruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
