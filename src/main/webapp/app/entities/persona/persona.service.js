(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Persona', Persona);

    Persona.$inject = ['$resource'];

    function Persona ($resource) {
        var resourceUrl =  'api/personas/:id';

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
