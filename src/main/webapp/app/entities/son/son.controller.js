(function() {
    'use strict';

    angular
        .module('test1App')
        .controller('SonController', SonController);

    SonController.$inject = ['$scope', '$state', 'Son'];

    function SonController ($scope, $state, Son) {
        var vm = this;
        
        vm.sons = [];

        loadAll();

        function loadAll() {
            Son.query(function(result) {
                vm.sons = result;
            });
        }
    }
})();
