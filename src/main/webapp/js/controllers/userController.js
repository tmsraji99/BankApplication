app.controller('userController', function(adminServices,userRegService ,$location,$timeout,$scope,$rootScope,$window,$timeout,$http) {
	  
	  //$scope.style1=true;
	  $scope.nandu="true";
	//pagination
	  	$scope.pageSize='10';
	  	$scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	 $scope.isLoading=false;
	  
	  if($rootScope.loading==true){
		   
	  $scope.mystyle={'opacity':'0.5'};
	  }
	  else{
		  $scope.mystyle={'opacity':'1'};
	  }

	  $scope.datehide=false;
  $scope.dateshow=true;
	
	   $scope.open1 = function() {
		   	   $scope.datehide=true;
	   $scope.dateshow=false;
    $scope.popup1.opened = true;
  };

  $scope.popup1 = {
    opened: false
  };
  
  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = $scope.formats[0];
  //Page Load List
$scope.getfun1 = function(){
	var promise=adminServices.getUserList();
	promise.then(function(response){
	
		console.log(response);	
		
				$scope.nandu=true;
				if(response.data.status=="StatusSuccess")
				{
					console.log("StatusSuccess")
					$scope.view1=true;
					$scope.view2=false;
					$scope.userlist=response.data.result;
				}
				else{
					$scope.view1=false;
					$scope.view2=true;
				}	
	    	});	
		}
	$scope.getfun1();
	$scope.userdetails = function(list){
		$scope.userdetail = list;
	}
//	Search Text 
	 $scope.searchText = function(){
		 $scope.getfun1();   
	 }
//	Clear  Text 
	$scope.clearText=function(){
		$scope.by="",
		$scope.order="",
		$scope.searchtext="",
		$scope.searchcategory="",
		$scope.getfun1(); 
		}	
//Update Users
$scope.updatefun = function(){
	$scope.isLoading=true;
	     	var promise=adminServices.getUserList();
	    	promise.then(function(response){
	    		console.log(response.data.result);
				$rootScope.userlist=response.data.result;
				if(response.data.status=="StatusSuccess")
				{
					console.log("StatusSuccess")
					$scope.view1=true;
					$scope.view2=false;
					$rootScope.userlist=response.data.result;
					$scope.isLoading=false;
				}
				else{
					$scope.view1=false;
					$scope.view2=true;
				}    		
	    	});
} 	
	  
$scope.updatefun();    
$timeout($scope.updatefun);	
	$scope.userid=function(list){	
		$scope.ListData=list;
		console.log($scope.ListData);
		console.log($scope.ListData.status);
	}	
		var promise=userRegService.getDegList();
	    promise.then(function(response){
	    		console.log(response.data.result);
				$scope.designationList=response.data.result;   		
	    	});
	    $scope.reportingList = function() {
			
			 $http.get('rest/Reg/getReportingManagersList').then(function(response){
		  		
//		   	        console.log("rest/Reg/getReportingManagersList",response.result);
//		   	        console.log(response.data)
//		   	        console.log(response.data.result)
		   	     $scope.reportList = response.data.result;
		  	  })
		  	  }		
			   $scope.ok=false;
	$scope.updateUserlist = function() {		
		$scope.responseMes="Proceesing..."
		$scope.updateList={
				name:$scope.ListData.firstName+"."+$scope.ListData.lastName+"@ojas-it.com",
				password:$scope.ListData.password,
				firstName:$scope.ListData.firstName,
				lastName:$scope.ListData.lastName,
				contactNumber:$scope.ListData.contactNumber,
				email:$scope.ListData.email,
				role:$scope.ListData.role,
				question:$scope.ListData.question,
				doj:$scope.ListData.doj,
				dob:$scope.ListData.dob,
				extension:$scope.ListData.extension,
				designation:$scope.ListData.designation,
				status:$scope.ListData.status,
				answer:$scope.ListData.answer,
				salary:$scope.ListData.salary,
				variablepay:$scope.ListData.variablepay,
				ctc:$scope.ListData.ctc,
				mintarget:$scope.ListData.mintarget,
				maxtarget:$scope.ListData.maxtarget,
				targetduration:$scope.ListData.targetduration
		};		  		
					  if($scope.updateList==undefined)
					  {						  
						  $scope.mes1="Please Fill All Details";
						  swal( $scope.mes1);
						  return;						  
					  }					                                  
					         $http.post("rest/Reg/"+$scope.ListData.id,$scope.updateList) .then(function(response) {
                              console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.responseMes="Updated  Successfully";	
                                 $scope.ok=true;								
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }						                          
                             }); 
					  
				  		
		// console.log($scope.ListData.id);
		// console.log(updateList);
	
		
		       	// var promise =adminServices.updateUserList($scope.ListData.id,updateList);
						
					// promise.then(function(response){
						
						// console.log(response.data);
						// updatefun();
					// });					
						
						
						
						
	//	$window.location.reload();				
		 
		
	}	
	$scope.redirect=function(){	
		$location.path("/listOfUsers");
		$window.location.reload();
	}
	 $scope.inactive=function(id){
	     	var promise=adminServices.inactiveStatus(id);
	    	promise.then(function(response){
	    		console.log(response.data);
	    		$scope.newUserlist=response.data;
	    		$scope.userlist.push($scope.newUserlist);
	    		$scope.userlist.splice(this.id,1);
	    		
	    		console.log($scope.newUserlist.status);
				//$window.location.reload();
	    		$scope.updatefun();				
	    	});
	     	
	  }	
});

































