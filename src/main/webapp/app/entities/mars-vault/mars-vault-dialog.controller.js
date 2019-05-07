(function () {
	'use strict';

	angular
		.module('marsApp')
		.controller('MarsVaultDialogController', MarsVaultDialogController);

	MarsVaultDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModal', '$uibModalInstance', 'entity', 'MarsVault', 'User'];

	function MarsVaultDialogController($timeout, $scope, $stateParams, $uibModal, $uibModalInstance, entity, MarsVault, User) {
		var vm = this;

		vm.marsVault = entity;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.openPwdGenModal = openPwdGenModal;
		vm.save = save;
		vm.clear = clear;
		vm.users = User.query();
		vm.pwdVisible = false;

		$timeout(function () {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function openPwdGenModal() {
			$uibModal.open({
				templateUrl: 'app/entities/mars-vault/mars-vault-pwd-gen.html',
				controller: 'MarsVaultPwdGenController',
				controllerAs: 'vm',
				backdrop: 'static',
				size: 'sm'
			}).result.then(function (password) {
				vm.marsVault.password = password;
			}, function () {
			});
		}

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if (vm.marsVault.id !== null) {
				MarsVault.update(vm.marsVault, onSaveSuccess, onSaveError);
			} else {
				MarsVault.save(vm.marsVault, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('marsApp:MarsVaultUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.datePickerOpenStatus.createdDate = false;
		vm.datePickerOpenStatus.lastModifiedDate = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}
	}
})();
