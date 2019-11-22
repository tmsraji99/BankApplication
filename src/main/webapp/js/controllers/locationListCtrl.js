app.controller('locationListController',function($scope,locationService,$location,listqualificationservice) {
 	$scope.locationlist=function() {
		var promise = listqualificationservice.getmultiLocations();
		promise.then(function(response) {		
		$scope.locationsList=response.data;	
		if(response.data.status =="DataIsEmpty"){
		$scope.noData = "No Locations Found";
		}
	});		
	}
 	$scope.locationlist();
  });