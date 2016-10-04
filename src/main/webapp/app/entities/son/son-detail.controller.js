(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('SonDetailController', SonDetailController);

    SonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Son'];

    function SonDetailController($scope, $rootScope, $stateParams, previousState, entity, Son) {
        var vm = this;

        vm.son = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('test1App:sonUpdate', function(event, result) {
            vm.son = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
