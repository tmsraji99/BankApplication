app.controller('matchRequirementCtrl',function($scope,$rootScope,rpoFactory,$window,$location,$routeParams,$timeout,$http) {
	
	$scope.getCandidateDetails = function(){
		$scope.isLoading=true;
		$scope.candidateId = $routeParams.id; 
			//$window.sessionStorage.getItem("candidateid");
		rpoFactory.candidateGetByid($scope.candidateId).then(function(response) {
			$scope.candidateDetails = response.result;
			$scope.education = $scope.candidateDetails.education;
			$scope.certificates = $scope.candidateDetails.certificates;
			$scope.skills = $scope.candidateDetails.skills;
			console.log($scope.candidateDetails);
			$scope.isLoading=false;
		})
	}
	$scope.mapCandidateToReq = function () {		
		if($scope.SelectedReqId ==undefined){
			swal('Please select one requirement')
		}
		else{
		$scope.isLoading=true;
		
		$scope.reqId = $scope.SelectedReqId;
		$scope.loginId = $window.localStorage.getItem("usid");
		var reqObj = {
		"bdmReq":{
			"id":$scope.reqId
		},
		"candidate":{
			"id":$routeParams.id
		},
		"mappedUser":{"id":$scope.loginId},
		}
		rpoFactory.candidateMapping(reqObj).then(function(response) {
			$scope.isLoading=false;
			$scope.candidateMappingss = response.res;
			if (response.errorMessage =="This Candidate already mapped to this requirement"){
			swal(response.errorMessage);
			return false;
			}
			$scope.isLoading=true;
			if(response.status == "OK"){
				$location.path("/CandidateList1");
			}
		})
	}
	}
	$scope.reqDetails=function(x){
		
		/*$scope.ReqDetails=x;*/
		$http.get("rest/Bdmrequire/"+x.id).then(function(response){
			$scope.ReqDetails=response.data.result;
			   });
	}
	  //pagination
//  $scope.pageSize=20;
	$scope.selected = {};
	  $scope.maxSize = 2;  
//Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	    $scope.searchtext= $scope.searchtext;
		$scope.searchcategory = $scope.searchcategory;	
		
	$scope.getRequirementDetails = function(){
		
		$scope.userId = $window.localStorage.getItem("usid");
		$scope.status = "Open";
		rpoFactory.requirementById($scope.userId,$scope.status,$scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory).then(function(res) {
			
			$scope.requirementDetails = res.result;
			$scope.noData = res.res;
			$scope.totalCount = res.totalRecords;
			/*$scope.Rskills = $scope.requirementDetails.skills;
			swal($scope.Rskills)*/
			//$scope.length = $scope.requirementDetails;
			/*$scope.education = $scope.candidateDetails.education;
			$scope.certificates = $scope.candidateDetails.certificates;
			$scope.skills = $scope.candidateDetails.skills;*/
			console.log($scope.requirementDetails);
			
		});
	}
	$scope.getRequirementDetails();
	 $scope.searchText = function(){
		 
		 $scope.getRequirementDetails();   
	 }
		$scope.clearText=function(){
			$scope.by="",
			$scope.order="",
			$scope.searchtext="",
			$scope.searchcategory="",
			$scope.getRequirementDetails(); 
		}
		$scope.pageChanged = function() {
			
			 $scope.getRequirementDetails(); 
			 console.log('Page changed to: ' + $scope.pageIndex);
			  };
		$timeout(function () {
	            $scope.getRequirementDetails();   
	          }, 1000);
		$timeout(function () {
	             $scope.getRequirementDetails();   
	         }, 3000);
	  

});