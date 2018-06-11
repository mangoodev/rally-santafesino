(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Etapa', Etapa);

    Etapa.$inject = ['$resource'];

    function Etapa ($resource) {
        var resourceUrl =  'api/etapas/:id';

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
