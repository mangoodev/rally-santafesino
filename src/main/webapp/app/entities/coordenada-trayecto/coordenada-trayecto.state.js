(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('coordenada-trayecto', {
            parent: 'entity',
            url: '/coordenada-trayecto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Coordenada_trayectos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coordenada-trayecto/coordenada-trayectos.html',
                    controller: 'Coordenada_trayectoController',
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
        .state('coordenada-trayecto-detail', {
            parent: 'coordenada-trayecto',
            url: '/coordenada-trayecto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Coordenada_trayecto'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coordenada-trayecto/coordenada-trayecto-detail.html',
                    controller: 'Coordenada_trayectoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Coordenada_trayecto', function($stateParams, Coordenada_trayecto) {
                    return Coordenada_trayecto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'coordenada-trayecto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('coordenada-trayecto-detail.edit', {
            parent: 'coordenada-trayecto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenada-trayecto/coordenada-trayecto-dialog.html',
                    controller: 'Coordenada_trayectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coordenada_trayecto', function(Coordenada_trayecto) {
                            return Coordenada_trayecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coordenada-trayecto.new', {
            parent: 'coordenada-trayecto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenada-trayecto/coordenada-trayecto-dialog.html',
                    controller: 'Coordenada_trayectoDialogController',
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
                    $state.go('coordenada-trayecto', null, { reload: 'coordenada-trayecto' });
                }, function() {
                    $state.go('coordenada-trayecto');
                });
            }]
        })
        .state('coordenada-trayecto.edit', {
            parent: 'coordenada-trayecto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenada-trayecto/coordenada-trayecto-dialog.html',
                    controller: 'Coordenada_trayectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coordenada_trayecto', function(Coordenada_trayecto) {
                            return Coordenada_trayecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coordenada-trayecto', null, { reload: 'coordenada-trayecto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coordenada-trayecto.delete', {
            parent: 'coordenada-trayecto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordenada-trayecto/coordenada-trayecto-delete-dialog.html',
                    controller: 'Coordenada_trayectoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Coordenada_trayecto', function(Coordenada_trayecto) {
                            return Coordenada_trayecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coordenada-trayecto', null, { reload: 'coordenada-trayecto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
