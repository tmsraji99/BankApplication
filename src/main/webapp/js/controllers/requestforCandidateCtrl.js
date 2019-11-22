app.controller("requestforCandidateCtrl",function($scope, $location, $routeParams, $filter, $rootScope, $window,
						$http,$q,selectedDetailsService,$timeout) {
							$scope.userRole=localStorage.getItem('role');
							$scope.userId =localStorage.getItem('usid');

		
	               $scope.reqForCandidate = function(){
	            	   
	            	   if($scope.mobile == undefined || $scope.mobile == ''){
	            		   swal("Enter Mobile Number and then Search")
	            	   }	
	            	   else{
	            	   var deferred = $q.defer();
	            	   $http.get("rest/candidateReqMapping/getCandiateMobileNumber/"+ $scope.mobile+"/"+$scope.userId).success(function(response){
	            		   $scope.isLoading =false;
	            		  console.log(response); 
	            		  if(response.status == "DataIsEmpty"){
		            			swal("No Candidate Found");
		            		}
		            		else{
		            			swal("Mobile Number Found");
		            		}
	            		$scope.mobileNumberlist= response.result;
	            		if($scope.mobileNumberlist== null){
	            			swal("No Data Found")
	            		}
	            		$scope.skillslist= $scope.mobileNumberlist.skills;
	            		$scope.qualificationslist= $scope.mobileNumberlist.education;
	            		console.log($scope.qualificationslist)
	            		$scope.certificatesList= $scope.mobileNumberlist.certificates;
	            		$scope.recruiterName= $scope.mobileNumberlist.user.firstName+' '+ $scope.mobileNumberlist.user.lastName;
	            		$scope.recruiteroldId= $scope.mobileNumberlist.user.id;
	            		$scope.recruiteroldEmail= $scope.mobileNumberlist.user.username;
	            		$scope.candidateId= $scope.mobileNumberlist.id;	            		
	            	   });           	  	            	
	               }
	               }
	               $scope.reqCan = function(){
	            	   
	            	   if($scope.mobile == undefined || $scope.mobile == ''){
	            		   swal("Please search with Mobile Number")
	            	   }
	            	   else if($scope.mobileNumberlist == undefined){
	            		   swal('Search and Request')
	            	   }
	            	   else{
	            	   $scope.requestStatus = "Requested";
	            	   var requestCand = {
	            			   "requestedUserId" : localStorage.getItem('usid'),         	   
								"ownerUserId": $scope.recruiteroldId,
								"candidateId": $scope.candidateId,
								"requestStatus":  $scope.requestStatus
	            	   }
						  $http.post("rest/candidateRequestByRecruiters",requestCand).success(function(response){
			            		  console.log(response); 
			            		  if (response.status="OK"){		            			  
			            			  swal("Your request has been sent to Other Recruiter");
			            			  $location.path('/reqCandList');
			            		  }			            		 
			            	   });
	            	   
	               }
	               }
					});
