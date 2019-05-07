(function () {
	'use strict';

	angular
		.module('marsApp')
		.config(localStorageConfig);

	localStorageConfig.$inject = ['$localStorageProvider', '$sessionStorageProvider'];

	function localStorageConfig($localStorageProvider, $sessionStorageProvider) {
		$localStorageProvider.setKeyPrefix('jhi-');
		$sessionStorageProvider.setKeyPrefix('jhi-');
	}
})();
