(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Carrera', Carrera);

    Carrera.$inject = ['$resource', 'DateUtils'];

    function Carrera ($resource, DateUtils) {
        var resourceUrl =  'api/carreras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
