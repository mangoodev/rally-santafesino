(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('auto-tiempo-prueba', {
            parent: 'entity',
            url: '/auto-tiempo-prueba?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auto_tiempo_pruebas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auto-tiempo-prueba/auto-tiempo-pruebas.html',
                    controller: 'Auto_tiempo_pruebaController',
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
        .state('auto-tiempo-prueba-detail', {
            parent: 'auto-tiempo-prueba',
            url: '/auto-tiempo-prueba/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auto_tiempo_prueba'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auto-tiempo-prueba/auto-tiempo-prueba-detail.html',
                    controller: 'Auto_tiempo_pruebaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Auto_tiempo_prueba', function($stateParams, Auto_tiempo_prueba) {
                    return Auto_tiempo_prueba.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'auto-tiempo-prueba',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('auto-tiempo-prueba-detail.edit', {
            parent: 'auto-tiempo-prueba-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-tiempo-prueba/auto-tiempo-prueba-dialog.html',
                    controller: 'Auto_tiempo_pruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auto_tiempo_prueba', function(Auto_tiempo_prueba) {
                            return Auto_tiempo_prueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auto-tiempo-prueba.new', {
            parent: 'auto-tiempo-prueba',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-tiempo-prueba/auto-tiempo-prueba-dialog.html',
                    controller: 'Auto_tiempo_pruebaDialogController',
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
                    $state.go('auto-tiempo-prueba', null, { reload: 'auto-tiempo-prueba' });
                }, function() {
                    $state.go('auto-tiempo-prueba');
                });
            }]
        })
        .state('auto-tiempo-prueba.edit', {
            parent: 'auto-tiempo-prueba',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-tiempo-prueba/auto-tiempo-prueba-dialog.html',
                    controller: 'Auto_tiempo_pruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auto_tiempo_prueba', function(Auto_tiempo_prueba) {
                            return Auto_tiempo_prueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auto-tiempo-prueba', null, { reload: 'auto-tiempo-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auto-tiempo-prueba.delete', {
            parent: 'auto-tiempo-prueba',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-tiempo-prueba/auto-tiempo-prueba-delete-dialog.html',
                    controller: 'Auto_tiempo_pruebaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Auto_tiempo_prueba', function(Auto_tiempo_prueba) {
                            return Auto_tiempo_prueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auto-tiempo-prueba', null, { reload: 'auto-tiempo-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
