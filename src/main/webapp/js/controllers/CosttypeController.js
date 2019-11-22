app.controller("costtypeController1",function($scope,costService,$location,$timeout,$window){
	
		var fun1=function(){
	     	var promise=costService.getcostExistingData();
	    	promise.then(function(response){
	    		console.log(response.data);
	    		$scope.costtypeDetails=response.data;
	    		
	    		
	    		console.log($scope.costtypeDetails);
	    	});
	     	
	  }
	
	fun1();
	$timeout(fun1);
	
	
	
	
	
	
	
	
	
	
	
	
	// $scope.costtypeDetails=costService.query();
	 console.log($scope.costtypeDetails);
	 
	
	
	$scope.editCosttype=function(cost){
	$scope.clickedCostType=cost;
}

$scope.updateCosttype=function(){
console.log($scope.clickedCostType);
	var val={
			costtype:$scope.clickedCostType.costType
	}
	console.log($scope.clickedCostType.id);
	costService.edit(val,$scope.clickedCostType.id);
	console.log("ggggggg");
	//$window.location.reload();	
	fun1();
}

});

 
app.controller("costtypeController",function($scope,$location,costtypeService){
	
	console.log("Controller");
	
	$scope.costTypeSave=function(){
		var cvalue={
				costtype :$scope.costtype
		};
		costtypeService.sendCostData(cvalue);
		$location.path('/Listcosttype');

	};
});
 
 
 
 
		
		
		
		
		
	
		
	


