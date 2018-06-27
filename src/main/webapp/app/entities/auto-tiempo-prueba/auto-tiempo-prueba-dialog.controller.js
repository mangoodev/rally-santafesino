(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('AutoTiempoPruebaDialogController', AutoTiempoPruebaDialogController);

    AutoTiempoPruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'AutoTiempoPrueba', 'Auto', 'Tiempos', 'Pruebas'];

    function AutoTiempoPruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, AutoTiempoPrueba, Auto, Tiempos, Pruebas) {
        var vm = this;

        vm.autoTiempoPrueba = entity;
        vm.clear = clear;
        vm.save = save;
        vm.autos = Auto.query();
        vm.id_tiempos = Tiempos.query({filter: 'auto_tiempo_prueba-is-null'});
        $q.all([vm.autoTiempoPrueba.$promise, vm.id_tiempos.$promise]).then(function() {
            if (!vm.autoTiempoPrueba.id_tiemposId) {
                return $q.reject();
            }
            return Tiempos.get({id : vm.autoTiempoPrueba.id_tiemposId}).$promise;
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
            if (vm.autoTiempoPrueba.id !== null) {
                AutoTiempoPrueba.update(vm.autoTiempoPrueba, onSaveSuccess, onSaveError);
            } else {
                AutoTiempoPrueba.save(vm.autoTiempoPrueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:autoTiempoPruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
