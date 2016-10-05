(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('StandardDeleteController',StandardDeleteController);

    StandardDeleteController.$inject = ['$uibModalInstance', 'entity', 'Standard'];

    function StandardDeleteController($uibModalInstance, entity, Standard) {
        var vm = this;

        vm.standard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Standard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
