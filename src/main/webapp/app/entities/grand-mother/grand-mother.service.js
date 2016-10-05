(function() {
    'use strict';
    angular
        .module('test1App')
        .factory('GrandMother', GrandMother);

    GrandMother.$inject = ['$resource'];

    function GrandMother ($resource) {
        var resourceUrl =  'api/grand-mothers/:id';

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
