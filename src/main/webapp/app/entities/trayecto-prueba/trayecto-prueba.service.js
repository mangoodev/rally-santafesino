(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Trayecto_prueba', Trayecto_prueba);

    Trayecto_prueba.$inject = ['$resource'];

    function Trayecto_prueba ($resource) {
        var resourceUrl =  'api/trayecto-pruebas/:id';

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
