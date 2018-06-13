(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tiempos', {
            parent: 'entity',
            url: '/tiempos?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tiempos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tiempos/tiempos.html',
                    controller: 'TiemposController',
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
        .state('tiempos-detail', {
            parent: 'tiempos',
            url: '/tiempos/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tiempos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tiempos/tiempos-detail.html',
                    controller: 'TiemposDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tiempos', function($stateParams, Tiempos) {
                    return Tiempos.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tiempos',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tiempos-detail.edit', {
            parent: 'tiempos-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tiempos/tiempos-dialog.html',
                    controller: 'TiemposDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tiempos', function(Tiempos) {
                            return Tiempos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tiempos.new', {
            parent: 'tiempos',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tiempos/tiempos-dialog.html',
                    controller: 'TiemposDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                parcial: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tiempos', null, { reload: 'tiempos' });
                }, function() {
                    $state.go('tiempos');
                });
            }]
        })
        .state('tiempos.edit', {
            parent: 'tiempos',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tiempos/tiempos-dialog.html',
                    controller: 'TiemposDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tiempos', function(Tiempos) {
                            return Tiempos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tiempos', null, { reload: 'tiempos' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tiempos.delete', {
            parent: 'tiempos',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tiempos/tiempos-delete-dialog.html',
                    controller: 'TiemposDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tiempos', function(Tiempos) {
                            return Tiempos.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tiempos', null, { reload: 'tiempos' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
