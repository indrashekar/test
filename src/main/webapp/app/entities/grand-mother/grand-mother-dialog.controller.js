(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('GrandMotherDialogController', GrandMotherDialogController);

    GrandMotherDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GrandMother'];

    function GrandMotherDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GrandMother) {
        var vm = this;

        vm.grandMother = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.grandMother.id !== null) {
                GrandMother.update(vm.grandMother, onSaveSuccess, onSaveError);
            } else {
                GrandMother.save(vm.grandMother, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('test1App:grandMotherUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
