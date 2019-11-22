app.controller('addLocationcontroller',function($scope, locationService) {
     console.log("addLocationcontroller running");
 
     $scope.addlocation=function(location){
    	 console.log(location);
    	 console.log("location going to services"); 
    	 locationService.addLocation(location);
		//$location.path('/ListOfNoticeperiod');
	
}
    
	
});