(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('AutorController', AutorController);

    AutorController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$rootScope'];

    function AutorController ($timeout, $scope, $stateParams, $uibModalInstance, $rootScope) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;
        vm.authorsList = [];
        vm.tipoAutor = "pessoa";

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save(){
           if($rootScope.authors == undefined){
               $rootScope.authors = [];
           }

            var authorFormated = formatarAuthor();

           //Recupera lista atualizada
            vm.authorsList = vm.authorsList.concat($rootScope.authors);
            if(vm.authorsList.indexOf(authorFormated) >= 0){
                alert("Autor já está cadastrado");
            }
            else{
                //Adiciona elemento a lista
                vm.authorsList.push(authorFormated);
                //Atualiza a lista
                $rootScope.authors = vm.authorsList;
                $uibModalInstance.dismiss('cancel');
            }
        }

        function formatarAuthor(){
            var formatado = "";
            if(vm.tipoAutor == 'pessoa'){
                formatado += vm.author.lastName + ',';
                var arrayPreNames = vm.author.preNames.split(" ");
                arrayPreNames.forEach(function(item, index){
                    formatado += " "+ item.substring(0,1).toUpperCase() + ".";
                });
                return formatado;
            }
            else{
                return vm.author.entity + ".";
            }
        }

    }
})();
