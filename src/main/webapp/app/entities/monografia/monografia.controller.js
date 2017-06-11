(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('MonografiaController', MonografiaController);

    MonografiaController.$inject = ['Monografia', 'Principal', 'AlertService', '$localStorage', '$rootScope'];

    function MonografiaController(Monografia, Principal, AlertService, $localStorage, $rootScope) {

        var vm = this;

        vm.monografias = [];

        loadAll();

        function loadAll() {
            Principal.identity().then(function (account) {
                Monografia.getByUserId({userId:account.id}, onSuccess, onError);
            });
        }

        function onSuccess(result){
            vm.monografias = result;
        }

        function onError(error){
            AlertService.error(error.data.message);
        }
    }
})();
