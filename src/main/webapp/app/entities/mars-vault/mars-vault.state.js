(function () {
	'use strict';

	angular
		.module('marsApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
			.state('mars-vault', {
				parent: 'entity',
				url: '/mars-vault?page&sort&search',
				data: {
					roles: ['USER', 'EMPLOYEE', 'MANAGER', 'ADMIN'],
					pageTitle: 'MarsVaults'
				},
				views: {
					'content@': {
						templateUrl: 'app/entities/mars-vault/mars-vaults.html',
						controller: 'MarsVaultController',
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
						}]
				}
			})
			.state('mars-vault.new', {
				parent: 'mars-vault',
				url: '/new',
				data: {
					roles: ['USER', 'EMPLOYEE', 'MANAGER', 'ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
						$uibModal.open({
							templateUrl: 'app/entities/mars-vault/mars-vault-dialog.html',
							controller: 'MarsVaultDialogController',
							controllerAs: 'vm',
							backdrop: 'static',
							size: 'lg',
							resolve: {
								entity: function () {
									return {
										site: null,
										login: null,
										password: null,
										createdDate: null,
										lastModifiedDate: null,
										id: null
									};
								}
							}
						}).result.then(function () {
							$state.go('mars-vault', null, {reload: 'mars-vault'});
						}, function () {
							$state.go('mars-vault');
						});
					}]
			})
			.state('mars-vault.edit', {
				parent: 'mars-vault',
				url: '/{id}/edit',
				data: {
					roles: ['USER', 'EMPLOYEE', 'MANAGER', 'ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
						$uibModal.open({
							templateUrl: 'app/entities/mars-vault/mars-vault-dialog.html',
							controller: 'MarsVaultDialogController',
							controllerAs: 'vm',
							backdrop: 'static',
							size: 'lg',
							resolve: {
								entity: ['MarsVault', function (MarsVault) {
										return MarsVault.get({id: $stateParams.id}).$promise;
									}]
							}
						}).result.then(function () {
							$state.go('mars-vault', null, {reload: 'mars-vault'});
						}, function () {
							$state.go('^');
						});
					}]
			})
			.state('mars-vault.delete', {
				parent: 'mars-vault',
				url: '/{id}/delete',
				data: {
					roles: ['USER', 'EMPLOYEE', 'MANAGER', 'ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
						$uibModal.open({
							templateUrl: 'app/entities/mars-vault/mars-vault-delete-dialog.html',
							controller: 'MarsVaultDeleteController',
							controllerAs: 'vm',
							size: 'md',
							resolve: {
								entity: ['MarsVault', function (MarsVault) {
										return MarsVault.get({id: $stateParams.id}).$promise;
									}]
							}
						}).result.then(function () {
							$state.go('mars-vault', null, {reload: 'mars-vault'});
						}, function () {
							$state.go('^');
						});
					}]
			});
	}

})();
