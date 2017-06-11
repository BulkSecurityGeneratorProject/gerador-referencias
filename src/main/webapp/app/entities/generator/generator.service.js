(function() {
    'use strict';
    angular
        .module('geradorReferenciasApp')
        .factory('Generator', Generator);

    Generator.$inject = ['$resource'];

    function Generator ($resource) {
        var resourceUrl =  'api/monografias/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
