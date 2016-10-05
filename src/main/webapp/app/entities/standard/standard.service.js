(function() {
    'use strict';
    angular
        .module('test1App')
        .factory('Standard', Standard);

    Standard.$inject = ['$resource'];

    function Standard ($resource) {
        var resourceUrl =  'api/standards/:id';

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
