(function() {
    'use strict';

    angular
        .module('test1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('grand-mother', {
            parent: 'entity',
            url: '/grand-mother',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GrandMothers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grand-mother/grand-mothers.html',
                    controller: 'GrandMotherController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('grand-mother-detail', {
            parent: 'entity',
            url: '/grand-mother/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GrandMother'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grand-mother/grand-mother-detail.html',
                    controller: 'GrandMotherDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GrandMother', function($stateParams, GrandMother) {
                    return GrandMother.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'grand-mother',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('grand-mother-detail.edit', {
            parent: 'grand-mother-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grand-mother/grand-mother-dialog.html',
                    controller: 'GrandMotherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GrandMother', function(GrandMother) {
                            return GrandMother.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grand-mother.new', {
            parent: 'grand-mother',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grand-mother/grand-mother-dialog.html',
                    controller: 'GrandMotherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                motherName: null,
                                motherAge: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('grand-mother', null, { reload: 'grand-mother' });
                }, function() {
                    $state.go('grand-mother');
                });
            }]
        })
        .state('grand-mother.edit', {
            parent: 'grand-mother',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grand-mother/grand-mother-dialog.html',
                    controller: 'GrandMotherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GrandMother', function(GrandMother) {
                            return GrandMother.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('grand-mother', null, { reload: 'grand-mother' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grand-mother.delete', {
            parent: 'grand-mother',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grand-mother/grand-mother-delete-dialog.html',
                    controller: 'GrandMotherDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GrandMother', function(GrandMother) {
                            return GrandMother.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('grand-mother', null, { reload: 'grand-mother' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
