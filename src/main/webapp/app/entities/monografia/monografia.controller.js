(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('MonografiaController', MonografiaController);

    MonografiaController.$inject = ['Monografia'];

    function MonografiaController(Monografia) {

        var vm = this;

        vm.monografias = [];

        loadAll();

        function loadAll() {
            Monografia.query(function(result) {
                vm.monografias = result;
                vm.searchQuery = null;
            });
        }
    }
})();
