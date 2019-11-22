app.controller('billingModelCtrl',function($scope,$rootScope,rpoFactory) {
	$scope.isEditable = false;
	$scope.isSaveFiled = false;	
	$scope.getBillingList=function(){
			rpoFactory.billingModel().then(function(response) {
				$scope.billingList = response;
				$scope.billingListS = $scope.billingList.result;
					});
			}
	$scope.getBillingList();
	$scope.editBilling = function(){
		$scope.isEditable = true;
			}
	
	$scope.saveEditBilling = function(billingModel) {	
		
		$scope.reqObj = {
				"billingModel": billingModel
					}
			$scope.id = bList.id;
			rpoFactory.billingEditModel($scope.id,$scope.reqObj).then(function(response) {
				$scope.editBillingList = response;
				$scope.billingListS = $scope.editBillingList.result;
				$scope.getBillingList();
			});
	}
	$scope.saveV = function(){
			$scope.isSaveFiled = true;
			}
	$scope.saveValues = function(billingModel){
			$scope.isSaveFiled = false;
			$scope.reqObj = {
					"billingModel": billingModel
						}
				rpoFactory.billingSaveModel($scope.reqObj).then(function(response) {
					$scope.saveBillingList = response;
					$scope.billingListS = $scope.saveBillingList.result;
					$scope.getBillingList();
				});
		}
});
