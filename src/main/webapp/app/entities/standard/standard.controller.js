(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('StandardController', StandardController);

    StandardController.$inject = ['$scope', '$state', 'Standard'];

    function StandardController ($scope, $state, Standard) {
        var vm = this;
        
        vm.standards = [];

        loadAll();

        function loadAll() {
            Standard.query(function(result) {
                vm.standards = result;
            });
        }
    }
})();
