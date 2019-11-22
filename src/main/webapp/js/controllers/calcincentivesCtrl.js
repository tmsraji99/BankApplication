app.controller("calcincentivesCtrl",function($scope,$window,$timeout,$http,$q,userlistServices,incentivesService,$rootScope){	
	/*  $scope.isLoading=false;	  
  	  if($rootScope.loading==true){	   
  	  $scope.mystyle={'opacity':'0.5'};
  	  }
  	  else{
  		  $scope.mystyle={'opacity':'1'};
  	  }*/
	$scope.sortColumn= "id";
	$scope.reverseSort= false;
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
	$scope.getRecruiters = function() {
	$scope.isLoading=true;
		var promise = userlistServices.getUserList();
		promise.then(function(data) {
			$scope.users = data.data.result;
			console.log($scope.users);
			$scope.recruiters = [];			

			angular.forEach($scope.users, function(key, values) {
				if (key.role === "recruiter") {
					$scope.recruiters.push(key);
					console.log($scope.recruiters);
				}
				$scope.isLoading=false;
				console.log($scope.recruiters);		
			})
		});
	}
	$scope.getRecruiters();	
	/*$scope.roleChange=function(searchcategory){
		
		$scope.isLoading=true;
		$scope.searchcategory;
		  $http.get("rest/Reg/getUserNamesByRole"+'?role='+searchcategory).then(function(response){
		    	$scope.roleNamesList =  response.data.result;
		    	console.log($scope.roleNamesList);
		    	$scope.isLoading=false;
	       	});	
		  };*/
	  /* $scope.open1 = function() {			
		    $scope.popup1.opened = true;
		  };
		 $scope.popup1 = {
			opened: false
			};*/	
	//pagination
/*	   $scope.searchtext =  new Date();*/
	 /*  $scope.isLoading=false;	  
	  	  if($rootScope.loading==true){	   
	  	  $scope.mystyle={'opacity':'0.5'};
	  	  }
	  	  else{
	  		  $scope.mystyle={'opacity':'1'};
	  	  }*/
		$scope.pageSize='10';
	 	$scope.selected = {};
	 	$scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	    
	/* $scope.getfun1 = function(){
		 
		   $scope.isLoading=true; 
		  var promise= incentivesService.incentivesData($scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory);	 
	      promise.then(function(response){
		console.log(response);
	    $scope.incentivesData=response.data.result;
	     $scope.totalCount = response.data.totalRecords;  
		 $scope.nodata=response.data.res;	
		   $scope.isLoading=false;	 
		console.log($scope.incentivesData);		 
	 }); 
	 }
	 $scope.getfun1();*/

	/* $scope.searchText = function(){
		 $scope.getfun1();   
	 }
	$scope.clearText=function(){
		$scope.by="",
		$scope.order="",
		$scope.searchtext="",
		$scope.searchcategory=""
		$scope.getfun1(); 
		}*/
	/* $timeout(function () {
            $scope.getfun1();   
          }, 1000);
	 $timeout(function () {
             $scope.getfun1();   
         }, 3000);*/
  
	  /* $scope.pageChanged = function() {
	   
				   $scope.getfun1();
		  };*/

		  $scope.pushassign = function(list) {
			  
			  $scope.recruiterId =list.id;
				 }
		$scope.calculateInc=function(){
			
			  /* $scope.isLoading=true; */
			var forall='no';
		    $http.get("rest/incentive/"+$scope.recruiterId+'/'+forall).then(function(response){
		    	$scope.incentivelist1 =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.incentivelist1);
		    	/*   $scope.isLoading=false; */
		    	if ($scope.status=="StatusSuccess"){
		    	swal('response Success')
		    	}
		    	$scope.getRecruiters();
				$window.location.reload();
				$scope.getRecruiters();
	       	});
		}
		$scope.calcAll=function(){
			
			  $scope.isLoading=true; 
			var id = $window.localStorage.getItem("usid");
			var forall='yes';
		    $http.get("rest/incentive/"+id+'/'+forall).then(function(response){
		    	$scope.incentivelistforall =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.incentivelistforall)
		    	 $scope.isLoading=false; 
	       	});
		}		
		/*$scope.viewInfo=function(list){
			
			var recId=list.id;
			 $http.get("rest/addCandidate/"+recId).then(function(response){
			    	$scope.recuiterDetails =  response.data.result;
		})
		}*/
		$scope.calcAll1=function(){
			
			 $scope.isLoading=true; 
			var id = $window.localStorage.getItem("usid");
			var forall='yes';
		   /* $http.get("rest/incentive/"+id+'/'+forall).then(function(response){
		    	$scope.incentivelistforall =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.incentivelistforall)
	    	 $scope.isLoading=false; 
       	});*/
		}	
		$scope.calcAl2=function(){
			
			 $scope.isLoading=true; 
			var id = $window.localStorage.getItem("usid");
			var forall='yes';
		  /*  $http.get("rest/incentive/"+id+'/'+forall).then(function(response){
		    	$scope.incentivelistforall =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.incentivelistforall)
		    	$scope.isLoading=false; 	      
		    	});*/
		}	
	$scope.example16settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 		
	$scope.ok=false;
	/*$scope.Assignwrk = function(list) {
	$scope.responseMes="Proceesing...";	
	console.log(list.client.id);
		var assignwrk = {						
			"client" : {
				"id" : list.client.id
			},
			"bdmReq" : {
				"id" : list.id
			},
			"users" :$scope.userarray
		}		
		console.log(assignwrk);
		  if(assignwrk==undefined)
					  {						  
						  $scope.mes1="Please Fill All Details";
						  swal( $scope.mes1);
						  return;						  
					  }					                                  
					         $http.post('rest/allocaterequirment',assignwrk) .then(function(response) {
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
	}		*/
	$scope.getRecrutierdetails = function() {
		
	var roleName="RECRUITER";	
	 $http.get('rest/Reg/assign/'+roleName) .then(function(response) {                   
		 $scope.recrutier1 = response.data;
			 console.log($scope.recrutier1);
	 var userarr1=[];		
 angular.forEach($scope.recrutier1, function(key) {
			console.log(key.id);
			 userarr1.push({
			"id" : key.id
		 });
 })		
 $scope.userarray=userarr1;
	});
	 }	
	$scope.getRecrutierdetails();
	 $timeout($scope.getRecrutierdetails);	
}) 



