(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('AutoTiempoPrueba', AutoTiempoPrueba);

    AutoTiempoPrueba.$inject = ['$resource'];

    function AutoTiempoPrueba ($resource) {
        var resourceUrl =  'api/auto-tiempo-pruebas/:id';

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
