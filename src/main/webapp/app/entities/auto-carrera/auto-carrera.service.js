(function() {
    'use strict';
    angular
        .module('rallyApp')
        .factory('Auto_carrera', Auto_carrera);

    Auto_carrera.$inject = ['$resource', 'DateUtils'];

    function Auto_carrera ($resource, DateUtils) {
        var resourceUrl =  'api/auto-carreras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creation_date = DateUtils.convertDateTimeFromServer(data.creation_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
