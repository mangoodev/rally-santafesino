(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('AutoCarrera', AutoCarrera);

    AutoCarrera.$inject = ['$resource'];

    function AutoCarrera ($resource) {
        var resourceUrl =  'api/auto-carreras/:id';

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
