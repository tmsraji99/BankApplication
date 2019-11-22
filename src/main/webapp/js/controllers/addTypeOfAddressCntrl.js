app.controller("typeOfAddressController", function($scope,typeOFAddressService,$location,$timeout,$window){
	
	$scope.typeSave=function(){
		var value={
				typeOfAddress :$scope.typeOfAddress
		};
		typeOFAddressService.typeAddressSave(value);
		$location.path("/typeAddList");
	};
	
	
	var promise=null;
	  var fun1 = function() {
		AddressList={}
		var promise = typeOFAddressService.getAddressData();
		promise.then(function(response) {
			console.log(response);
			$scope.AddressList = response.data;
			console.log($scope.AddressList);

		});
	};	
	
	$scope.popupdata=function(id){
		console.log(id);
		var promise1=typeOFAddressService.getEditData(id);
		promise1.then(function(response){
			console.log(response.data);
	        $scope.editDetails=response.data;
	        console.log($scope.editDetails);	        
	        $scope.editAddressType=$scope.editDetails.typeOfAddress;
		});
		
		
	}
	

	 $scope.UpdateCerti=function(id) {	
		update = {
				typeOfAddress : $scope.editAddressType				
		};
		console.log(update);
		typeOFAddressService.addUpdatedData(id,update);
		//$scope.getList();
		
		$window.location.reload();
	 }	
	//$scope.getList();
	fun1();
	$timeout(fun1);

});