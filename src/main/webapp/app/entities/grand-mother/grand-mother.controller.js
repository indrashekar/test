(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('GrandMotherController', GrandMotherController);

    GrandMotherController.$inject = ['$scope', '$state', 'GrandMother'];

    function GrandMotherController ($scope, $state, GrandMother) {
        var vm = this;
        
        vm.grandMothers = [];

        loadAll();

        function loadAll() {
            GrandMother.query(function(result) {
                vm.grandMothers = result;
            });
        }
    }
})();
