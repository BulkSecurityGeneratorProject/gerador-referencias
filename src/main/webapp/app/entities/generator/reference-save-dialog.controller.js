(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('ReferenceSaveDialogController',ReferenceSaveDialogController);

    ReferenceSaveDialogController.$inject = ['$uibModalInstance', 'Principal', 'LoginService', '$localStorage',
        '$rootScope', 'Monografia', 'AlertService', '$filter'];

    function ReferenceSaveDialogController($uibModalInstance, Principal, LoginService, $localStorage,
                                           $rootScope, Monografia, AlertService, $filter) {
        var vm = this;

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.isLogged = function(){
            if($localStorage.authenticationToken){
                return true;
            }
            return false;
        }

        vm.openLogin = function(){
            console.log("openLogin");
            LoginService.open();
        }

        vm.save = function(){
            Principal.identity().then(function (account) {
                $rootScope.referencia.userId = account.id;
                Monografia.update($rootScope.referencia, onSaveSuccess, onSaveError);
            });
        }

        function onSaveSuccess(result){
            $uibModalInstance.dismiss('cancel');
            alert($filter('translate')('geradorReferenciasApp.generator.messages.saveSuccess'))
        }

        function onSaveError(error){
            AlertService.error(error.data.message);
        }
    }
})();
