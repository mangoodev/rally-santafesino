(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Clase', Clase);

    Clase.$inject = ['$resource'];

    function Clase ($resource) {
        var resourceUrl =  'api/clases/:id';

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
