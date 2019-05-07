(function () {
	'use strict';

	angular
		.module('marsApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider.state('docs', {
			parent: 'admin',
			url: '/docs',
			data: {
				roles: ['ADMIN'],
				pageTitle: 'API'
			},
			views: {
				'content@': {
					templateUrl: 'app/admin/docs/docs.html'
				}
			}
		});
	}
})();
