app.controller("designationController",function($scope,$location,designService){
	
	console.log("designationController");
	
	$scope.desigSave=function(){
		var value={
				designation :$scope.designation
		};
		designService.sendData(value);
		$location.path('/roles');
	};
		
	
});


