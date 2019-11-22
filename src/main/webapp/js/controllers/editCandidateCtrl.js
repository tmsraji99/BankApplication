app.controller('editcandidatectrl',function($scope,$rootScope,rpoFactory,$window,$location,$routeParams,$timeout,$http,listSkillService,candidateSer) {
	
	$scope.getCandidateDetails = function(){
		
		$scope.candidateId = $routeParams.id; 
		
			//$window.localStorage.getItem("candidateid");
		 $http.get("rest/addCandidate/"+$scope.candidateId).then(function(response){
		    	$scope.candidatelist1 =  response.data.result;
		    	console.log($scope.candidatelist1);
		    	$scope.education= [];
				$scope.education=$scope.candidatelist1.education;
	       	});
	}
	$scope.getCandidateDetails();
	//$scope.save0button =true;
  /*  $scope.desigarray = [];
	$scope.skillarray= [];
	$scope.certiarray= [];
	$scope.primary= [];
	$scope.locationarray= [];*/
	$scope.education= [];
	$scope.skillarray3 = [];
	$scope.certiarray=[];
	$scope.skillarray=[];
	$scope.skillarray1=[];
	$http.get("rest/location").then(function(response) {
		debugger;
		$scope.locationList = response.data;
		console.log($scope.locationList);

	})
	$scope.example16settings = {styleActive: true, enableSearch: true, showSelectAll: true, keyboardControls: true ,showEnableSearchButton: true, scrollableHeight: '300px', scrollable: true}; 
	$scope.editCandidate =function () {
		
	/*	var cid = $window.localStorage.getItem("candidateid");*/
		var canid=$scope.candidateId;
		$scope.date = new Date();		
		$scope.skillarray3 = $scope.skillarray.concat($scope.skillarray1);
		$scope.id = $window.localStorage.getItem("usid");
		 if ($scope.candidatelist1.title == undefined) {
				swal("Please Select  Title");
				return false;
			} else if ($scope.candidatelist1.firstName == undefined || $scope.candidatelist1.firstName == '') {
				swal("Please Enter First Name ");
				return false;
			} else if ($scope.candidatelist1.lastName == undefined
					|| $scope.candidatelist1.lastName == '') {
				swal("Please Enter Last Name");
				return false;
			} 
			else if ($scope.candidatelist1.mobile == undefined ||$scope.candidatelist1.mobile =='') {
				swal("Please Enter Mobile Number  ");
				return false;
			}
			else if ($scope.candidatelist1.email == undefined || $scope.candidatelist1.email == '') {
				swal("Please Enter Email");
				return false;
			} else if ($scope.candidatelist1.candidateSource == undefined) {
				swal("Please select Candidate Source");
				return false;
			}
			else if ($scope.candidatelist1.education.length == 0) {
				swal("Please Select Education");
				return false;
			}
			else if ($scope.candidatelist1.applyingAs.length == 0) {
				swal("Please Select Applying as");
				return false;
			}
			else if ($scope.candidatelist1.totalExperience == undefined || $scope.candidatelist1.totalExperience == '') {
				swal("Please Enter Total Experince");
				return false;
			} else if ($scope.candidatelist1.relevantExperience == undefined || $scope.candidatelist1.relevantExperience == '') {
				swal("Please Enter Relevant Experince ");
				return false;
			} else if ($scope.candidatelist1.currentCTC== undefined || $scope.candidatelist1.currentCTC== '') {
				swal("Please Enter Current CTC");
				return false;
			}
			else if ($scope.candidatelist1.expectedCTC== undefined || $scope.candidatelist1.expectedCTC== '') {
				swal("Please Enter Expected CTC");
				return false;
			}			
			else if ($scope.candidatelist1.salaryNegotiable == undefined
					) {
				swal("Please Select Salary Negotiable");
				return false;
			}
			else if ($scope.candidatelist1.noticePeriod == undefined) {
				swal("Please Select Notice Period");
				return false;
			}
			else if ($scope.candidatelist1.willingtoRelocate == undefined) {
				swal("Please Select Willing To Relocate ");
				return false;
			}
			else if ($scope.candidatelist1.currentJobType == undefined ) {
				swal("Please Enter Current Job Type");
				return false;
			}/* else if ($scope.candidatelist1.payRollCompanyName == undefined || $scope.candidatelist1.payRollCompanyName == '') {
				swal("Please Enter PayRoll Company Name ");
				return false;

			} *//*else if ($scope.candidatelist1.currentCompanyName== undefined || $scope.candidatelist1.currentCompanyName == '') {
				swal("Please Enter Current Company Name");
				return false;
			}*/
			else if ($scope.candidatelist1.location.locationName == undefined) {
				swal("Please Enter Preferred Location");
				return false;
			}
			/*else if ($scope.candidatelist1.skillarray.length== 0) {
				swal("Please Select Skills");
				return false;
			}*/	
			else{
				debugger;
		$scope.updateCandidate = {
				'user':{
					'id':$scope.id
				},
				'id': $scope.candidateId,
				'title':$scope.candidatelist1.title,
				'firstName':$scope.candidatelist1.firstName,
				'lastName':$scope.candidatelist1.lastName,
				'mobile':$scope.candidatelist1.mobile,
				'alternateMobile':$scope.candidatelist1.alternateMobile,
				'street1':$scope.candidatelist1.street1,
				'street2':$scope.candidatelist1.street2,
				/*'gender':$scope.candidatelist1.gender,*/
				'email':$scope.candidatelist1.email,
				'altenateEmail':$scope.candidatelist1.altenateEmail,
				'candidateSource':$scope.candidatelist1.candidateSource,
				'education' : $scope.candidatelist1.education,
				'totalExperience' : $scope.candidatelist1.totalExperience,
				'relevantExperience' : $scope.candidatelist1.relevantExperience,
				'currentCTC' : $scope.candidatelist1.currentCTC,
				'expectedCTC' : $scope.candidatelist1.expectedCTC,
				'salaryNegotiable' : $scope.candidatelist1.salaryNegotiable,
				'noticePeriod' : $scope.candidatelist1.noticePeriod,
				'willingtoRelocate' : $scope.candidatelist1.willingtoRelocate,
				'skypeID' : $scope.candidatelist1.skypeID,
				'currentJobType' : $scope.candidatelist1.currentJobType,
				'payRollCompanyName' : $scope.candidatelist1.payRollCompanyName,
				'currentCompanyName' : $scope.candidatelist1.currentCompanyName,
				'pancardNumber' : $scope.candidatelist1.pancardNumber,
				'certificationscheck' : $scope.candidatelist1.certificationscheck,
				'skills' : $scope.candidatelist1.skills,
				'certificates' : $scope.candidatelist1.certiarray,
				/*'filename' : $scope.inputParams.name,*/
				'pincode' : $scope.candidatelist1.pincode,
				'city' : $scope.candidatelist1.city,
				'country':$scope.candidatelist1.country,
				'state':$scope.candidatelist1.state,	
				'appliedPossitionFor': $scope.candidatelist1.appliedPossitionFor,
				'currentLocation': $scope.candidatelist1.currentLocation,
				'location':{
					id: $scope.candidatelist1.location.id
				},
				'applyingAs': $scope.candidatelist1.applyingAs,
				'noOfMonths': $scope.candidatelist1.noOfMonths
				}
		
		 $http.post("rest/addCandidate",$scope.updateCandidate).success(function(response){ 
			 debugger;
			 console.log(response);
			 if (response.status= "StatusSuccess"){
				/* $scope.resMessage == "Candidate added successfully";*/
				 swal( "Candidate details updated successfully");
				 $("#msgss").show();
					$("#msgss").css("opacity", "4");
			 }			 
		$location.path("/listOfCandidate");
		
	});

	}
	}
	/*==============================================================================*/
	$scope.getskillsfun = function(){
		var promise1=listSkillService.getskill();
		promise1.then(function(data){  
			$scope.primary=data.data.result;
			console.log($scope.primary);			
		});
		}
		$scope.getskillsfun();
	/*==============================================================================*/

	var promise = candidateSer.getContactName();

	promise.then(function(data) {
		$scope.contactNames = data.data.result;
	});
	candidateSer.getContactName();

	/*==============================================================================	*/
	var promise = candidateSer.getPrimarySkill();

	promise.then(function(data) {
		$scope.skills = data.data.result;
		

		$scope.skills2 = data.data.result;

		$scope.primary = [];
		$scope.secondary = [];
		angular.forEach($scope.skills, function(key, value) {
			if (key.flag == true) {
				$scope.primarySkills = key;

				$scope.primary.push($scope.primarySkills);
			} else {
				$scope.secondarySkills = key;
				$scope.secondary.push($scope.secondarySkills);
			}
		})
		console.log("$scope.primary",$scope.primary);
		console.log($scope.secondary);
		
	});
	candidateSer.getPrimarySkill();
	/*==============================================================================	*/
	var promise = candidateSer.getCertificateNames();
	promise.then(function(data) {
		$scope.certificatname = data.data.result;
		console.log($scope.certificatname);

	});
	candidateSer.getCertificateNames();

	/*==============================================================================	*/
	var promise = candidateSer.getContactType();

	promise.then(function(data) {
		$scope.currentJobTypes = data.data.result;
		console.log($scope.currentJobTypes);

	});
	candidateSer.getContactType();

	/*==============================================================================	*/
	var promise = candidateSer.getEducation();

	promise.then(function(data) {
		
		$scope.educationdetails = data.data.result;
		console.log($scope.educationdetails);

	});
	candidateSer.getEducation();

/*	$scope.mapCandidateToReq = function () {
		
		$scope.reqId = $scope.SelectedReqId;
		var reqObj = {
		"bdmReq":{
			"id":$scope.reqId
		},
		"candidate":{
			"id":$routeParams.id
		}
		}
		rpoFactory.candidateMapping(reqObj).then(function(response) {
			$scope.candidateMappingss = response;
			if(response.status == "StatusSuccess"){
				$location.path("/CandidateList1");
			}
		})
	}*/
	
	/*$scope.reqDetails=function(x){
		
		$scope.ReqDetails=x;
		$http.get("rest/Bdmrequire/"+x.id).then(function(response){
			$scope.ReqDetails=response.data.result;
			   });
	}*/
	  //pagination
//  $scope.pageSize=20;
/*	$scope.selected = {};
	  $scope.maxSize = 2;  
//Limit number for pagination display number.  
	    $scope.totalCount = 0;  // Total number of items in all pages. initialize as a zero  
	    $scope.pageIndex = 1;   // Current page number. First page is 1.-->  
	    $scope.pageSizeSelected = 10; // Maximum number of items per page.	     
	    $scope.searchtext= $scope.searchtext;
		$scope.searchcategory = $scope.searchcategory;	*/
		
	/*$scope.getRequirementDetails = function(){
		
		$scope.userId = $window.localStorage.getItem("usid");
		$scope.status = "Open";
		rpoFactory.requirementById($scope.userId,$scope.status,$scope.pageIndex,$scope.pageSizeSelected,$scope.by,$scope.order,$scope.searchtext,$scope.searchcategory).then(function(res) {
			
			$scope.requirementDetails = res.result;
			$scope.noData = res.res;
			$scope.totalCount = res.totalRecords;
			$scope.Rskills = $scope.requirementDetails.skills;
			swal($scope.Rskills)
			//$scope.length = $scope.requirementDetails;
			$scope.education = $scope.candidateDetails.education;
			$scope.certificates = $scope.candidateDetails.certificates;
			$scope.skills = $scope.candidateDetails.skills;
			console.log($scope.requirementDetails);
			
		});
	}*/
	/*$scope.getRequirementDetails();*/
/*	 $scope.searchText = function(){
		 
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
	          }, 1000);*/
		/*$timeout(function () {
	             $scope.getRequirementDetails();   
	         }, 3000);*/
	  

});