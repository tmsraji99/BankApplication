app.controller("designationListController", function($scope, $location,
		designationService, designService, $timeout, $window) {
	$scope.clickedDesignation = {};
/*	var promise = designationService.getDesignationList();
	promise.then(function(response) {
		console.log(response.data);
		$scope.existingDetails = response.data.result;
		console.log($scope.existingDetails);
	});*/
	$scope.fun1 = function() {
		var promise = designationService.getDesignationList();
		promise.then(function(response) {
			console.log(response.data);
			$scope.existingDetails = response.data.result;
			if( response.data.status =="DataIsEmpty" ){
				$scope.noData = "No Roles Found";
			}
		});
	}
	$scope.fun1();
/*	$timeout($scope.fun1());
*/	$scope.editDesignation = function(exist) {
		$scope.clickedDesignation = exist;
	}
	$scope.updateDesignation = function() {
		var val = {
			designation : $scope.clickedDesignation.designation
		}
		designService.editdesignation(val, $scope.clickedDesignation.id);
		$scope.fun1();
	}
});
