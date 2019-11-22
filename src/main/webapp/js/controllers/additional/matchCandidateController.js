app.controller('matchCandidateCtrl',function($scope,$rootScope,rpoFactory,$window,$routeParams,$location,listofCandidateservice,$http) {
	
$scope.getReqByReqId = function(){
		var reqid = $routeParams.id;
		var loginUserId = $routeParams.uid;
		rpoFactory.reqGetByiD(reqid).then(function(res) {
			$scope.reqDetails = res.result;
			$scope.skills = $scope.reqDetails.skills;
			$scope.qualifications = $scope.reqDetails.qualifications;
			$scope.designations = $scope.reqDetails.designations;
		})
}
//pagination
$scope.maxSize = 2;     // Limit number for pagination display number.  
  $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
  $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
  $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
 /* $scope.searchtext= $scope.searchtext;
  $scope.searchcategory = $scope.searchcategory;*/
  
$scope.getCandidatesByRole = function(){
	
	$scope.userId = $window.localStorage.getItem("usid");
	$scope.role = $window.localStorage.getItem("role");
	$scope.status = undefined;
	var promise = listofCandidateservice.getCandidateList($scope.userId,$scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory).then(function(res) {
		$scope.candidateData = res.data.result;
		$scope.noData = res.data.res;
		$scope.totalCount = res.data.totalRecords;
		})
	}
$scope.getCandidatesByRole();
$scope.searchText = function(){
	 $scope.getCandidatesByRole();   
}
	$scope.clearText=function(){
		$scope.by="",
		$scope.order="",
		$scope.searchtext="",
		$scope.searchcategory="",
		$scope.getCandidatesByRole(); 
	}
$scope.canDetails=function(canDetails){
	
	   $http.get("rest/addCandidate/"+canDetails.candidateId).then(function(response){
	$scope.datails=response.data.result;
	   });
}
$scope.pageChanged = function() {
	 $scope.getCandidatesByRole(); 
	 console.log('Page changed to: ' + $scope.pageIndex);
	  };

$scope.mapToRequirement =function(candidateDetails) {
	
	var reqObj = {
	"bdmReq":{
		"id":$routeParams.id
	},
	"candidate":{
		"id":candidateDetails.candidateId
	},
	"mappedUser":{
		"id":$routeParams.uid
	}
		}
//	if(candidateDetails.candidateStatus == "Created" || candidateDetails.candidateStatus == "Hold" || candidateDetails.candidateStatus == "Rejected"){
	rpoFactory.candidateMapping(reqObj).then(function(response) {
		$scope.candidateReqMap = response;
		if(response.errorMessage!=  null  &&  response.errorMessage =="This Candidate already mapped to this requirement"){
			swal(response.errorMessage);
			return false;
		}
		
		if(response.status == "OK"){
			swal('Successfully Mapped ');
			$location.path("/CandidateList1");
		}
	})	
}
});