(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pruebas', {
            parent: 'entity',
            url: '/pruebas?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pruebas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pruebas/pruebas.html',
                    controller: 'PruebasController',
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
        .state('pruebas-detail', {
            parent: 'pruebas',
            url: '/pruebas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pruebas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pruebas/pruebas-detail.html',
                    controller: 'PruebasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pruebas', function($stateParams, Pruebas) {
                    return Pruebas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pruebas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pruebas-detail.edit', {
            parent: 'pruebas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pruebas/pruebas-dialog.html',
                    controller: 'PruebasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pruebas', function(Pruebas) {
                            return Pruebas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pruebas.new', {
            parent: 'pruebas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pruebas/pruebas-dialog.html',
                    controller: 'PruebasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numeroDePrueba: null,
                                longitud: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pruebas', null, { reload: 'pruebas' });
                }, function() {
                    $state.go('pruebas');
                });
            }]
        })
        .state('pruebas.edit', {
            parent: 'pruebas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pruebas/pruebas-dialog.html',
                    controller: 'PruebasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pruebas', function(Pruebas) {
                            return Pruebas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pruebas', null, { reload: 'pruebas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pruebas.delete', {
            parent: 'pruebas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pruebas/pruebas-delete-dialog.html',
                    controller: 'PruebasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pruebas', function(Pruebas) {
                            return Pruebas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pruebas', null, { reload: 'pruebas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
