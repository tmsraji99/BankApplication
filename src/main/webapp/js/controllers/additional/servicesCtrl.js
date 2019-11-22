app.controller('servicesCtrl',function($scope,$rootScope,rpoFactory) {
	$scope.isEditable = false;
	$scope.isSaveFiled = false;
	
	$scope.getServicesList=function(){
		rpoFactory.servicesGet().then(function(response) {
			$scope.serviceList = response;
			$scope.serviceListS = $scope.serviceList.result;
				});
		}
	$scope.saveV = function(){
		$scope.isSaveFiled = true;
		}
	$scope.saveValues = function(serviceName){
		$scope.isSaveFiled = false;
		$scope.reqObj = {
				"serviceName": serviceName
					}
			rpoFactory.serviceSave($scope.reqObj).then(function(response) {
				$scope.saveServiceList = response;
				$scope.serviceListS = $scope.saveServiceList.result;
				$scope.getServicesList();
			});
	}
	
	$scope.saveEditServices = function(billingModel) {	
		$scope.reqObj = {
				"billingModel": billingModel
					}
			$scope.id = bList.id;
			rpoFactory.serviceEdit($scope.id,$scope.reqObj).then(function(response) {
				$scope.editBillingList = response;
				$scope.billingListS = $scope.editBillingList.result;
				$scope.getBillingList();
			});
	}
});