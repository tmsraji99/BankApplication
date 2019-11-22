app.controller("assignTaskController", function($scope,$rootScope, rpoFactory,assignTaskService, assignTaskService1, $window,rpoFactory,$location,$http) {
	$scope.record = assignTaskService.query();
	
	/*$scope.getTask = function() {
		//id=$rootScope.id;
		var id = $window.localStorage.getItem("usid");

		var promise = assignTaskService1.getTaskAssignedRequirement(id);
		promise.then(function(response) {
			$scope.resultTask = response.data;
			console.log($scope.resultTask);
		});
	}
	$scope.getTask(); */
	/*$scope.isLoading=true;
	  if($rootScope.loading==true){		   
		  $scope.mystyle={'opacity':'0.5'};
		  }
		  else{
			  $scope.mystyle={'opacity':'1'};
		  }*/
	$scope.sortColumn= "assigenedDate";
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
	  $scope.maxSize = 2;     // Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	   /* $scope.searchtext= $scope.searchtext;
		$scope.searchcategory = $scope.searchcategory;*/
	$scope.popdata=function(record1){
		$scope.isLoading=true;
		console.log(record1);
		
		$scope.popdatarecord = record1.bdmReq;
		$scope.isLoading=false;		
	}
	
	
	$scope.addreqcandifun = function(list){
		$scope.isLoading=true;
		$scope.reqname = list.bdmReq.nameOfRequirement;
		$scope.reqid = list.bdmReq.id;
		$scope.clientname = list.bdmReq.client.clientName;
		$scope.client_id = list.bdmReq.client.id;
		$window.localStorage.setItem("addreqcandireqname",$scope.reqname);
		$window.localStorage.setItem("addreqcandireqid",$scope.reqid);		
		$window.localStorage.setItem("addreqcandiclientname",$scope.clientname);
		$window.localStorage.setItem("addreqcandiclient_id",$scope.client_id);
	}
	$scope.viewReqInfo = function(x){
		
		$scope.isLoading=true;
		var reqid = x.id;
		rpoFactory.reqGetByiD(reqid).then(function(res) {
			$scope.reqDetails = res.result;
			$scope.skills = $scope.reqDetails.skills;
			$scope.qualifications = $scope.reqDetails.qualifications;
			$scope.designations = $scope.reqDetails.designations;
			$scope.locations = $scope.reqDetails.locations;
			$scope.isLoading=false;
		})
		console.log($scope.reqDetails);
	}
	$scope.getRequirementDetails = function (){
		$scope.isLoading=true;
		$scope.Userid = $window.localStorage.getItem("usid");
		$scope.status = "Open";
		rpoFactory.requirementById($scope.Userid,$scope.status,$scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory).then(function(response) {
			
			$scope.reqListDetails = response.result;
			$scope.noData = response.res;
			$scope.totalCount = response.totalRecords;
			$scope.isLoading=false;
		})
	}
	 $scope.searchText = function(){
		 
		 $scope.isLoading=true;
		 $scope.getRequirementDetails();  
		 $scope.isLoading=false;
	 }
		$scope.clearText=function(){
			$scope.isLoading=true;
			$scope.by="",
			$scope.order="",
			$scope.searchtext="",
			$scope.searchcategory="",
			$scope.getRequirementDetails(); 
			$scope.isLoading=false;
		}
		$scope.pageChanged = function() {
			$scope.isLoading=true;
			 $scope.getRequirementDetails(); 
			 $scope.isLoading=false;
			 console.log('Page changed to: ' + $scope.pageIndex);
			  };
	$scope.navigationToCandidate = function(list) {
		/*$scope.isLoading=true;*/
		console.log(list);
		var reqId = list.id;
		$location.path("/matchCandidate/"+reqId+"/"+$scope.Userid);
	}
	
	/*$scope.pageSize=5;
	  $scope.id=1;
	 $scope.options=[{'id':1,'pageSize':5},{'id':2,'pageSize':10},{'id':3,'pageSize':15},{'id':4,'pageSize':20},{'id':5,'pageSize':25}]

	 $scope.pageSelected=function(id){
		 for(var i=0;i<=$scope.options.length;i++){
			 if($scope.options[i].id==id){
				  $scope.pageSize=$scope.options[i].pageSize;
			 }
		 }
	 }*/
	
	$scope.flag=false;
    $scope.expandSelected=function(details){
      $scope.reqListDetails.forEach(function(val){
        val.expanded=false;
      })
      details.expanded=true;
      
      
   	$http.get('rest/addCandidate/getCandiateListByRequirementId/'+details.id+'/'+$scope.pageIndex+'/'+$scope.pageSizeSelected).then(function(response){
   	  $scope.statusres=response.data.status;
		    if($scope.statusres=='OK'){
		    	$scope.flag=true;
		    $scope.ListReqData=response.data.result;	
		    }
		    else{
		    	   $scope.ListReqData=[];
		    		$scope.flag=false;
		    }
		
  		});	
  
      
    }
	
});

