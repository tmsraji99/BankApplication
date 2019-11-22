app.controller('userRegistrationCtrl',function($scope,$rootScope,userRegService,$window, adminServices,$location,$http) {


   $scope.open1 = function() {
    $scope.popup1.opened = true;
  };

  $scope.open2 = function() {
   $scope.popup2.opened = true;
 };

  $scope.popup1 = {
    opened: false
  };
  $scope.popup2 = {
		    opened: false
		  };
  
  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = $scope.formats[0];

// secuity questions
	$scope.sample = [{
		id: '1',
		name: 'What was your childhood nickname?'
		}, {
		id: '2',
		name: 'In what city or town was your first job?'
		
		}];
        

	   var promise=userRegService.getUserList();
	    promise.then(function(response){
				$scope.UserDataList=response.data.result; 		
	    	});
	
	
	
	
//designation List
		var promise=userRegService.getDegList();
	    promise.then(function(response){
	    		console.log(response.data.result);
				$scope.designationList=response.data.result; 		
	    	});
		
	 
	    
	    
	    
	    
//add user
    $scope.ok=false;
    $scope.addUser=function(){
    	var userId = parseInt($scope.userSelectedId);
    	
		$scope.responseMes="Processing...";
    	$scope.userDetails={
    			name:$scope.firstName+"."+$scope.lastName+"@ojas-it.com",
    			password:$scope.passwordU,
    			firstName:$scope.firstName,
    			lastName:$scope.lastName,
    			contactNumber:$scope.contactNumber,
    			email:$scope.email,
          		role:$scope.role,
          		question:$scope.selitem,
          		answer:$scope.answer,
				doj:$scope.doj,
				dob:$scope.dob,
				extension:$scope.extension,
				//designation:$scope.designation,
				salary:$scope.Salary,
				variablepay:$scope.Variablle,
				ctc:$scope.CTC,
				mintarget:$scope.minTarget,
				maxtarget:$scope.maxTarget,
				targetduration:$scope.targetDuration,
				reportingId:{id:userId},
    	     }

	   
              
					 
					  if($scope.userDetails==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  swal( $scope.mes1);
						  return;
						  
					  }
					                                  
					         $http.post("rest/Reg",$scope.userDetails) .then(function(response) {
                              console.log(response);
							  if(response.data.status=="StatusSuccess"){
								$scope.responseMes="User Registered Successfully";	
                                 $scope.ok=true;
							  }
							  else{
								  $scope.responseMes="Internal Server Error"
								  $scope.ok=true;
							  }
						  
                          
                             }); 
					  
				  
				  
				  
				  
	}
	
	
	
	$scope.redirect=function(){
		
		$location.path("/listOfUsers");
		$window.location.reload();
	}
	
	
	
 });
