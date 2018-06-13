(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trayecto', {
            parent: 'entity',
            url: '/trayecto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Trayectos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trayecto/trayectos.html',
                    controller: 'TrayectoController',
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
        .state('trayecto-detail', {
            parent: 'trayecto',
            url: '/trayecto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Trayecto'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trayecto/trayecto-detail.html',
                    controller: 'TrayectoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Trayecto', function($stateParams, Trayecto) {
                    return Trayecto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trayecto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trayecto-detail.edit', {
            parent: 'trayecto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto/trayecto-dialog.html',
                    controller: 'TrayectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trayecto', function(Trayecto) {
                            return Trayecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trayecto.new', {
            parent: 'trayecto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto/trayecto-dialog.html',
                    controller: 'TrayectoDialogController',
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
                    $state.go('trayecto', null, { reload: 'trayecto' });
                }, function() {
                    $state.go('trayecto');
                });
            }]
        })
        .state('trayecto.edit', {
            parent: 'trayecto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto/trayecto-dialog.html',
                    controller: 'TrayectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trayecto', function(Trayecto) {
                            return Trayecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trayecto', null, { reload: 'trayecto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trayecto.delete', {
            parent: 'trayecto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto/trayecto-delete-dialog.html',
                    controller: 'TrayectoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Trayecto', function(Trayecto) {
                            return Trayecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trayecto', null, { reload: 'trayecto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
