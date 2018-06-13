(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('auto-carrear', {
            parent: 'entity',
            url: '/auto-carrear?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auto_carrears'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auto-carrear/auto-carrears.html',
                    controller: 'Auto_carrearController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('auto-carrear-detail', {
            parent: 'auto-carrear',
            url: '/auto-carrear/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auto_carrear'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auto-carrear/auto-carrear-detail.html',
                    controller: 'Auto_carrearDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Auto_carrear', function($stateParams, Auto_carrear) {
                    return Auto_carrear.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'auto-carrear',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('auto-carrear-detail.edit', {
            parent: 'auto-carrear-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrear/auto-carrear-dialog.html',
                    controller: 'Auto_carrearDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auto_carrear', function(Auto_carrear) {
                            return Auto_carrear.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auto-carrear.new', {
            parent: 'auto-carrear',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrear/auto-carrear-dialog.html',
                    controller: 'Auto_carrearDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('auto-carrear', null, { reload: 'auto-carrear' });
                }, function() {
                    $state.go('auto-carrear');
                });
            }]
        })
        .state('auto-carrear.edit', {
            parent: 'auto-carrear',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrear/auto-carrear-dialog.html',
                    controller: 'Auto_carrearDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auto_carrear', function(Auto_carrear) {
                            return Auto_carrear.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auto-carrear', null, { reload: 'auto-carrear' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auto-carrear.delete', {
            parent: 'auto-carrear',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrear/auto-carrear-delete-dialog.html',
                    controller: 'Auto_carrearDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Auto_carrear', function(Auto_carrear) {
                            return Auto_carrear.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auto-carrear', null, { reload: 'auto-carrear' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
