(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('MonografiaDialogController', MonografiaDialogController);

    MonografiaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Monografia'];

    function MonografiaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Monografia) {
        var vm = this;

        vm.monografia = entity;
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
            if (vm.monografia.id !== null) {
                Monografia.update(vm.monografia, onSaveSuccess, onSaveError);
            } else {
                Monografia.save(vm.monografia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('geradorReferenciasApp:monografiaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
