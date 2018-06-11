(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('localidad', {
            parent: 'entity',
            url: '/localidad?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Localidads'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/localidad/localidads.html',
                    controller: 'LocalidadController',
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
        .state('localidad-detail', {
            parent: 'localidad',
            url: '/localidad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Localidad'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/localidad/localidad-detail.html',
                    controller: 'LocalidadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Localidad', function($stateParams, Localidad) {
                    return Localidad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'localidad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('localidad-detail.edit', {
            parent: 'localidad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad/localidad-dialog.html',
                    controller: 'LocalidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Localidad', function(Localidad) {
                            return Localidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('localidad.new', {
            parent: 'localidad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad/localidad-dialog.html',
                    controller: 'LocalidadDialogController',
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
                    $state.go('localidad', null, { reload: 'localidad' });
                }, function() {
                    $state.go('localidad');
                });
            }]
        })
        .state('localidad.edit', {
            parent: 'localidad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad/localidad-dialog.html',
                    controller: 'LocalidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Localidad', function(Localidad) {
                            return Localidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('localidad', null, { reload: 'localidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('localidad.delete', {
            parent: 'localidad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/localidad/localidad-delete-dialog.html',
                    controller: 'LocalidadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Localidad', function(Localidad) {
                            return Localidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('localidad', null, { reload: 'localidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
