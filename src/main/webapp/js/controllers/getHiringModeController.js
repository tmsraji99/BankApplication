/*app.controller('getHiringModeControllers',function($scope, getHiringModeService,$location) {
    console.log("get HiringMode Controller");
	$scope.HiringModeNew={};
	$scope.clickedUser={};
    $scope.HiringModes =getHiringModeService.query();
   
    console.log($scope.HiringModes);
    $scope.HiringModeName= $scope.HiringModes.HiringModeName;
    
    $scope.id;
    $scope.sendHiringModes=function(hiringmodes){
    	$scope.clickedUser=hiringmodes;
    }
	
	
    
    $scope.updatehiringmode=function(){
    	console.log($scope.clickedUser);
    	updatehiringtype={
    			HiringModeName:$scope.clickedUser.HiringModeName
    	}
    
    console.log($scope.clickedUser.id);
    	updatehiringmodeService.updatehiringmode($scope.clickedUser.id,updatehiringtype);
   
    }
});*/

app.controller('getHiringModeControllers',function($scope, getHiringModeService) {
	//var promise = getHiringModeService.getModeDetails();
	$scope.getDetails=function(){
		var existDetails={}	
		var promise = getHiringModeService.getModeDetails();
		promise.then(function(response){
		console.log("hi");
		
		$scope.existDetails=response.data;
		console.log($scope.existDetails);
		/*s*/
	}),
	
		
		
		$scope.sendHiringModes=function(id){
			var promise1 = getHiringModeService.editHiringMode(id);
			promise1.then(function(response){
				console.log("hi");
			
				$scope.EditexistDetails=response.data;
				$scope.popupdata=$scope.EditexistDetails.modeOfHiring;
				console.log($scope.EditexistDetails);
			})	
		}
	
		
	
		$scope.updateddata=function(id){		
				var update={
						modeOfHiring:$scope.popupdata
				}				
				getHiringModeService.updateHiringMode(id, update);
				$scope.getDetails();
		}
		
		

$scope.deleteHiringModeSet=function(items){
 	var promise=getHiringModeService.deleteHiringMode(items);
	promise.then(function(response){
		console.log(response.data);
		$scope.existDetails=response.data;
		//console.log(response.data.result);
		$scope.existDetails.splice(this.id,1);
		$scope.existDetails.push($scope.EditexistDetails);
		console.log($scope.existDetails.status);
	});
	$scope.getDetails();
}

	}
	$scope.getDetails();

});
	
