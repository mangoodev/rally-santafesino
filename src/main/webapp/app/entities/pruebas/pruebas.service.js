(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Pruebas', Pruebas);

    Pruebas.$inject = ['$resource'];

    function Pruebas ($resource) {
        var resourceUrl =  'api/pruebas/:id';

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
