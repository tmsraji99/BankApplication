app.controller('StatusController',function($scope, statusService,$location) {
     console.log("Status Controller");
     
     
     
     $scope.addStatus=function(Status){
    	 console.log(Status);
    	 console.log("Status going to services"); 
    	 statusService.addStatus(Status);
		$location.path('/ListOfStatus');
	
}
    
	
});
