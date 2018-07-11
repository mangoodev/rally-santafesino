(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('CarreraClase', CarreraClase);

    CarreraClase.$inject = ['$resource'];

    function CarreraClase ($resource) {
        var resourceUrl =  'api/carrera-clases/:id';

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
