app.controller('candidatelistController',function($scope,listofCandidateservice,$location,$window,$timeout,$http,$q) {
   
    
//    console.log("candidate List Controller");
//	$scope.clickedUser={};
//    $scope.getcandidatelist = ;
//   
//    console.log($scope.candidatelist);
//    $scope.roleName= $scope.rolelists.roleName;
//    
//    $scope.id;
//    $scope.pushrole=function(roles){
//    	$scope.clickedUser=roles;
//    }
	
	
//    $scope.editSkill12=function(){
//    	console.log($scope.clickedUser);
//    	editrole={
//    			roleName:$scope.clickedUser.roleName
//    	}
//    
//    console.log($scope.clickedUser.id);
//    	editroleService.editrole($scope.clickedUser.id,editrole);
//   
//    }
	$scope.sortColumn= "statusLastUpdatedDate";
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
    $scope.pageSize='10';
 	$scope.selected = {};
	  $scope.maxSize = 2;  
// Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 40; // Maximum number of items per page.	     
	  /*  $scope.searchtext= $scope.searchtext;
		$scope.searchcategory = $scope.searchcategory;		*/
	    $scope.isLoading= true;
    $scope.candidateList=function(){
	$scope.userId = $window.localStorage.getItem("usid");
	$scope.role = $window.localStorage.getItem("role");
	var promise = listofCandidateservice.getCandidateList($scope.userId,$scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory);
	promise.then(function(response) {
		
//			var len = response.data.result.length;
////				$scope.candidatelist =response.data.data.result;
//				for(var i=0;i>len;i++){
////					$scope.candidatelist=response.data.result[i];
//					console.log(response.data.result[i]);
//				}
		$scope.isLoading= false;
		$scope.candidatelist = response.data.result;
		console.log($scope.candidatelist)
        $scope.totalCount = response.data.totalRecords; 
        $scope.noData = response.data.res;  
	/*	$scope.candidateName= candidatelist.firstName+""+candidatelist.lastName*/
			});
	$scope.pageChanged = function() {
			$scope.isLoading= true;
			$scope.candidateList(); 
			$scope.isLoading= false;
		 console.log('Page changed to: ' + $scope.pageIndex);
		  };
    }
//    $scope.candidateList(); 
	 $scope.searchText = function(){		 
		 $scope.candidateList();   
	 }
		$scope.clearText=function(){
			$scope.by="",
			$scope.order="",
			$scope.searchtext="",
			$scope.searchcategory="",
			$scope.candidateList(); 
		}
		/*if($scope.resume == undefined){
			swal("Please Select a File");
			return false;
		}	   
		else{
	   	if($scope.resume!=undefined){
  		 var ext = $scope.resume.name.split('.').pop();
  		 console.log(ext);
  		 if( ext=='docx' || ext=='pdf'){
  			$scope.resumeFile = $scope.resume;
  			$scope.resumeFileName = "resume";
  			 $scope.message="";
  			 $scope.UploadResume();
  		 }
  		 else{
  			 $scope.message="Please Select only docx & Pdf Files  !";
				  		return;
  		 }
  		}
		}*/
		$scope.uploadResume = function(data,resume) {
if (resume == undefined)		{
	swal("Select Resume and Upload");
	return false;
}	
else{		
			var file = resume;	
			var canId = data;
			  var deferred = $q.defer();
			  var hd = {
					  	transformRequest: angular.identity,
				       headers: {'Content-Type': undefined }
				}
			  var formData = new FormData();
			  formData.append('file', file);
			  $http.post("rest/addCandidate/uploadResume/"+canId,formData,hd).success(function(response){ 
				 console.log(response);
				 if(response.status2=="SUCCESS"){
					 swal("Successfully Uploaded");
				 }
				 
			  });
		}
		}
		/*$timeout(function () {
	            $scope.candidateList();   
	          }, 1000);*/
		$timeout(function () {
	             $scope.candidateList();   
	         }, 100);
	  
		
	$scope.updatedata = function(data){
		console.log(data);
		var reqId = data;
		$location.path("/matchData/"+reqId);
	}
	$scope.editCandidate = function(id){	
		var reqId = id;		
		$scope.candidatelist ;
		$scope.urlForEdit = "#!/editCanndidate/" + reqId;
		/*$location.path("/editCanndidate/"+reqId);*/
		/* $http.post("rest/updateCandidateInfo/"+id).then(function(response){
		    	$scope.hi =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.hi);
			});*/
	}
	 $scope.pageSelected=function(id){
		 for(var i=0;i<=$scope.options.length;i++){
			 if($scope.options[i].id==id){
				  $scope.pageSize=$scope.options[i].pageSize;
			 }
		 }
	 }
	
	 $scope.viewInfo = function(candidateId) {
			debugger;
			    $http.get("rest/addCandidate/"+candidateId).then(function(response){
			    	$scope.candidatelist1 =  response.data.result;
		       	});
		}
	 
});






