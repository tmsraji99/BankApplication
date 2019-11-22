
app.controller('clientController1',function($scope,$rootScope,$http,clientService1,$location,$rootScope,clientService,$window,$timeout,clientContactService){
	/*  $scope.isLoading=false;
	    if($rootScope.loading==true){		   
	  	  $scope.mystyle={'opacity':'0.5'};
	  	  }
	  	  else{
	  		  $scope.mystyle={'opacity':'1'};
	  	  }	*/
	////get client	
	    $scope.movetoRequirement=function(id,sName){
	    	$rootScope.SPOCName=sName;
	    	$rootScope.clientId=id;
	    	$location.path('/bdmreqdtls/'+id) 
	    	
	    }
		
		$scope.editFun=function(list){
			$scope.urlForEdit="#!/editBdmreqdtls/"+list.id;
		
		}
		$scope.assignFun = function(list){
			 $scope.clientname =list.client.clientName;
		 $scope.client_id =list.client.id;
	       $scope.requirementName=list.nameOfRequirement;
	         $scope.requirement_id=list.id;	   
		 
		 
		 $window.localStorage.setItem("reqclientname",$scope.clientname);
		 $window.localStorage.setItem("reqclient_id",$scope.client_id);
		 $window.localStorage.setItem("reqrequirementName",$scope.requirementName);
		 $window.localStorage.setItem("reqrequirement_id",$scope.requirement_id);

		 }
	
		  $scope.pageSize='10';
		//pagination
		  $scope.maxSize = 2;     // Limit number for pagination display number.  
		    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
		    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
		    $scope.pageSizeSelected = 10; // Maximum number of items per page.
		  /*  $scope.searchtext= $scope.searchtext;
			$scope.searchcategory = $scope.searchcategory;*/
		    $scope.isLoading=true;
	$scope.getCfun=function(){
		
		
		var promise=clientService1.clientDisplay($scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory);
		promise.then(function(response){
			
			$scope.client1=response.data.result;
			$rootScope.clienId =response.data.result
			$scope.totalCount = response.data.totalRecords; 
			 $scope.nodata=response.data.res;
			 $scope.isLoading=false;
			console.log($scope.client1);
		});
		}
	$scope.clearText=function(){
		$scope.by="",
		$scope.order="",
		$scope.searchtext="",
		$scope.searchcategory="",
		$scope.getCfun(); 
	}
	 $scope.sortorderad = function(order){
		 
		 $scope.getCfun();   
	 }
	 $scope.sortby = function(by){
		 $scope.getCfun();   
	 }
	 $scope.searchBy = function(searchcategory){
		 
		 $scope.getCfun();   
	 }
	 $scope.searchText = function(){
		 $scope.getCfun();   
	 }
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
	 /*$scope.searchclient=function(searchitem) {
			
			
			$scope.searchtext=searchitem.searchtext;
			$scope.searchcategory=searchitem.searchcategory;
			$scope.getrole = $window.localStorage.getItem("role");
			$scope.getuserid = $window.localStorage.getItem("usid");
			$http.get("rest/client/searchClients/"+$scope.getrole+"/"+$scope.getuserid+"/"+$scope.searchtext+"/"+$scope.searchcategory+"/"+$scope.pageIndex+"/"+$scope.pageSizeSelected).success(function(response){
				 $scope.totalCount = response.data.totalRecords;  
				 $scope.client1=response.data.result;
				 $scope.nodata=response.res;
				 
				 console.log("Clients list",$scope.client1);
			});
		

			
		}*/
		$timeout($scope.getCfun());
		
		 $timeout(function () {
           $scope.getCfun();   
          }, 1000);
		//var editdata;
		 $scope.pageChanged = function() {
			 $scope.getCfun();  
			          console.log('Page changed to: ' + $scope.pageIndex);
			  };
	////view address
		$scope.getaddresspopup = function(x){
			
			$scope.addressdetails = x.addressDetails;
			console.log(x);
		}
	
		$scope.editClientfun=function(data1){
				$scope.urlForEdit="#!/editClient/"+data1.id;
		}
	
		
		$scope.getDetails=function(){
			var pin=$scope.pincode;
			var promise=clientService.getDetails(pin);
			promise.then(function(response){
				console.log("country data...");
				$scope.client=response.Data;
					console.log($scope.client);
				console.log($scope.client[1].Country);
			})		
		}
		 console.log("client search Controller");  
		 $scope.search=function(clientName){
		 	console.log("entered into function");
		 $scope.client=clientName;
		 console.log($scope.client);
		 	var promise=clientService1.search($scope.client);
		 	
		 	promise.then(function(response){
		 			$rootScope.clientdata=response.data;
		 			console.log($scope.clientdata);		
		 		});
		 }
		 
		 $scope.redirect=function(){
	var change = $window.localStorage.getItem("role");
	if(change=="BDM"){
		$location.path('/bdm');
	}
	else if(change=="bdmlead"){
		$location.path('/bdmlead');
	}
	
}
		
		  $scope.id=1;
		 $scope.options=[{'id':1,'pageSize':10},{'id':2,'pageSize':20},{'id':3,'pageSize':30},{'id':4,'pageSize':40},{'id':5,'pageSize':50}]
      
		 $scope.pageSelected=function(id){
			 for(var i=0;i<=$scope.options.length;i++){
				 if($scope.options[i].id==id){
					  $scope.pageSize=$scope.options[i].pageSize;
				 }
			 }
		 }
		 
		 
		 
		$scope.flag=false;
	       $scope.expandSelected=function(person){
	         $scope.client1.forEach(function(val){
	           val.expanded=false;
	         })
	         person.expanded=true;
	         
	         
	      	$http.get('rest/Bdmrequire/clientreq/'+person.id+'/Open'+'/'+$scope.pageIndex+'/'+$scope.pageSizeSelected).then(function(response){
	      	  $scope.statusres=response.data.status;
    		    if($scope.statusres=='OK'){
    		    	$scope.flag=true;
    		    $scope.ListReqData=response.data.result;
    		    $scope.totalrecords=response.data.totalRecords;	
    		    console.log($scope.ListReqData);
    		    }
    		    else{
    		    	   $scope.ListReqData=[];
    		    		$scope.flag=false;
    		    }
   			
	     		});	
	     
	         
	       }	
	      $scope.statusChange=function(status,id){
	    	  
		        var id =id ;
		     
		         var status = status ;
		         if(status=='Active') {
		        	 status='InActive' 
		        	 
		         }else{
		        	 status='Active'  
		         }
		      	$http.get('rest/client/updateContactStatus/'+id+'/'+status).then(function(response){
		      	  $scope.statusres=response.data.status;
	    		    if($scope.statusres=='StatusSuccess'){
	    		    swal('updated successfully')
	    		    	
	    		    
	    		    }
	  
		     		});	
		     
		         
		       }
 $scope.addclientfun = function(x){

$scope.clientname =x.clientName;
 $scope.client_id = x.id;	
 $window.localStorage.setItem("addclientname",$scope.clientname);
 $window.localStorage.setItem("addrcandiclient_id",$scope.client_id);
	 	
	 }	
 
$scope.getcontactClient = function(id){
		  var promise=clientContactService.getData1(id);
	promise.then(function(response){
		$scope.clientdata=response.data.result;
		console.log($scope.clientdata);
	})
	     }	 
	
$scope.editAddress=function(list,itemData,i){
	$scope.routebtn1=false;
	$scope.routebtn2=true;
	$scope.idupdate=itemData.id;
	
	
	
	$scope.updAddress1=true;

	$scope.address1=itemData.addressLane1;
	$scope.address2=itemData.addressLane2;
	$scope.cid=itemData.cid;
	//itemData.city;
	 $scope.clientCountry=itemData.country;
	//itemData.id;
	$scope.pincode=itemData.pincode;
	$scope.clientState=itemData.state;
	//$scope.addressId=itemData.typeOfAddress.id;
	  //$scope.typeOfAddress= [
	 //   {id: 1,typeOfAddress: "Office"},
	//     {id: 2,typeOfAddress: "Residence"}
	//  ]

	var id = itemData.typeOfAddress.id;
	
	  //$scope.id = $scope.addressType[i];
//	$scope.id = itemData.typeOfAddress.typeOfAddress;
	//$scope.id = itemData.typeOfAddress; 
	  
	  
	//  $scope.id1=list[i].typeOfAddress;
	
	  var n = id.toString();
		$scope.id = n;
}
});