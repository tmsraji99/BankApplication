
app.controller('editClientController',function($scope,clientService1,$location,$timeout,$routeParams,clientService,$http,$window){
       console.log("editclientcontroller");
		 $rootScope.id12=$routeParams.id;  
		 $scope.clienttype=true;
		 $scope.clienttype1=false;
		 $scope.showcity=true;
		 $scope.showcity11=true;
		 
		 $scope.showfun1=function(){
			  $scope.showcity=false;
			  $scope.showcity1=true;
		 }
		 
		 	$scope.showfun2=function(){
				$scope.showcity11=false;
				$scope.showcity12=true;
			}
		 
		 
		 
		var promise=clientService1.search($rootScope.id12);
		promise.then(function(response){
			$scope.client1=response.data.result;
			console.log($scope.client1);
			$scope.clientType1 = $scope.client1.customer.customerType;
			$scope.clientType = $scope.client1.customer.id;
			$scope.Country = $scope.client1.addressDetails[0].country;
			$scope.state = $scope.client1.addressDetails[0].state;
			$scope.city = $scope.client1.addressDetails[0].city;
			$scope.pincode = $scope.client1.addressDetails[0].pincode;
			$scope.addressLane2 = $scope.client1.addressDetails[0].addressLane2;
			$scope.addressLane1 = $scope.client1.addressDetails[0].addressLane1;
			
			$scope.Country1 = $scope.client1.addressDetails[0].country;
			$scope.state1 = $scope.client1.addressDetails[0].state;
			$scope.city1 = $scope.client1.addressDetails[0].city;
			$scope.pincode1 = $scope.client1.addressDetails[0].pincode;
			$scope.addressLane21 = $scope.client1.addressDetails[0].addressLane2;
			$scope.addressLane11 = $scope.client1.addressDetails[0].addressLane1;	
		});



		$scope.getClientType = function(){
			$scope.clienttype=false;
			$scope.clienttype1=true;
			var promise=clientService.getClientType();
			promise.then(function(data) {
				console.log("hai this is customer controller");
				console.log(data.data);
				$scope.customerTypeData=data.data.result;
				console.log($scope.customerTypeData);
			});
			};

		 

	$scope.ok=false;	
	$scope.updateclientdata = function(){
		$scope.responseMes="Proceesing...";
		var clientdetails={
				clientName:$scope.client1.clientName,
				customerType:{
					id:$scope.clientType
					},
				phone:$scope.client1.phone,
				email:$scope.client1.email,
				addressDetails: [{
						addressLane1: $scope.Address,
						addressLane2: $scope.addressLane2,
						city: $scope.city,
						country: $scope.Country,
						state: $scope.state,
						pincode: $scope.pincode,
						typeOfAddress: "billing"
					},
					{
						addressLane1: $scope.Address1,
						addressLane2: $scope.addressLane21,
						city: $scope.city1,
						country: $scope.Country1,
						state: $scope.state1,
						pincode: $scope.pincode1,
						typeOfAddress: "shipping"
				}]
			};	




         
					  if(clientdetails==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  swal( $scope.mes1);
						  return;
						  
					  }
					                                  
					         $http.post('rest/client/'+ $rootScope.id12,clientdetails) .then(function(response) {
                              console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.responseMes="Updated Successfully";	
                                 $scope.ok=true;								
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }
						  
                          
                             }); 


			

	// clientService.editClient(clientdetails,$scope.id12);
	// console.log(clientdetails);
	// console.log($scope.id12);
	// $location.path("/getclient");
	}
	
	
	
	$scope.redirect=function(){
		
		$location.path("/getclient");
		$window.location.reload();
	}
	
	
	$scope.getDetails=function(){
	var pin=$scope.pincode;
	var promise=clientService.getDetails(pin);
	promise.then(function(response){
		$scope.client=response.Data;
			console.log($scope.client);
			$scope.Country = $scope.client[1].Country;
			$scope.state = $scope.client[1].State;
			$scope.city = $scope.client[1].City;
	});
};
$scope.getDetails1=function(){
	var pin=$scope.pincode1;
	var promise=clientService.getDetails1(pin);
	promise.then(function(result){
		$scope.client11=result.Data;
		console.log($scope.client11);
		$scope.Country1 = $scope.client11[1].Country;
		console.log($scope.Country1);
		$scope.state1 = $scope.client11[1].State;
		$scope.city1 = $scope.client11[1].City;
	});	
};
});
