(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Etapa_prueba', Etapa_prueba);

    Etapa_prueba.$inject = ['$resource'];

    function Etapa_prueba ($resource) {
        var resourceUrl =  'api/etapa-pruebas/:id';

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
