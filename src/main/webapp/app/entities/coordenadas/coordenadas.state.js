(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('coordenadas', {
            parent: 'entity',
            url: '/coordenadas?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Coordenadas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coordenadas/coordenadas.html',
                    controller: 'CoordenadasController',
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
        .state('coordenadas-detail', {
            parent: 'coordenadas',
            url: '/coordenadas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Coordenadas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coordenadas/coordenadas-detail.html',
                    controller: 'CoordenadasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Coordenadas', function($stateParams, Coordenadas) {
                    return Coordenadas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'coordenadas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('coordenadas-detail.edit', {
            parent: 'coordenadas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenadas/coordenadas-dialog.html',
                    controller: 'CoordenadasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coordenadas', function(Coordenadas) {
                            return Coordenadas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coordenadas.new', {
            parent: 'coordenadas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenadas/coordenadas-dialog.html',
                    controller: 'CoordenadasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                latitud: null,
                                longitud: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('coordenadas', null, { reload: 'coordenadas' });
                }, function() {
                    $state.go('coordenadas');
                });
            }]
        })
        .state('coordenadas.edit', {
            parent: 'coordenadas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenadas/coordenadas-dialog.html',
                    controller: 'CoordenadasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coordenadas', function(Coordenadas) {
                            return Coordenadas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coordenadas', null, { reload: 'coordenadas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coordenadas.delete', {
            parent: 'coordenadas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenadas/coordenadas-delete-dialog.html',
                    controller: 'CoordenadasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Coordenadas', function(Coordenadas) {
                            return Coordenadas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coordenadas', null, { reload: 'coordenadas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
