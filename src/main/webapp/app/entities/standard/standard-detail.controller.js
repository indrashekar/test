(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('StandardDetailController', StandardDetailController);

    StandardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Standard'];

    function StandardDetailController($scope, $rootScope, $stateParams, previousState, entity, Standard) {
        var vm = this;

        vm.standard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('test1App:standardUpdate', function(event, result) {
            vm.standard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
