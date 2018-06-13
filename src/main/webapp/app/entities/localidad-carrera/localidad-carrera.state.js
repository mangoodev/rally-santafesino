(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('localidad-carrera', {
            parent: 'entity',
            url: '/localidad-carrera?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Localidad_carreras'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/localidad-carrera/localidad-carreras.html',
                    controller: 'Localidad_carreraController',
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
        .state('localidad-carrera-detail', {
            parent: 'localidad-carrera',
            url: '/localidad-carrera/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Localidad_carrera'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/localidad-carrera/localidad-carrera-detail.html',
                    controller: 'Localidad_carreraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Localidad_carrera', function($stateParams, Localidad_carrera) {
                    return Localidad_carrera.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'localidad-carrera',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('localidad-carrera-detail.edit', {
            parent: 'localidad-carrera-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad-carrera/localidad-carrera-dialog.html',
                    controller: 'Localidad_carreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Localidad_carrera', function(Localidad_carrera) {
                            return Localidad_carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('localidad-carrera.new', {
            parent: 'localidad-carrera',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad-carrera/localidad-carrera-dialog.html',
                    controller: 'Localidad_carreraDialogController',
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
                    $state.go('localidad-carrera', null, { reload: 'localidad-carrera' });
                }, function() {
                    $state.go('localidad-carrera');
                });
            }]
        })
        .state('localidad-carrera.edit', {
            parent: 'localidad-carrera',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad-carrera/localidad-carrera-dialog.html',
                    controller: 'Localidad_carreraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Localidad_carrera', function(Localidad_carrera) {
                            return Localidad_carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('localidad-carrera', null, { reload: 'localidad-carrera' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('localidad-carrera.delete', {
            parent: 'localidad-carrera',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad-carrera/localidad-carrera-delete-dialog.html',
                    controller: 'Localidad_carreraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Localidad_carrera', function(Localidad_carrera) {
                            return Localidad_carrera.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('localidad-carrera', null, { reload: 'localidad-carrera' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
