(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trayecto-prueba', {
            parent: 'entity',
            url: '/trayecto-prueba?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TrayectoPruebas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trayecto-prueba/trayecto-pruebas.html',
                    controller: 'TrayectoPruebaController',
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
        .state('trayecto-prueba-detail', {
            parent: 'trayecto-prueba',
            url: '/trayecto-prueba/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TrayectoPrueba'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trayecto-prueba/trayecto-prueba-detail.html',
                    controller: 'TrayectoPruebaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TrayectoPrueba', function($stateParams, TrayectoPrueba) {
                    return TrayectoPrueba.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trayecto-prueba',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trayecto-prueba-detail.edit', {
            parent: 'trayecto-prueba-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto-prueba/trayecto-prueba-dialog.html',
                    controller: 'TrayectoPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrayectoPrueba', function(TrayectoPrueba) {
                            return TrayectoPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trayecto-prueba.new', {
            parent: 'trayecto-prueba',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto-prueba/trayecto-prueba-dialog.html',
                    controller: 'TrayectoPruebaDialogController',
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
                    $state.go('trayecto-prueba', null, { reload: 'trayecto-prueba' });
                }, function() {
                    $state.go('trayecto-prueba');
                });
            }]
        })
        .state('trayecto-prueba.edit', {
            parent: 'trayecto-prueba',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto-prueba/trayecto-prueba-dialog.html',
                    controller: 'TrayectoPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrayectoPrueba', function(TrayectoPrueba) {
                            return TrayectoPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trayecto-prueba', null, { reload: 'trayecto-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trayecto-prueba.delete', {
            parent: 'trayecto-prueba',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trayecto-prueba/trayecto-prueba-delete-dialog.html',
                    controller: 'TrayectoPruebaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TrayectoPrueba', function(TrayectoPrueba) {
                            return TrayectoPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trayecto-prueba', null, { reload: 'trayecto-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
