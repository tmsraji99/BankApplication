app.controller('GetClientAddressController',function($scope,$routeParams, addressService,$rootScope,$timeout) {
 var iddd=$routeParams.id1;
 console.log(iddd);
 var getaddressfun = function(){
 var promise = addressService.getContactAddressType( iddd);
	promise.then(function(response) {
		$scope.AddressTypeListvalue = response.data.result;
		console.log($scope.AddressTypeListvalue);
	});
 }
 getaddressfun();
	
	
	//get popup address data
$scope.popupdata=function(id){
	console.log(id);
	var promise1=addressService.getTypeOfAddress(id);
	promise1.then(function(response){
		console.log(response.data.result);
        $scope.editgetDetails=response.data.result;
        console.log($scope.editgetDetails);	       
        $scope.pin=$scope.editgetDetails.pincode;
        $scope.count=$scope.editgetDetails.country;
        $scope.stat=$scope.editgetDetails.state;
        $scope.cit=$scope.editgetDetails.city;
        $scope.addressLane=$scope.editgetDetails.addressLane1;
        $scope.address=$scope.editgetDetails.addressLane2;
        $rootScope.contact_id=$scope.editgetDetails.addContact.id;
        $scope.address_id=$scope.editgetDetails.typeOfAddress.id;      
	});
}

//update popup address data
$scope.UpdateAddress=function(id) {	
	editvalues = {
			addContact:{
				id:$rootScope.contact_id
				},			
			addressLane1:$scope.addressLane,
			addressLane2:$scope.address,
			city:$scope.cit,
			country:$scope.count,
			pincode:$scope.pin,
			state:$scope.stat,
			typeOfAddress:{
				id:$scope.address_id
			}
	};
	console.log(editvalues);
	addressService.addAddressEditValues(editvalues, id);
	 getaddressfun();
 }

});