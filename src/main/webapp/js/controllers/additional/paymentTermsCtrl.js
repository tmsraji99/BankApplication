
app.controller('paymentTermsCtrl',function($scope,$rootScope,rpoFactory) {
	$scope.isEditable = false;
	$scope.isSaveFiled = false;
	
	$scope.getPaymentList=function(){
		rpoFactory.paymentGet().then(function(response) {
			$scope.serviceList = response;
			$scope.serviceListS = $scope.serviceList.result;
			if(response.data.status == "DataIsEmpty"){
				$scope.noData = "No Payments Found"
			}
				});
		}
	$scope.saveV = function(){
		$scope.isSaveFiled = true;
		}
	$scope.saveValues = function(paymentType){
		$scope.isSaveFiled = false;
		$scope.reqObj = {
				"paymentType": paymentType
					}
			rpoFactory.paymentSave($scope.reqObj).then(function(response) {
				$scope.saveServiceList = response;
				$scope.serviceListS = $scope.saveServiceList.result;
				$scope.getPaymentList();
			});
	}
	
	$scope.saveEditServices = function(paymentType) {	
		$scope.reqObj = {
				"paymentType": paymentType
					}
			$scope.id = bList.id;
			rpoFactory.paymentEdit($scope.id,$scope.reqObj).then(function(response) {
				$scope.editBillingList = response;
				$scope.billingListS = $scope.editBillingList.result;
				$scope.getPaymentList();
			});
	}	
});