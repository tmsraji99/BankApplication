
app.controller('AddClientAddressController',function($scope,$location,$window,$rootScope, clientContactService,addressService, clientService) {
	
	$scope.view1=true;
	$scope.view2=false;
	$scope.hide1=false;
	
	$scope.pageSize='10';
	$scope.sortColumn= "lastUpdatedDate";
	$scope.reverseSort= true;
	$scope.sortData= function(column){
		
		$scope.reverseSort = ($scope.sortColumn == column) ? !$scope.reverseSort :false;
		$scope.sortColumn= column;
	}
	$scope.getSortClass= function(column){
		if($scope.sortColumn == column) {
			return $scope.reverseSort ? 'arrow-down' :'arrow-up'
		}
		return '';
	}
	//pagination
/*	 $scope.isLoading=false;	  
	  if($rootScope.loading==true){
		   
	  $scope.mystyle={'opacity':'0.5'};
	  }
	  else{
		  $scope.mystyle={'opacity':'1'};
	  }
*/
	  $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.
	    
//get client contacts
$scope.getDetails=function(){
	
	 $scope.isLoading=true;
	var promise=clientContactService.getData($scope.pageIndex,$scope.pageSizeSelected,$scope.order,$scope.by,$scope.searchcategory,$scope.searchtext);
	promise.then(function(response){
		$scope.clientdata=response.data.result;
		$scope.totalCount = response.data.totalRecords; 
		$scope.noData = response.data.res;
		$scope.isLoading=false;
		console.log($scope.clientdata);
	})
}
$scope.getDetails();
$scope.searchText = function(){
	 $scope.getDetails();   
}
	$scope.clearText=function(){
		$scope.by="",
		$scope.order="",
		$scope.searchtext="",
		$scope.searchcategory="",
		$scope.getDetails(); 
	}
$scope.pageChanged = function() {
	   $scope.getDetails(); 
	          console.log('Page changed to: ' + $scope.pageIndex);
	  };

//carrying client details
$scope.getContactData=function(value){
	$window.localStorage.setItem("cname",value.contact_Name);
	$window.localStorage.setItem("cid",value.id);
	console.log(value);	
}
	$scope.xyz = $window.localStorage.getItem("cname");
	$scope.xyz1 = $window.localStorage.getItem("cid");
	console.log($window.localStorage.getItem("cname"));
	console.log($window.localStorage.getItem("cid"));

//get address types
/*$scope.getList = function() {
	AddressTypeList={}
	var promise = addressService.getAddressType();
	promise.then(function(response) {
		$scope.AddressTypeList = response.data;
		console.log($scope.AddressTypeList);
		
	});
};	
$scope.getList();*/

$scope.idfun=function(idtype1){
	var val=JSON.parse(idtype1);
	$scope.typeAddress=val.typeOfAddress;
	$scope.idtype=val.id;
}	

//get address API
$scope.getcountryDetails=function(){
	var pin=$scope.pincode;	
	var promise=clientService.getDetails(pin);
	promise.then(function(response){
		console.log("conty data...");
		$scope.client=response.Data;
			console.log($scope.client);
		console.log($scope.client[1].Country);
	})
}
$scope.getcountryDetails1=function(){
	var pin=$scope.pincode1;	
	var promise=clientService.getDetails(pin);
	promise.then(function(response){
		console.log("conty data...");
		$scope.client1=response.Data;
			console.log($scope.client1);
		console.log($scope.client1[1].Country);
	})
}



//adding address
$scope.addAddress1 = function() {
	$scope.hide1=true;
	$scope.view1=true;
	$scope.view2=true;
	address={
			pincode:$scope.pincode,
			country:$scope.client[1].Country,
			state:$scope.client[1].State,
			city:$scope.client[1].City,
			addressLane1:$scope.Address,
			addressLane2:$scope.addressLane2,
			typeOfAddress:{
				id:"1"
			},
				addContact:{
					id:$scope.xyz1
					}
		};
	console.log(address);
	addressService.addAddressValues(address);
	
	
}

$scope.addAddress2= function() {

	address={
			pincode:$scope.pincode1,
			country:$scope.client1[1].Country,
			state:$scope.client1[1].State,
			city:$scope.client1[1].City,
			addressLane1:$scope.Address1,
			addressLane2:$scope.addressLane21,
			typeOfAddress:{
				id:"2"
			},
				addContact:{
					id:$scope.xyz1
					}
		};
	console.log(address);
	addressService.addAddressValues(address);
	$location.path('/viewClientContact');
}



//getting address
$scope.getListAddress = function(id) {
	
	$scope.getidURL="#!/addClientContact/"+id;
	console.log($scope.getidURL);		
};




//dashboard redirecting
$scope.redirect=function(){
		var change = $window.localStorage.getItem("role");
		if(change=="BDM"){
			$location.path('/bdm');
		}
		else if(change=="bdmlead"){
			$location.path('/bdmlead');
		}		
	}
});

