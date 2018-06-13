(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Auto_tiempo_prueba', Auto_tiempo_prueba);

    Auto_tiempo_prueba.$inject = ['$resource'];

    function Auto_tiempo_prueba ($resource) {
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
