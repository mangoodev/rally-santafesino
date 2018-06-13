(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carrera-etapa', {
            parent: 'entity',
            url: '/carrera-etapa?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carrera_etapas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrera-etapa/carrera-etapas.html',
                    controller: 'Carrera_etapaController',
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
        .state('carrera-etapa-detail', {
            parent: 'carrera-etapa',
            url: '/carrera-etapa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carrera_etapa'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carrera-etapa/carrera-etapa-detail.html',
                    controller: 'Carrera_etapaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Carrera_etapa', function($stateParams, Carrera_etapa) {
                    return Carrera_etapa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carrera-etapa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carrera-etapa-detail.edit', {
            parent: 'carrera-etapa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-etapa/carrera-etapa-dialog.html',
                    controller: 'Carrera_etapaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carrera_etapa', function(Carrera_etapa) {
                            return Carrera_etapa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrera-etapa.new', {
            parent: 'carrera-etapa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-etapa/carrera-etapa-dialog.html',
                    controller: 'Carrera_etapaDialogController',
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
                    $state.go('carrera-etapa', null, { reload: 'carrera-etapa' });
                }, function() {
                    $state.go('carrera-etapa');
                });
            }]
        })
        .state('carrera-etapa.edit', {
            parent: 'carrera-etapa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-etapa/carrera-etapa-dialog.html',
                    controller: 'Carrera_etapaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carrera_etapa', function(Carrera_etapa) {
                            return Carrera_etapa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrera-etapa', null, { reload: 'carrera-etapa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carrera-etapa.delete', {
            parent: 'carrera-etapa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carrera-etapa/carrera-etapa-delete-dialog.html',
                    controller: 'Carrera_etapaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Carrera_etapa', function(Carrera_etapa) {
                            return Carrera_etapa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carrera-etapa', null, { reload: 'carrera-etapa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
