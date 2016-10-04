(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('SonDeleteController',SonDeleteController);

    SonDeleteController.$inject = ['$uibModalInstance', 'entity', 'Son'];

    function SonDeleteController($uibModalInstance, entity, Son) {
        var vm = this;

        vm.son = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Son.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
