(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('CoordenadaTrayecto', CoordenadaTrayecto);

    CoordenadaTrayecto.$inject = ['$resource'];

    function CoordenadaTrayecto ($resource) {
        var resourceUrl =  'api/coordenada-trayectos/:id';

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
