(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Auto_carrear', Auto_carrear);

    Auto_carrear.$inject = ['$resource'];

    function Auto_carrear ($resource) {
        var resourceUrl =  'api/auto-carrears/:id';

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
