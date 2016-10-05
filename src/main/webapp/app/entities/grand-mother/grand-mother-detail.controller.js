(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('GrandMotherDetailController', GrandMotherDetailController);

    GrandMotherDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GrandMother'];

    function GrandMotherDetailController($scope, $rootScope, $stateParams, previousState, entity, GrandMother) {
        var vm = this;

        vm.grandMother = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('test1App:grandMotherUpdate', function(event, result) {
            vm.grandMother = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
