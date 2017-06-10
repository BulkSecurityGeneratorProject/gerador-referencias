(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .controller('GeneratorController', GeneratorController);

    GeneratorController.$inject = ['$rootScope'];

    function GeneratorController($rootScope) {

        var vm = this;

        vm.tipoReferencia = "Monografia";
        vm.referenciaFormatada = "";
        vm.monografia = {
            authors : []
        }

        $rootScope.$watch('authors', function(newValue, oldValue) {
            vm.monografia.authors = $rootScope.authors;
        });

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
            for(var i=0; i<vm.monografia.authors.length; i++){
                if(i==3){
                    referenciaFormatada += " et al. ";
                    break;
                }
                referenciaFormatada += vm.monografia.authors[i];
                if(!(i == vm.monografia.authors.length-1) && (i < 2)){
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

            vm.referenciaFormatada = referenciaFormatada;

        }

        vm.removerAutor = function(indexElemento){
            vm.monografia.authors.splice(indexElemento, 1);
        }

    }
})();
