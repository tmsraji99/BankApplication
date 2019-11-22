app.controller('listStatusController',function($scope, listStatusService,editStatusService,$location,$timeout,$window) {
    console.log("list Status Controller");
	$scope.statusNew={};
	$scope.clickedUser={};
	
	
	
    //$scope.statuses = listStatusService.query();
   
   	var fun1=function(){
	     	var promise=listStatusService.getStatusList();
	    	promise.then(function(response){
	    		console.log(response.data);
	    		$scope.statuses=response.data.result;
	    		
	    		
	    		console.log($scope.statuses);
	    	});
	     	
	  }
	
	fun1();
	$timeout(fun1);
	
	
   
   
   
   
   
   
   
   
   
   
    console.log($scope.statuses);
   //  $scope.status=  $scope.statuses.status;
    
    $scope.id;
    $scope.pushstatus=function(items){
    	$scope.clickedUser=items;
    }
	
	
    $scope.editstatus =function(){
		console.log("ffffffffffff");
    	console.log($scope.clickedUser);
    	editStatus1={
    			status:$scope.clickedUser.status
    			
    	}
    
    console.log($scope.clickedUser.id);
    	editStatusService.editStatus1($scope.clickedUser.id,editStatus1);
		
		//$window.location.reload();	
      fun1();
    }
 
});
	


