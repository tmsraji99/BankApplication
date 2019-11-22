app.controller("editbankDeatilsController",function($scope,bankdetailsservice,$routeParams,$http,$location){
	var id=$routeParams.bankdetailsId;
   $scope.getbyidBankDetails=function(id){
	   console.log(id);
////	   
//		var promise=bankdetailsservice.getbyidbankDetailsData(id);
//		promise.then(function(res){
////			if(response.status='OK'){
//				console.log(res);
//				$scope.bankdetails=res.data.result;
//				console.log($scope.bankdetails);
///*				$scope.client1=response.data.result;
//				$scope.totalCount = response.data.totalRecords; 
//				 $scope.nodata=response.data.res;
//				 $scope.isLoading=false;
//				console.log($scope.client1);*/
////			}
//			
//		});
	   $http.get('rest/bankDetails/getbyid/'+id).then(function(res){
	   debugger;
	   console.log(res);
	   $scope.bankdetails=res.data.result;
   })
		}
   $scope.getbyidBankDetails(id);
   $scope.updateBankDetails=function(){
//   
		var promise=bankdetailsservice.updatebankDetailsData(id,$scope.bankdetails);
		promise.then(function(res){
//			if(response.status='OK'){
				console.log(res);
				$scope.status=res.data.status;
				 if($scope.status=='OK'){
				    	window.swal('Updated successfully');
				    	$location.path('/bankDetails');
				    }
/*				$scope.client1=response.data.result;
				$scope.totalCount = response.data.totalRecords; 
				 $scope.nodata=response.data.res;
				 $scope.isLoading=false;
				console.log($scope.client1);*/
//			}
			
		});
   }
})