(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carrera', {
            parent: 'entity',
            url: '/carrera?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carreras'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrera/carreras.html',
                    controller: 'CarreraController',
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
        .state('carrera-detail', {
            parent: 'carrera',
            url: '/carrera/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carrera'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrera/carrera-detail.html',
                    controller: 'CarreraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Carrera', function($stateParams, Carrera) {
                    return Carrera.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carrera',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carrera-detail.edit', {
            parent: 'carrera-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera/carrera-dialog.html',
                    controller: 'CarreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carrera', function(Carrera) {
                            return Carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrera.new', {
            parent: 'carrera',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera/carrera-dialog.html',
                    controller: 'CarreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                fecha: null,
                                localidad: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carrera', null, { reload: 'carrera' });
                }, function() {
                    $state.go('carrera');
                });
            }]
        })
        .state('carrera.edit', {
            parent: 'carrera',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera/carrera-dialog.html',
                    controller: 'CarreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carrera', function(Carrera) {
                            return Carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrera', null, { reload: 'carrera' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrera.delete', {
            parent: 'carrera',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera/carrera-delete-dialog.html',
                    controller: 'CarreraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Carrera', function(Carrera) {
                            return Carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrera', null, { reload: 'carrera' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
