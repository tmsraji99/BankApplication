app.controller('canreqDetailsCtrl',function($scope,$http,$timeout,$rootScope,rpoFactory,$window,$routeParams,$location,$sce) {	
		var rId = $routeParams.rId;
		var cId = $routeParams.cId;
		var uId = $routeParams.uId;
		$scope.userId = $window.localStorage.getItem("usid");
		$scope.role = $window.localStorage.getItem("role");
		
		rpoFactory.canreqGetByiD(rId).then(function(res) {
			
			$scope.reqDetails = res.result;
			$scope.skills = $scope.reqDetails.skills;
			$scope.qualifications = $scope.reqDetails.qualifications;
			$scope.qualifname =$scope.qualifications[0].qualificationName;
			console.log($scope.qualifname);
			$scope.designations = $scope.reqDetails.designations;
		})
        

	
			rpoFactory.canreqdidatesByRole(cId).then(function(res) {
		      $scope.candidateData = res.result;
				})		
				$scope.viewResume=function(){
			debugger;
			 // $scope.url='images/sample.pdf'		
		/*	var docUrl1='https://view.officeapps.live.com/op/embed.aspx?src=';
				var docUrl='../Resumes/'+$routeParams.cId+'.docx&embedded=true';
				$scope.cvUrlTrusted = $sce.trustAsResourceUrl(docUrl1+docUrl);
				console.log($scope.cvUrlTrusted);*/
			 $http.get("rest/addCandidate/getResumes/"+cId).then(function(response){
				 		console.log(response.data);
//				 		var arrayBuffer=[];
//				 		arrayBuffer.push(response.data);
//				 		console.log(arrayBuffer);
//			            mammoth.convertToHtml({arrayBuffer: response.data})
//			                .then(displayResult)
//			                .done();
				 		 var binary_string =  window.atob(response.data.errorMessage);
				 	    var len = binary_string.length;
				 	    var bytes = new Uint8Array( len );
				 	    for (var i = 0; i < len; i++)        {
				 	        bytes[i] = binary_string.charCodeAt(i);
				 	    }
				 	   mammoth.convertToHtml({arrayBuffer: bytes.buffer})
				 	   .then(displayResult)
		                .done();
				 	    //return bytes.buffer;
			       
		       	});
		         }
		$scope.getCompareList=function(){
			debugger;
		    $http.get("rest/Bdmrequire/compareCandidateAndRequirement/"+cId+'/'+rId).then(function(response){
		    	$scope.getCompareList =  response.data.result;
		    	$scope.status =  response.data.status;
		    	console.log($scope.getCompareList)
	       	});
		}	
		$scope.getCompareList();
	/*
	 * $scope.acceptedAndrejected=function(status){ if(status=="Accepted By
	 * Lead"){
	 * 
	 * $http.post('rest/addCandidate/updatingstatusbylead/'+cId+'/'+status).then(function(response){
	 * $scope.res=response.data; if($scope.res.status=='StatusSuccess'){
	 * $timeout(function () { $location.path('/CandidateList1'); }, 1000);
	 *  } }); } else{
	 * $http.post('rest/addCandidate/updatingstatusbylead/'+cId+'/'+status).then(function(response){
	 * $scope.res=response.data; if($scope.res.status=='StatusSuccess'){
	 * $timeout(function () { $location.path('/CandidateList1'); }, 1000); } }); } }
	 */
		$scope.acceptedAndrejected=function(status){
			
			if($scope.reason== undefined ||$scope.reason=="" ){
				swal('Enter Reason');
				return false;
			}		
			$scope.candidateId =cId;
			$scope.requirementId = rId;
			$scope.uId = uId;
			$scope.candidateStatus = status;
			$scope.reason = $scope.reason;
			var accepted ={
					"candidateId":$scope.candidateId,
					"requirementId":$scope.requirementId,
					"loginId": $scope.uId,
					"reason": $scope.reason,
					"candidateStatus": $scope.candidateStatus 
			}
			$http.post('rest/addCandidate/updatingstatusbylead/',accepted).then(function(response){
				$scope.res=response.data;
				if($scope.res.status=='StatusSuccess'){
					$timeout(function () {
						$location.path('/CandidateList1');
					  }, 1000);
					
				}
				});	
		}
//	
		
		    
		    function displayResult(result) {
		        document.getElementById("output").innerHTML = result.value;
		        
		        var messageHtml = result.messages.map(function(message) {
		            return '<li class="' + message.type + '">' + escapeHtml(message.message) + "</li>";
		        }).join("");
		        
		        document.getElementById("messages").innerHTML = "<ul>" + messageHtml + "</ul>";
		    }
		    
		   

    function escapeHtml(value) {
		        return value
	            .replace(/&/g, '&amp;')
	            .replace(/"/g, '&quot;')
        .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;');
	    }
		
//		
});