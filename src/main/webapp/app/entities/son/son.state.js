(function() {
    'use strict';

    angular
        .module('test1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('son', {
            parent: 'entity',
            url: '/son',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sons'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/son/sons.html',
                    controller: 'SonController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('son-detail', {
            parent: 'entity',
            url: '/son/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Son'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/son/son-detail.html',
                    controller: 'SonDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Son', function($stateParams, Son) {
                    return Son.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'son',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('son-detail.edit', {
            parent: 'son-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/son/son-dialog.html',
                    controller: 'SonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Son', function(Son) {
                            return Son.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('son.new', {
            parent: 'son',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/son/son-dialog.html',
                    controller: 'SonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fisrtName: null,
                                lastName: null,
                                age: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('son', null, { reload: 'son' });
                }, function() {
                    $state.go('son');
                });
            }]
        })
        .state('son.edit', {
            parent: 'son',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/son/son-dialog.html',
                    controller: 'SonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Son', function(Son) {
                            return Son.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('son', null, { reload: 'son' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('son.delete', {
            parent: 'son',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/son/son-delete-dialog.html',
                    controller: 'SonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Son', function(Son) {
                            return Son.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('son', null, { reload: 'son' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
