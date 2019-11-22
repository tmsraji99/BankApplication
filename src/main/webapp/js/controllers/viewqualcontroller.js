app.controller('listqualificationController', function($scope,
		listqualificationservice, editqualiService, $location, $window) {
	$scope.clickedUser = {};
	$scope.fun1 = function() {
		debugger;
		var promise = listqualificationservice.getqualification();
		promise.then(function(response) {
			$scope.qualificationlists = response.data.result;
			if(response.data.status == "DataIsEmpty"){
				$scope.noData = "No Qualifications Found";
			}
		});
	}
	$scope.fun1();
	/*var promise = listqualificationservice.getqualification();
	promise.then(function(response){
		$scope.qualificationlists=response.data.result;
		});	
	//$scope.qualificationlists = listqualificationservice.query();
	console.log($scope.qualificationlists);*/
	//$scope.qualificationName = $scope.qualificationlists.qualificationName;
	$scope.id;
	$scope.pushqualification = function(qualification) {
		$scope.clickedUser = qualification;
	}
	$scope.editqualification = function() {
		debugger;
		var editqualifications = {
			qualificationName : $scope.clickedUser.qualificationName
		}
		editqualiService.editqualification($scope.clickedUser.id,
				editqualifications);
		$window.location.reload();
		$scope.fun1();
	}
	$scope.inactive = function(id) {
		var promise = inactiveQualServices.inactiveStatus(id);
		promise.then(function(response) {
			$scope.clickedUser = response.data;
			$scope.qualificationlists.push($scope.clickedUser);
			$scope.qualificationlists.splice(this.id, 1);
		});
	}
});
