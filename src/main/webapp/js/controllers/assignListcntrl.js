app.controller("assignListCtrl", function($scope, $window, assignListwrkSer,$timeout,
		updateAssignService, clientService, bdmService, userlistServices,$http,
		bdmReqService,deleteAssignService,rpoFactory,$rootScope) {

	console.log("myctrl");
	$scope.example16settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 

	//$scope.example17settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 

	$scope.role=$window.localStorage.getItem("role");
	$scope.loginId =$window.localStorage.getItem("usid");
	
	$scope.req = [];
	$scope.sortColumn= "assignedDate";
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
	
	$scope.pageSize='10';
	/* $scope.isLoading=false;	  
	  if($rootScope.loading==true){	   
	  $scope.mystyle={'opacity':'0.5'};
	  }
	  else{
		  $scope.mystyle={'opacity':'1'};
	  }*/
	$scope.selected = {};
	//$scope.record = assignListwrkSer.query();
	
	var promise = userlistServices.getUserList();
		
	promise.then(function(data) {
		$scope.users = data.data.result;
		console.log($scope.users);
		$scope.recruiters = [];
		angular.forEach($scope.users, function(key, values) {
			console.log(key.role);
			if (key.role === "recruiter") {
				$scope.recruiters.push(key);
				console.log($scope.recruiters);
			}
			console.log($scope.recruiters);
		})
	})
	
	
	//pagination
		  $scope.maxSize = 2;     // Limit number for pagination display number.  
		    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
		    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
		    $scope.pageSizeSelected = 10; // Maximum number of items per page.
	
		    $scope.assignfun=function() {
		    	$scope.isLoading=true;
		    	var promise = assignListwrkSer.assignfunservice($scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory);
	promise.then(function(response) {
		
// $scope.records = response.data;
		$scope.record = response.data.result;
		$scope.totalCount = response.data.totalRecords;
		$scope.nodata = response.data.res;
		$scope.isLoading=false;
		console.log($scope.record);	
	});	
		    }
//		    Search Code
		    $scope.searchText = function(){
				 $scope.assignfun();   
			 }
//		    Clear Search
				$scope.clearText=function(){
					$scope.by="",
					$scope.order="",
					$scope.searchtext="",
					$scope.searchcategory="",
					$scope.assignfun(); 
				}
		    /*$scope.searchassign=function(searchitem) {	
				
				$scope.searchtext=searchitem.searchtext;
				$scope.searchcategory=searchitem.searchcategory;
				$scope.getrole = $window.localStorage.getItem("role");
				$scope.getuserid = $window.localStorage.getItem("usid");
				$http.get("rest/allocaterequirment/searchAssignedRequirements/"+$scope.getrole+"/"+$scope.getuserid+"/"+$scope.searchtext+"/"+$scope.searchcategory+"/"+$scope.pageIndex+"/"+$scope.pageSizeSelected).success(function(response){
					 $scope.totalCount = response.totalRecords;  
					 $scope.record=response.result;
					 $scope.nodata=response.res;
					 
					 console.log("Assignment list",$scope.record);
				});
			

				
			}*/
		    $scope.pageChanged = function() {
		    	
		    	 $scope.assignfun(); 
				          console.log('Page changed to: ' + $scope.pageIndex);
				  };
					$scope.viewReqInfo = function(record1){
						
						var reqid = record1.requirementId;
						rpoFactory.reqGetByiD(reqid).then(function(res) {
							
							$scope.reqDetails = res.result;
							$scope.skills = $scope.reqDetails.skills;
							$scope.qualifications = $scope.reqDetails.qualifications;
							$scope.designations = $scope.reqDetails.designations;
							$scope.locations= $scope.reqDetails.locations;
							console.log($scope.locations)
						})
						console.log($scope.reqDetails);
				}
	$scope.pushassign = function(list) {
		 var fun1=function() {
		var promise = clientService.getCa();

	promise.then(function(data) {
		$scope.client = data.data.result;
		console.log($scope.client);
	});

		
	}
	fun1();
	$timeout(fun1);
	
		$scope.requirements = bdmService.getClientNames();
		console.log($scope.requirements);
		
		$scope.getRequirement = function(id) {
			console.log(id);
			var promise = bdmReqService.getClientRequirement(id);
			promise.then(function(data) {
				console.log(data);
				$scope.requirement = data;
			});
		}

		

		console.log(list);
		$scope.selected = list;
		localStorage.setItem('list', JSON.stringify($scope.selected));
		$scope.selectedList = localStorage.getItem('list');
		
		console.log($scope.selectedList);
		$scope.data = JSON.parse($scope.selectedList);
		
		console.log($scope.data);
		
		
		
		
		var editRecValue=$scope.data;
		var userarr=editRecValue.users;
		console.log(userarr);
		var userarr1=[];
		
		angular.forEach(editRecValue.users, function(key) {
			console.log(key.id);
			userarr1.push({
				"id" : key.id
			});
		})
		

		
		$scope.userarray=userarr1;
		console.log($scope.userarray);
		
		//$scope.recruiter = $scope.data.users.firstName;

	}

	
	$scope.ok=false;
	$scope.editAssignments = function() {
		$scope.responseMes="Proceesing...";
		console.log($scope.recruiter);
		var usersList = [];
		console.log($scope.recruiter);
		
		console.log(usersList);

		updateList = {
			bdmReq : {
				"id" : $scope.data.bdmReq.id
			},
			client : {
				"id" :  $scope.data.client.id
			},
			users : $scope.userarray

		};
		
		  if(updateList==undefined)
					  {
						  
						  $scope.mes1="Please Fill All Details";
						  swal( $scope.mes1);
						  return;
						  
					  }                            
	  $http.post('rest/allocaterequirment/'+$scope.data.id,updateList) .then(function(response) {
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

		
		
		// console.log(updateList);
		// updateAssignService.editAssignments($scope.data.id, updateList);
	}
	$scope.deleteAssignments=function(){
		
		$scope.isLoading= true;
		  $http.delete('rest/allocaterequirment/delete/'+$scope.data.assignId) .then(function(response) {
	            console.log(response);
	            $scope.isLoading= false;
			if(response.data.status=="StatusSuccess"){
		           $scope.responseMes="Deleted Successfully";	
		           swal( $scope.responseMes)
	                $scope.ok=true;
		           $scope.assignfun(); 
						 }
						 else{
								 $scope.responseMes="Internal Server Error"
								 $scope.ok=true;
							 }	
				$scope.assignfun();
				
				$window.location.reload();
				$scope.assignfun();
	                        }
		  ); 
		}

	$scope.viewInfo = function(list) {

		$scope.allReqData = list;
		console.log($scope.allReqData);
	}

});