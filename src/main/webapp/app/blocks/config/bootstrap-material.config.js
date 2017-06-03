(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .config(bootstrapMaterialDesignConfig);

    bootstrapMaterialDesignConfig.$inject = [];

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();
