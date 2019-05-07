(function () {
	'use strict';

	angular
		.module('marsApp')
		.factory('Register', Register);

	Register.$inject = ['$resource'];

	function Register($resource) {
		return $resource('api/register', {}, {});
	}
})();
