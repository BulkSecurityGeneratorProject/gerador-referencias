(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('MonografiaDetailController', MonografiaDetailController);

    MonografiaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Monografia'];

    function MonografiaDetailController($scope, $rootScope, $stateParams, previousState, entity, Monografia) {
        var vm = this;

        vm.monografia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('geradorReferenciasApp:monografiaUpdate', function(event, result) {
            vm.monografia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
