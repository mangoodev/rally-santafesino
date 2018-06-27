(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('TrayectoPrueba', TrayectoPrueba);

    TrayectoPrueba.$inject = ['$resource'];

    function TrayectoPrueba ($resource) {
        var resourceUrl =  'api/trayecto-pruebas/:id';

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
