app.controller("incentiveConfigCtrl",function($scope,incentivesConfigService ,$location,$window){
	  $scope.incentiveConfig = {
			  'is_countable':0
	  }
	  console.log($scope.incentiveConfig.is_countable);
    $scope.getIncentives=function(){
		var promise=incentivesConfigService.getincentivesData();
		promise.then(function(response){
			console.log(response); 
			$scope.lists=response.data.result;	
			$scope.status=response.data.status;
			if(response.data.status =="DataIsEmpty"){
				$scope.nodata = "No Data Found"
			}
		});
		}
    $scope.getIncentives();
    
   $scope.addIncentives=function(incentiveConfig){
	var promise=incentivesConfigService.addincentivesData(incentiveConfig);
	promise.then(function(response){
		$scope.client1=response.data.result;
		console.log($scope.client1);
		$scope.totalCount = response.data.totalRecords; 
		$scope.status=response.data.status;
		if($scope.status == 'OK'){
			window.swal("Registerd Succesfully");
			$location.path('/incentivesConfiguration');
			 $scope.getIncentives();
		}
	});
	}
  
   $scope.deleteIncentives=function(id){
		var promise=incentivesConfigService.deleteincentivesData(id);
		promise.then(function(response){
			$scope.client1=response.data.result;
			$scope.totalCount = response.data.totalRecords; 
			$scope.status=response.data.status;
			if($scope.status== 'OK'){
				window.swal("Deleted Succesfully");
				$scope.getIncentives();
			}
			console.log($scope.client1);
		});
		}
});