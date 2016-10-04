(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('UserProfileDetailController', UserProfileDetailController);

    UserProfileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserProfile', 'User'];

    function UserProfileDetailController($scope, $rootScope, $stateParams, previousState, entity, UserProfile, User) {
        var vm = this;

        vm.userProfile = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('test1App:userProfileUpdate', function(event, result) {
            vm.userProfile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
