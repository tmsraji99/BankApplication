app.controller("addModeController",function($scope,addModeService, $location) {
	var promise=null;
 var mode={};
	 $scope.addMode=function() {	
		mode = {
			modeofInterview : $scope.modeofInterview				
		};
		console.log(mode);
		var promise = addModeService.addModeData(mode);
		promise.then(function(response) {
			console.log(response);
		});
		$location.path("/InterviewModeList");
	 }	
});

