(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('GrandMotherDeleteController',GrandMotherDeleteController);

    GrandMotherDeleteController.$inject = ['$uibModalInstance', 'entity', 'GrandMother'];

    function GrandMotherDeleteController($uibModalInstance, entity, GrandMother) {
        var vm = this;

        vm.grandMother = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GrandMother.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
