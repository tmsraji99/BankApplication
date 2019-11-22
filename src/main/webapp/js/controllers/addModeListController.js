app.controller("addModeListController",function($scope,addModeService,$timeout, $window) { 

	var getMode = function() {
		modeList={}
		var promise1 = addModeService.getModeData();
		promise1.then(function(response) {
			console.log(response);
			$scope.modeList = response.data.result;
			if(response.data.status == "DataIsEmpty"){
				$scope.noData = "No Mode of interviews Found";
			}
		});
	};	
	getMode();
	$timeout(getMode);
	
	
	$scope.popupdata=function(id){
		console.log(id);
		var promise1=addModeService.getEditData(id);
		promise1.then(function(response){
			console.log(response.data);
	        $scope.editDetails=response.data.result;
	        console.log($scope.editDetails);	        
	        $scope.modeofInterview=$scope.editDetails.modeofInterview;
		});
	}
	

	 $scope.UpdateCerti=function(id) {	
		update = {
				modeofInterview : $scope.modeofInterview				
		};
		console.log(update);
		addModeService.addUpdatedData(id,update);
          //$window.location.reload();	
		  getMode();
	 }
		
	

});