(function() {
    'use strict';

    angular
        .module('rallyApp')
        .controller('Auto_carrearDialogController', Auto_carrearDialogController);

    Auto_carrearDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Auto_carrear'];

    function Auto_carrearDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Auto_carrear) {
        var vm = this;

        vm.auto_carrear = entity;
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
            if (vm.auto_carrear.id !== null) {
                Auto_carrear.update(vm.auto_carrear, onSaveSuccess, onSaveError);
            } else {
                Auto_carrear.save(vm.auto_carrear, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rallyApp:auto_carrearUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
