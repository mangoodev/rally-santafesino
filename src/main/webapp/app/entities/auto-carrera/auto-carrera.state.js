(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('auto-carrera', {
            parent: 'entity',
            url: '/auto-carrera?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auto_carreras'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auto-carrera/auto-carreras.html',
                    controller: 'Auto_carreraController',
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
        .state('auto-carrera-detail', {
            parent: 'auto-carrera',
            url: '/auto-carrera/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Auto_carrera'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auto-carrera/auto-carrera-detail.html',
                    controller: 'Auto_carreraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Auto_carrera', function($stateParams, Auto_carrera) {
                    return Auto_carrera.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'auto-carrera',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('auto-carrera-detail.edit', {
            parent: 'auto-carrera-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrera/auto-carrera-dialog.html',
                    controller: 'Auto_carreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auto_carrera', function(Auto_carrera) {
                            return Auto_carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auto-carrera.new', {
            parent: 'auto-carrera',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrera/auto-carrera-dialog.html',
                    controller: 'Auto_carreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                creation_date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('auto-carrera', null, { reload: 'auto-carrera' });
                }, function() {
                    $state.go('auto-carrera');
                });
            }]
        })
        .state('auto-carrera.edit', {
            parent: 'auto-carrera',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrera/auto-carrera-dialog.html',
                    controller: 'Auto_carreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Auto_carrera', function(Auto_carrera) {
                            return Auto_carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auto-carrera', null, { reload: 'auto-carrera' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auto-carrera.delete', {
            parent: 'auto-carrera',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auto-carrera/auto-carrera-delete-dialog.html',
                    controller: 'Auto_carreraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Auto_carrera', function(Auto_carrera) {
                            return Auto_carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auto-carrera', null, { reload: 'auto-carrera' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
