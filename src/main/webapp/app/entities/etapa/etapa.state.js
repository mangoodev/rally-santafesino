(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('etapa', {
            parent: 'entity',
            url: '/etapa?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Etapas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/etapa/etapas.html',
                    controller: 'EtapaController',
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
        .state('etapa-detail', {
            parent: 'etapa',
            url: '/etapa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Etapa'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/etapa/etapa-detail.html',
                    controller: 'EtapaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Etapa', function($stateParams, Etapa) {
                    return Etapa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'etapa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('etapa-detail.edit', {
            parent: 'etapa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etapa/etapa-dialog.html',
                    controller: 'EtapaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Etapa', function(Etapa) {
                            return Etapa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('etapa.new', {
            parent: 'etapa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etapa/etapa-dialog.html',
                    controller: 'EtapaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('etapa', null, { reload: 'etapa' });
                }, function() {
                    $state.go('etapa');
                });
            }]
        })
        .state('etapa.edit', {
            parent: 'etapa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etapa/etapa-dialog.html',
                    controller: 'EtapaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Etapa', function(Etapa) {
                            return Etapa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('etapa', null, { reload: 'etapa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('etapa.delete', {
            parent: 'etapa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etapa/etapa-delete-dialog.html',
                    controller: 'EtapaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Etapa', function(Etapa) {
                            return Etapa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('etapa', null, { reload: 'etapa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
