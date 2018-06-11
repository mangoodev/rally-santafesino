(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Localidad', Localidad);

    Localidad.$inject = ['$resource'];

    function Localidad ($resource) {
        var resourceUrl =  'api/localidads/:id';

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
