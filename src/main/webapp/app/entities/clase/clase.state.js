(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('clase', {
            parent: 'entity',
            url: '/clase?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Clases'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clase/clases.html',
                    controller: 'ClaseController',
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
        .state('clase-detail', {
            parent: 'clase',
            url: '/clase/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Clase'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clase/clase-detail.html',
                    controller: 'ClaseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Clase', function($stateParams, Clase) {
                    return Clase.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'clase',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('clase-detail.edit', {
            parent: 'clase-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clase/clase-dialog.html',
                    controller: 'ClaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clase', function(Clase) {
                            return Clase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clase.new', {
            parent: 'clase',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clase/clase-dialog.html',
                    controller: 'ClaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('clase', null, { reload: 'clase' });
                }, function() {
                    $state.go('clase');
                });
            }]
        })
        .state('clase.edit', {
            parent: 'clase',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clase/clase-dialog.html',
                    controller: 'ClaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clase', function(Clase) {
                            return Clase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clase', null, { reload: 'clase' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clase.delete', {
            parent: 'clase',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clase/clase-delete-dialog.html',
                    controller: 'ClaseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Clase', function(Clase) {
                            return Clase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clase', null, { reload: 'clase' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
