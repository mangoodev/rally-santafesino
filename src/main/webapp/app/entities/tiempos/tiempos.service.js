(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Tiempos', Tiempos);

    Tiempos.$inject = ['$resource'];

    function Tiempos ($resource) {
        var resourceUrl =  'api/tiempos/:id';

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
