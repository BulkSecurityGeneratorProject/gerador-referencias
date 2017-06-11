(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('GeneratorController', GeneratorController);

    GeneratorController.$inject = ['$rootScope', 'Principal', 'Monografia', 'LoginService'];

    function GeneratorController($rootScope, Principal, Monografia, LoginService) {

        var vm = this;

        vm.tipoReferencia = "Monografia";
        vm.referenciaFormatada = "";
        vm.monografia = {
            autores : []
        }
        $rootScope.referencia = {};

        $rootScope.$watch('authors', function(newValue, oldValue) {
            vm.monografia.autores = $rootScope.authors;
        });

        init();
        function init(){
            $rootScope.authors = [];
        }

        vm.isValid = function(field){
            if(field == null || field == "" || field == undefined){
                return false;
            }
            return true;
        }

        vm.formatarReferencia = function(){
            switch (vm.tipoReferencia){
                case "Monografia":
                    formatarMonografia();
            }
        }

        function formatarMonografia(){

            var referenciaFormatada = "";
            //Atores
            for(var i=0; i<vm.monografia.autores.length; i++){
                if(i==3){
                    referenciaFormatada += " et al. ";
                    break;
                }
                referenciaFormatada += vm.monografia.autores[i];
                if(!(i == vm.monografia.autores.length-1) && (i < 2)){
                    referenciaFormatada += "; ";
                }
            }
            //Titulo
            referenciaFormatada += " " + vm.monografia.titulo;
            //Subtitulo
            if(vm.isValid(vm.monografia.subtitulo)){
                referenciaFormatada += ": " + vm.monografia.subtitulo;
            }
            referenciaFormatada += ". ";
            //Ediçao
            if(vm.isValid(vm.monografia.edicao)){
                referenciaFormatada += vm.monografia.edicao + ". ";
            }
            //Local
            referenciaFormatada += vm.monografia.local + ": ";
            //Editora
            referenciaFormatada += vm.monografia.editora + ", ";
            //Data Publicaçao
            referenciaFormatada += vm.monografia.dataPublicacao + ". ";
            //Paginas
            if(vm.isValid(vm.monografia.paginas)){
                referenciaFormatada += vm.monografia.paginas + " p. ";
            }
            //Volume
            if(vm.isValid(vm.monografia.volumes)){
                referenciaFormatada += " v."+vm.monografia.volumes;
            }

            vm.monografia.referenciaFormatada = referenciaFormatada;
            $rootScope.referencia = vm.monografia;
        }

        vm.removerAutor = function(indexElemento){
            vm.monografia.autores.splice(indexElemento, 1);
        }

    }
})();
