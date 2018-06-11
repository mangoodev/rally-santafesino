(function() {
    'use strict';

    angular
        .module('rallyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('persona', {
            parent: 'entity',
            url: '/persona?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Personas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/persona/personas.html',
                    controller: 'PersonaController',
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
        .state('persona-detail', {
            parent: 'persona',
            url: '/persona/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Persona'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/persona/persona-detail.html',
                    controller: 'PersonaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Persona', function($stateParams, Persona) {
                    return Persona.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'persona',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('persona-detail.edit', {
            parent: 'persona-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-dialog.html',
                    controller: 'PersonaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Persona', function(Persona) {
                            return Persona.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('persona.new', {
            parent: 'persona',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-dialog.html',
                    controller: 'PersonaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido: null,
                                edad: null,
                                sexo: null,
                                redes: null,
                                foto: null,
                                fotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('persona', null, { reload: 'persona' });
                }, function() {
                    $state.go('persona');
                });
            }]
        })
        .state('persona.edit', {
            parent: 'persona',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-dialog.html',
                    controller: 'PersonaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Persona', function(Persona) {
                            return Persona.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('persona', null, { reload: 'persona' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('persona.delete', {
            parent: 'persona',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/persona/persona-delete-dialog.html',
                    controller: 'PersonaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Persona', function(Persona) {
                            return Persona.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('persona', null, { reload: 'persona' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
