(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Coordenadas', Coordenadas);

    Coordenadas.$inject = ['$resource'];

    function Coordenadas ($resource) {
        var resourceUrl =  'api/coordenadas/:id';

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
