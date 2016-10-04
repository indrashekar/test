(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('UserProfileDialogController', UserProfileDialogController);

    UserProfileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserProfile', 'User'];

    function UserProfileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserProfile, User) {
        var vm = this;

        vm.userProfile = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userProfile.id !== null) {
                UserProfile.update(vm.userProfile, onSaveSuccess, onSaveError);
            } else {
                UserProfile.save(vm.userProfile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('test1App:userProfileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
