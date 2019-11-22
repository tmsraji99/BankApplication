app.controller("editincentiveConfigCtrl",function($scope,incentivesConfigService,$routeParams,$http,$location){
	var id=$routeParams.incentivesId;
   $scope.getbyidIncentives=function(id){
	   console.log(id);
////	   
//		var promise=incentivesConfigService.getByIdIncentivesData(id);
//		promise.then(function(res){
////			if(response.status='OK'){
//				console.log(res);
///*				$scope.client1=response.data.result;
//				$scope.totalCount = response.data.totalRecords; 
//				 $scope.nodata=response.data.res;
//				 $scope.isLoading=false;
//				console.log($scope.client1);*/
////			}
//			
//		});
	   $http.get('rest/incentiveConfig/get/'+id).then(function(response){
	   debugger;
	   console.log(response);
	   $scope.incentiveConfig=response.data.result;
   })
		}
   $scope.getbyidIncentives(id);
   $scope.updateincentiveConfig=function(){
//   
		var promise=incentivesConfigService.updateIncentivesData(id,$scope.incentiveConfig);
		promise.then(function(res){
			 $scope.status=res.data.status;
			 if($scope.status=='OK'){
			    	window.swal('Updated Successfully');
			    	$location.path('/incentivesConfiguration');
			    }
//			if(response.status='OK'){
				console.log(res);
/*				$scope.client1=response.data.result;
				$scope.totalCount = response.data.totalRecords; 
				 $scope.nodata=response.data.res;
				 $scope.isLoading=false;
				console.log($scope.client1);*/
//			}
			
		});
   }
})