(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('CarreraEtapa', CarreraEtapa);

    CarreraEtapa.$inject = ['$resource'];

    function CarreraEtapa ($resource) {
        var resourceUrl =  'api/carrera-etapas/:id';

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
