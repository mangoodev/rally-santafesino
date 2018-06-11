(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Auto', Auto);

    Auto.$inject = ['$resource'];

    function Auto ($resource) {
        var resourceUrl =  'api/autos/:id';

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
