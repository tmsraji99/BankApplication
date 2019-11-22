/*app.controller('userlistController', function(userlistServices, updateUserService,inactiveUserServices,$location,$timeout,$scope,$window) {
	
	//$scope.userlist = userlistServices.query();
	//$scope.userlist = userlistServices.query();
	//$location.path("/listOfUsers");
	var fun1=function(){
	     	var promise=userlistServices.getUserList();
	    	promise.then(function(response){
	    		
				
				console.log(response.data.result);
				
				if(response.data.status=="StatusSuccess")
				{
					console.log("StatusSuccess")
					$scope.view1=true;
					$scope.view2=false;
				}
				else{
					$scope.view1=false;
					$scope.view2=true;
				}
				
				
				
	    		$scope.userlist=response.data.result;
	    		
	    		
	    		
	    	});
	     	
	  }
	
	fun1();
	$timeout(fun1);
	
	

	$scope.userid=function(list){
		
		$scope.ListData=list;
		console.log($scope.ListData);
	}
	

	$scope.updateUserlist = function() {
		updateList={
				name:$scope.ListData.name,
				password:$scope.ListData.password,
				firstName:$scope.ListData.firstName,
				lastName:$scope.ListData.lastName,
				contactNumber:$scope.ListData.contactNumber,
				email:$scope.ListData.email,
				role:$scope.ListData.role,
				status:$scope.ListData.nstatus
		};
		
		console.log($scope.ListData.id);
		
		
		       	var promise =updateUserService.updateUserList($scope.ListData.id,updateList);
						
					promise.then(function(response){
						
						console.log(response.data);
					});					
						
						
						
			fun1();			
		//$window.location.reload();				
		//$location.path('/listOfUsers');
		
	}
	 $scope.inactive=function(id){
	     	var promise=inactiveUserServices.inactiveStatus(id);
	    	promise.then(function(response){
	    		console.log(response.data);
	    		$scope.newUserlist=response.data;
	    		$scope.userlist.push($scope.newUserlist);
	    		$scope.userlist.splice(this.id,1);
	    		
	    		console.log($scope.newUserlist.status);
	    	});
	     	
	  }
});
*/
































