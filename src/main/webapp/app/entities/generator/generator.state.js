(function() {
    'use strict';

    angular
        .module('geradorReferenciasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('generator', {
            parent: 'entity',
            url: '/generator',
            data: {
                pageTitle: 'geradorReferenciasApp.generator.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/generator/generator.html',
                    controller: 'GeneratorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('generator');
                    $translatePartialLoader.addPart('monografia');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('autor-new', {
            parent: 'generator',
            url: '/autor/new',
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/generator/monografia/monografia-generator-autor.html',
                    controller: 'AutorController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                }).result.then(function() {
                    $state.go('generator', null, { reload: 'generator' });
                }, function() {
                    $state.go('generator');
                });
            }]
        })
            .state('reference-save', {
                parent: 'generator',
                url: '/reference/save',
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/generator/reference-save-dialog.html',
                        controller: 'ReferenceSaveDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                    }).result.then(function() {
                        $state.go('generator', null, { reload: 'generator' });
                    }, function() {
                        $state.go('generator');
                    });
                }]
            });
    }

})();
