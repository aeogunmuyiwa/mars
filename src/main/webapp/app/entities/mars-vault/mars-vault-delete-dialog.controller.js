(function () {
	'use strict';

	angular
		.module('marsApp')
		.controller('MarsVaultDeleteController', MarsVaultDeleteController);

	MarsVaultDeleteController.$inject = ['$uibModalInstance', 'entity', 'MarsVault'];

	function MarsVaultDeleteController($uibModalInstance, entity, MarsVault) {
		var vm = this;

		vm.marsVault = entity;
		vm.clear = clear;
		vm.confirmDelete = confirmDelete;

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function confirmDelete(id) {
			MarsVault.delete({id: id},
			function () {
				$uibModalInstance.close(true);
			});
		}
	}
})();
