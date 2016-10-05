(function() {
    'use strict';

    angular
        .module('test1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('standard', {
            parent: 'entity',
            url: '/standard',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Standards'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/standard/standards.html',
                    controller: 'StandardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('standard-detail', {
            parent: 'entity',
            url: '/standard/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Standard'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/standard/standard-detail.html',
                    controller: 'StandardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Standard', function($stateParams, Standard) {
                    return Standard.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'standard',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('standard-detail.edit', {
            parent: 'standard-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/standard/standard-dialog.html',
                    controller: 'StandardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Standard', function(Standard) {
                            return Standard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('standard.new', {
            parent: 'standard',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/standard/standard-dialog.html',
                    controller: 'StandardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                standard: null,
                                division: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('standard', null, { reload: 'standard' });
                }, function() {
                    $state.go('standard');
                });
            }]
        })
        .state('standard.edit', {
            parent: 'standard',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/standard/standard-dialog.html',
                    controller: 'StandardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Standard', function(Standard) {
                            return Standard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('standard', null, { reload: 'standard' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('standard.delete', {
            parent: 'standard',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/standard/standard-delete-dialog.html',
                    controller: 'StandardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Standard', function(Standard) {
                            return Standard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('standard', null, { reload: 'standard' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
