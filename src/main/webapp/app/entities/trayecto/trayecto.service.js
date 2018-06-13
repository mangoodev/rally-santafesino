(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Trayecto', Trayecto);

    Trayecto.$inject = ['$resource'];

    function Trayecto ($resource) {
        var resourceUrl =  'api/trayectos/:id';

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
