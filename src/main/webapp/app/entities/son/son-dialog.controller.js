(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('SonDialogController', SonDialogController);

    SonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Son', 'Standard'];

    function SonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Son, Standard) {
        var vm = this;

        vm.son = entity;
        vm.clear = clear;
        vm.save = save;
        vm.standards = Standard.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.son.id !== null) {
                Son.update(vm.son, onSaveSuccess, onSaveError);
            } else {
                Son.save(vm.son, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('test1App:sonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
