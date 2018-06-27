(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('LocalidadCarrera', LocalidadCarrera);

    LocalidadCarrera.$inject = ['$resource'];

    function LocalidadCarrera ($resource) {
        var resourceUrl =  'api/localidad-carreras/:id';

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
