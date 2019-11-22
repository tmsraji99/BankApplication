app.controller('AdvSearchController',function($scope, AdvSearchService) {

	$scope.process=function(){
	var promise=AdvSearchService.getExistingData();
	console.log(promise);
	promise.then(function(response){
			console.log(response.data);
	        $scope.Details=response.data.result;
	        console.log($scope.Details);
		});
	}
	
	$scope.popupdata=function(details){
		console.log(details);
		$scope.popdata=details;
		console.log($scope.popdata);
		$scope.des=$scope.popdata.designations;
		$scope.skills=$scope.popdata.skills;
		console.log($scope.skills);
		$scope.qualifications=$scope.popdata.qualifications;
		
	}
	
	$scope.process();
});

/*app.filter("filterctrl", function ($scope,AdvSearchService) {
	$scope.users = function(){
		var existingDetails={}	
		var promise = AdvSearchService.getExistingData();
		promise.then(function(response){
		console.log("hi");
		
		$scope.existingDetails=response.data;
		console.log($scope.existingDetails);
		})
	}
	$scope.users();
			});*/

