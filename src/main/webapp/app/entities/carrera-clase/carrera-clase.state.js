(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carrera-clase', {
            parent: 'entity',
            url: '/carrera-clase?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CarreraClases'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrera-clase/carrera-clases.html',
                    controller: 'CarreraClaseController',
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
        .state('carrera-clase-detail', {
            parent: 'carrera-clase',
            url: '/carrera-clase/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CarreraClase'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrera-clase/carrera-clase-detail.html',
                    controller: 'CarreraClaseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CarreraClase', function($stateParams, CarreraClase) {
                    return CarreraClase.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carrera-clase',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carrera-clase-detail.edit', {
            parent: 'carrera-clase-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-clase/carrera-clase-dialog.html',
                    controller: 'CarreraClaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarreraClase', function(CarreraClase) {
                            return CarreraClase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrera-clase.new', {
            parent: 'carrera-clase',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-clase/carrera-clase-dialog.html',
                    controller: 'CarreraClaseDialogController',
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
                    $state.go('carrera-clase', null, { reload: 'carrera-clase' });
                }, function() {
                    $state.go('carrera-clase');
                });
            }]
        })
        .state('carrera-clase.edit', {
            parent: 'carrera-clase',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-clase/carrera-clase-dialog.html',
                    controller: 'CarreraClaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarreraClase', function(CarreraClase) {
                            return CarreraClase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrera-clase', null, { reload: 'carrera-clase' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrera-clase.delete', {
            parent: 'carrera-clase',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-clase/carrera-clase-delete-dialog.html',
                    controller: 'CarreraClaseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CarreraClase', function(CarreraClase) {
                            return CarreraClase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrera-clase', null, { reload: 'carrera-clase' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
