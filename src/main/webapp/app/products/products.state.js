(function () {
	'use strict';

	angular
		.module('marsApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider.state('products', {
			parent: 'app',
			url: '/products',
			data: {
				roles: []
			},
			views: {
				'content@': {
					templateUrl: 'app/products/products.html',
					controller: 'ProductsController',
					controllerAs: 'vm'
				}
			}
		});
	}
})();
