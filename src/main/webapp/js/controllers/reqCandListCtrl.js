app.controller('reqCandListCtrl',function($scope,$location,$window,$timeout,$http,$q ) {
	//pagination
    $scope.pageSize='10';
 	$scope.selected = {};
	  $scope.maxSize = 2;  
// Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     

    $scope.reqcandidatesList=function(){
	
	$scope.userId = $window.localStorage.getItem("usid");
	$scope.role = $window.localStorage.getItem("role");
	/*var promise = reqCandidatesListservice.getreqCandidateList($scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory);
	promise.then(function(response) {
		
		$scope.reqcandidatelist = response.data.result;
		console.log($scope.reqcandidatelist)
			});*/
	$http.get("rest/candidateRequestByRecruiters/getAllcandidateRequestByRecruiters/"+$scope.pageIndex+'/'+$scope.pageSizeSelected+'/'+$scope.role+'/'+$scope.userId+'?sortingOrder='+$scope.sortingOrder+'&sortingField='+$scope.sortingField+'&searchText='+$scope.searchtext+'&searchField='+$scope.searchcategory).then(function(response){
		 console.log(response);
		 $scope.reqcandidatelist = response.data.result;
		 $scope.totalCount = response.data.totalRecords;
		 if( $scope.totalCount == 0){
			 $scope.noData= "No Requests Found"
		 }
		 console.log($scope.reqcandidatelist);
	})
	$scope.pageChanged = function() {
		 $scope.candidateList(); 
		 console.log('Page changed to: ' + $scope.pageIndex);
		  };
    }
    $scope.reqcandidatesList();
//  Search Text
    $scope.searchText = function(){
		 
		 $scope.reqcandidatesList();   
	 }
//    Clear Text
		$scope.clearText=function(){
			$scope.by="",
			$scope.order="",
			$scope.searchtext="",
			$scope.searchcategory="",
			$scope.reqcandidatesList(); 
		}
		$scope.approveCand = function(canid,olduserid,newuserid,id){
			
			$scope.canid = canid;
			$scope.status = "approved";
			$scope.olduserid = olduserid;
			$scope.newuserid = newuserid;
			$scope.id =id;
			
			var approveCandidate ={
					"candidateId" :$scope.canid,
					"requestStatus":  $scope.status,
					"ownerUserId":  $scope.olduserid,
					"requestedUserId":  $scope.newuserid,
					"id": $scope.id
			}
			$http.post("rest/candidateRequestByRecruiters/update",approveCandidate).then(function(response){
				 console.log(response);
				 $scope.requestStatus =response.data.result.requestStatus;
				 if(response.data.status== "StatusSuccess"){
					 swal("successfully updated")
				 }
			})
			 $scope.reqcandidatesList();   
		}
		$scope.rejCand = function(canid,olduserid,newuserid,id){
			
			$scope.canid = canid;
			$scope.status = "rejected";
			$scope.olduserid = olduserid;
			$scope.newuserid = newuserid;
			$scope.id =id;
			
			var rejectedCandidate ={
					"candidateId" :$scope.canid,
					"requestStatus":  $scope.status,
					"ownerUserId":  $scope.olduserid,
					"requestedUserId":  $scope.newuserid,
					"id": $scope.id
			}
			$http.post("rest/candidateRequestByRecruiters/update",rejectedCandidate).then(function(response){
				 console.log(response);
				 if(response.data.status== "StatusSuccess"){
					 swal("successfully updated")
				 }
			})
			 $scope.reqcandidatesList();   
		}
		$scope.releaseCand = function(canid,olduserid,newuserid,id){
			
			$scope.canid = canid;
			$scope.status = "released";
			$scope.olduserid = olduserid;
			$scope.newuserid = newuserid;
			$scope.id =id;
			
			var releasedCandidate ={
					"candidateId" :$scope.canid,
					"requestStatus":  $scope.status,
					"ownerUserId":  $scope.olduserid,
					"requestedUserId":  $scope.newuserid,
					"id": $scope.id
			}
			$http.post("rest/candidateRequestByRecruiters/update",releasedCandidate).then(function(response){
				 console.log(response);
				 if(response.data.status== "StatusSuccess"){
					 swal("successfully updated")
				 }
			})
			 $scope.reqcandidatesList();   
		}
});
