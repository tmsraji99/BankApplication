app.controller("bankdetailsController",function($scope,bankdetailsservice,$location) { 
		 $scope.getbankDetails=function(){
				var promise=bankdetailsservice.getbankdetailsData();
				promise.then(function(res){
			    console.log(res.data);
			    $scope.lists=res.data.result;
			    $scope.status=res.data.status;
			    if($scope.status=='ExcepcetdDataNotAvilable'){
			    	$scope.nodata = "No Data Found";
			    }
				});	
		 }
		 $scope.getbankDetails();
		 $scope.addbankDetails=function(bankdetails){
				var promise=bankdetailsservice.addbankDetailsData(bankdetails);
				promise.then(function(res){
			    console.log(res.data);
			    $scope.status=res.data.status;
			    if($scope.status=='OK'){
			    	window.swal('Added successfully');
			    	 $location.path('/bankDetails');
					 $scope.getbankDetails();
			    }
				});	
		 }
		 $scope.deleteBankDetalis=function(id){
			 var promise=bankdetailsservice.deletebankDetailsData(id);
				promise.then(function(res){
			    console.log(res.data);
			    $scope.lists=res.data.result;
			    $scope.status=res.data.status;
			    if($scope.status=='OK'){
			    	window.swal('Deleted successfully');
					 $scope.getbankDetails();
			    }
				});	
		 }
})