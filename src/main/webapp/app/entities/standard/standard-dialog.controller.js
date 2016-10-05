(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('StandardDialogController', StandardDialogController);

    StandardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Standard'];

    function StandardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Standard) {
        var vm = this;

        vm.standard = entity;
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
            if (vm.standard.id !== null) {
                Standard.update(vm.standard, onSaveSuccess, onSaveError);
            } else {
                Standard.save(vm.standard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('test1App:standardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
