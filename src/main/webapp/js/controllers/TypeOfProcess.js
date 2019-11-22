app
		.controller(
				"TypeOfProcessController",
				function($scope, $location, $routeParams, $filter, $rootScope,
						$window, TypeOfProcessService, invoiceServices,
						invoiceService1, $http, $q, selectedDetailsService,
						$timeout) {
					$scope.$on('$viewContentLoaded', function() {
						$scope.userRole = localStorage.getItem('role');//Lead
						$scope.loginId = localStorage.getItem('usid');//43

						$scope.candidates();
						//$scope.process1();
					})
					$scope.open1 = function() {
						$scope.popup1.opened = true;
					};
					$scope.open2 = function() {
						$scope.popup2.opened = true;
					};
					$scope.open3 = function() {
						$scope.popup3.opened = true;
					};
					$scope.open4 = function() {
						$scope.popup4.opened = true;
					};
					$scope.open8 = function() {
						$scope.popup8.opened = true;
					};
					$scope.open5 = function() {
						$scope.popup5.opened = true;
					};
					$scope.motherdob = function() {
						$scope.popup6.opened = true;
					};
					$scope.fatherdob = function() {
						$scope.popup7.opened = true;
					};

					$scope.popup1 = {
						opened : false
					};
					$scope.popup2 = {
						opened : false
					};
					$scope.popup3 = {
						opened : false
					};
					$scope.popup4 = {
						opened : false
					};
					$scope.popup5 = {
						opened : false
					};
					$scope.popup6 = {
						opened : false
					};
					$scope.popup8 = {
						opened : false
					};
					$scope.popup7 = {
						opened : false
					};
					$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
							'dd.MM.yyyy', 'shortDate' ];
					$scope.format = $scope.formats[0];

					var param1 = $routeParams.id;//  candID 50
					var canUserId = $routeParams.uid//
					$scope.mappedUserId = $routeParams.uid;
					var recID = $routeParams.recID
					$http
							.get(
									'rest/candidateReqMapping/getCandiateMapCandedateId/'
											+ param1 + '/' + $routeParams.recID
											+ '/' + $routeParams.uid)
							.then(
									function(response) {

										$scope.reqCanData = response.data.result[0]
										$scope.reqId = $scope.reqCanData[0].bdmReq.id;
										$scope.reqName = $scope.reqCanData[0].bdmReq.nameOfRequirement;
										$scope.clientName = $scope.reqCanData[0].bdmReq.client.clientName;
										$scope.spocName = $scope.reqCanData[0].bdmReq.addContact.contact_Name;
										$scope.candidateiname = $scope.reqCanData[0].candidate.firstName
												+ ' '
												+ $scope.reqCanData[0].candidate.lastName;
										$scope.candidateid = $scope.reqCanData[0].id;
										$scope.clientId = $scope.reqCanData[0].bdmReq.client.id;
										console.log($scope.clientId);
										//$scope.firstName$scope.reqCanData[0].candidate.firstName

									});

					//remaining details for interview progress after saving
					$http
							.get(
									'rest/interviewDetails/getInterviewDetailsById/'
											+ param1 + '/' + $routeParams.recID
											+ '/' + $routeParams.uid)
							.then(
									function(response) {
										$scope.interviewData = response.data.result;
										$scope.candidateiname = response.data.result.candidate.firstName
												+ ' '
												+ response.data.result.candidate.lastName;
										$scope.interviewAddress = response.data.result.address;
										$scope.interviewlocation = response.data.result.interviewLocation;
										$scope.typeofinterview = response.data.result.interviewType.id;
										$scope.modeofInterview = response.data.result.interviewType.modeofInterview;
										$scope.interviewTime = response.data.result.interviewTime;
										$scope.typeOfHiring = response.data.result.requirement.typeOfHiring;
										$scope.round = response.data.result.nameOfRound;
										$scope.interviewDate = response.data.result.interviewDate;
										$window.localStorage
												.setItem(
														'candidateID',
														response.data.result.candidate.id);
										$window.localStorage
												.setItem(
														'requirementID',
														response.data.result.requirement.id);//57

										console
												.log(
														"rest/interviewDetails/getInterviewDetailsById",
														response.data);
										console
												.log(
														"candidateID,requirementID",
														response.data.result.candidate.id,
														response.data.result.requirement.id)

									});

					//getting the rejected details

					var candidate_id = $window.localStorage
							.getItem('candidateID');
					var requirement_id = $window.localStorage
							.getItem('requirementID');
					$http
							.get(
									'rest/timeSlots/getCandidateTimeSlot/'
											+ candidate_id + '/'
											+ requirement_id)
							.then(
									function(response) {
										console
												.log(
														"rest/timeSlots/getCandidateTimeSlot",
														response.data);
										$scope.slot1_time = response.data.result.slot1Time;
										$scope.slot2_time = response.data.result.slot2Time;
										$scope.slot3_time = response.data.result.slot3Time;
										$scope.slot1_date = response.data.result.slot1Date;
										$scope.slot2_date = response.data.result.slot2Date;
										$scope.slot3_date = response.data.result.slot3Date;
										$scope.reason = response.data.result.reason;

									});

					$scope.canStatus = localStorage.getItem('canStatus');

					$http.get(
							'rest/addCandidate/candidateStausList/'
									+ $scope.canStatus).then(
							function(response) {
								$scope.processData = response.data.result;
								console.log(
										"rest/addCandidate/candidateStausList",
										response.data.result);
							});

					/*	$scope.interviewstatusClick = function(interviewstatus){
							
							
							console.log(interviewstatus);
								if(interviewstatus === "selected"){
									console.log($scope.interviewFeedbackForm);
									
									
								}
						
							
							
						}*/
					/*$timeout(function () {
					                  $scope.process1();
					                       }, 1000);*/

					//	save_12thMarkmemo
					/*@Rakesh Chengelli@*/

					//Upload All Files Start....
					$rootScope.files;
					$rootScope.fileName;
					$scope.upload10ThMM = function() {
						/*var file = $scope.myFile; */

						if ($scope._10thMarksMemo == undefined) {
							$scope.mes1 = "Please Select a File";
							return;
						} else {
							if ($scope._10thMarksMemo != undefined) {
								var ext = $scope._10thMarksMemo.name.split('.')
										.pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._10thMarksMemo;
									fileName = "_10thMarksMemo";
									$scope.mes1 = "";
									$scope.imageUpload();
								} else {
									$scope.mes1 = "Please Select only Pdf Files!";
									return;
								}
							}
						}

					}

					$scope.upload12ThMM = function() {
						if ($scope._12thMarksMemo == undefined) {
							$scope.mes2 = "Please Select a File";
							return;
						} else {
							if ($scope._12thMarksMemo != undefined) {
								var ext = $scope._12thMarksMemo.name.split('.')
										.pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._12thMarksMemo;
									fileName = "_12thMarksMemo";
									$scope.mes2 = "";
									$scope.imageUpload();
								} else {
									$scope.mes2 = "Please Select only Pdf Files !";
									return;
								}

							}
						}
					}
					$scope.uploadHMM = function() {
						if ($scope._highestMarkmemo == undefined) {
							$scope.mes3 = "Please Select a File";
							return;
						} else {
							if ($scope._highestMarkmemo != undefined) {
								var ext = $scope._highestMarkmemo.name.split(
										'.').pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._highestMarkmemo;
									fileName = "_highestMarkmemo";
									$scope.mes3 = "";
									$scope.imageUpload();
								} else {
									$scope.mes3 = "Please Select only Pdf Files!";
									return;
								}

							}
						}

					}
					$scope.uploadLatestSalaryslips = function() {
						if ($scope._latestSalaryslips == undefined) {
							$scope.mes4 = "Please Select a File";
							return;
						} else {
							if ($scope._latestSalaryslips != undefined) {
								var ext = $scope._latestSalaryslips.name.split(
										'.').pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._latestSalaryslips;
									fileName = "_latestSalaryslips";
									$scope.mes4 = "";
									$scope.imageUpload();
								} else {
									$scope.mes4 = "Please Select only Pdf Files !";
									return;
								}

							}
						}

					}

					$scope.uploadBankStatements = function() {
						if ($scope._bankStatements == undefined) {
							$scope.mes6 = "Please Select a File";
							return;
						} else {
							if ($scope._bankStatements != undefined) {
								var ext = $scope._bankStatements.name
										.split('.').pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._bankStatements;
									fileName = "_bankStatements";
									$scope.mes6 = "";
									$scope.imageUpload();
								} else {
									$scope.mes6 = "Please Select only Pdf Files !";
									return;
								}

							}
						}

					}
					$scope.uploadOfferedLetter = function() {
						if ($scope._offeredLetter == undefined) {
							$scope.mes7 = "Please Select a File";
							return;
						} else {
							if ($scope._offeredLetter != undefined) {
								var ext = $scope._offeredLetter.name.split('.')
										.pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._offeredLetter;
									fileName = "_offeredLetter";
									$scope.mes7 = "";
									$scope.imageUpload();
								} else {
									$scope.mes7 = "Please Select only Pdf  Files !";
									return;
								}

							}
						}

					}

					$scope.uploadRelieving = function() {
						if ($scope._RelievingLetter == undefined) {
							$scope.mes5 = "Please Select a File";
							return;
						} else {
							if ($scope._RelievingLetter != undefined) {
								var ext = $scope._RelievingLetter.name.split(
										'.').pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._RelievingLetter;
									fileName = "_RelievingLetter";
									$scope.mes5 = "";
									$scope.imageUpload();
								} else {
									$scope.mes5 = "Please Select only Pdf  Files !";
									return;
								}

							}
						}

					}
					$scope.uploadPassportCopy = function() {
						if ($scope._passportCopy == undefined) {
							$scope.mes8 = "Please Select a File";
							return;
						} else {
							if ($scope._passportCopy != undefined) {
								var ext = $scope._passportCopy.name.split('.')
										.pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._passportCopy;
									fileName = "_passportCopy";
									$scope.mes8 = "";
									$scope.imageUpload();
								} else {
									$scope.mes8 = "Please Select only Pdf Files !";
									return;
								}

							}

						}
					}

					$scope.uploadForm16 = function() {
						if ($scope._form16Certificate == undefined) {
							$scope.mes9 = "Please Select a File";
							return;
						} else {
							if ($scope._form16Certificate != undefined) {
								var ext = $scope._form16Certificate.name.split(
										'.').pop();
								console.log(ext);
								if (ext == 'pdf') {
									files = $scope._form16Certificate;
									fileName = "_form16Certificate";
									$scope.mes9 = "";
									$scope.imageUpload();
								} else {
									$scope.mes9 = "Please Select only Pdf Files !";
									return;
								}

							}
						}
					}
					$scope.uploadasZip = function() {

						/*var file = $scope.myFile; */
						if ($scope.uploadZip == undefined) {
							$scope.mes11 = "Please Select a File";
							return;
						} else {
							if ($scope.uploadZip != undefined) {
								var ext = $scope.uploadZip.name.split('.')
										.pop();
								console.log(ext);
								if (ext == 'zip') {
									files = $scope.uploadZip;
									fileName = "uploadZip";
									$scope.mes11 = "";
									$scope.allUpload();
								} else {
									$scope.mes11 = "Please Select only Zip Files!";
									return;
								}
							}
						}

					}
					$scope.allUpload = function() {

						var id = $window.localStorage.getItem("candidateid");
						var deferred = $q.defer();
						var hd = {
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}
						var formData = new FormData();
						formData.append('zipfile', files);
						$http.post("rest/addCandidate/uploadZip/" + id,
								formData, hd).success(function(response) {
							console.log(response);
							if (response.status == "OK") {
								swal(response.status);
								//							 $scope.uploadS1=true;
								//							 $scope.uploadE1=false; 
								//							 $scope.mes2="";
							}
							//						  else{
							//							   $scope.uploadS2=false;
							//							   $scope.uploadE2=true; 
							//						  }
						});
					}
					$scope.imageUpload = function() {
						var id = $window.localStorage.getItem("candidateid");
						var deferred = $q.defer();
						var hd = {
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}
						var formData = new FormData();
						formData.append('file', files);
						$http.post(
								"rest/addCandidate/upload/" + fileName + "/"
										+ id, formData, hd).success(
								function(response) {
									console.log(response);
									if (response.conflict == "OK") {
										swal(response.res);
										//							 $scope.uploadS1=true;
										//							 $scope.uploadE1=false; 
										//							 $scope.mes2="";
									}
									//						  else{
									//							   $scope.uploadS2=false;
									//							   $scope.uploadE2=true; 
									//						  }
								});
					}

					$scope.save = function() {
						var id = $window.localStorage.getItem("candidateid");

						if ($scope.processtype == undefined) {
							swal("Select Type of Process");
							return false;
						} else if ($scope.interviewstatus == undefined) {
							swal("Select Status");
							return false;
						} else {
							/*  var status = $scope.interviewstatus;*/
							var saveStatus = {
								candidateId : id,
								requirementId : $routeParams.recID,
								loginId : $routeParams.uid,
								/*status:$scope.interviewstatus,*/
								candidateStatus : $scope.interviewstatus
							}
							var deferred = $q.defer();
							$http
									.post(
											"rest/addCandidate/updatingstatusbylead/",
											saveStatus)
									.success(
											function(response) {
												console.log(response);
												if (response.status = "StatusSuccess") {
													swal("Candidate Details Updated Successfully");
												}
											});

							$location.path("/CandidateList1");
						}
					}
					$scope.saveCtoh = function() {
						if ($scope.docu == undefined) {
							swal('Please Upload documents as Zip File and select Checkbox');
							return false;
						} else {
							var id = $window.localStorage
									.getItem("candidateid");
							$scope.reqId;
							$scope.amid;
							var status = "Offer Release";
							var uploadedasZip = {
								"loginId" : $scope.amid,
								"candidateId" : id,
								"requirementId" : $scope.reqId,
								"candidateStatus" : status
							}
							var deferred = $q.defer();
							$http
									.post(
											"rest/addCandidate/updatingstatusbylead/",
											uploadedasZip)
									.success(
											function(response) {
												console.log(response);
												if (response.status = "StatusSuccess") {
													swal("Candidate Details Updated Successfully");
												}
											});

							$location.path("/CandidateList1");
						}
					}

					$scope.saveStatus = function() {
						if ($scope.processtype == undefined) {
							swal("Select Type Of Process")
						} else if ($scope.interviewstatus == undefined) {
							swal("Select Offer Status")
						} else {
							var id = $window.localStorage
									.getItem("candidateid");

							//	            	   var status = "Offer Release";
							var interviewStatus = $scope.interviewstatus;
							var offerStatus = $scope.interviewstatus;
							var deferred = $q.defer();

							$http
									.post(
											"rest/addCandidate/updatingOfferStatus/"
													+ id + '/'
													+ $routeParams.recID + '/'
													+ offerStatus + '/'
													+ $routeParams.uid)
									.success(
											function(response) {
												console.log(response);
												if (response.status = "StatusSuccess") {
													swal("Offer Status updated Successfully")
												}
											});

							$location.path("/CandidateList1");
						}
					}
					$scope.saveOnBoardStatus = function() {
						var id = $window.localStorage.getItem("candidateid");

						if ($scope.processtype == undefined) {
							swal("Select Type Of Process");
							return false;
						} else if ($scope.interviewstatus == undefined) {
							swal("Select Status");
							return false;
						} else if ($scope.onboardeddate == undefined
								|| $scope.onboardeddate == "") {
							swal("Select On Boarding Date");
							return false;
						} else if ($scope.CTC == undefined || $scope.CTC == "") {
							swal("Please Enter CTC");
							return false;
						} else {
							var onBoardFields = {
								"candidateId" : id,
								"status" : $scope.interviewstatus,
								"onBoardingDate" : $scope.onboardeddate,
								"ctc" : $scope.CTC,
								"reqId" : $routeParams.recID,
								"userId" : $routeParams.uid
							}
							//	            	   var status = "Offer Release";	
							/*  var onBoardStatus= $scope.interviewstatus;
							  var onboardeddate =$scope.onboardeddate*/
							var deferred = $q.defer();
							$http.post(
									"rest/addCandidate/updatingOnBoardStatus",
									onBoardFields).success(function(response) {
								console.log(response.status);
								if (response.status = "StatusSuccess") {
									swal("Raise Invoice Status Updated")
								}
							});
							$location.path("/CandidateList1");
						}
					}
					$scope.saveCtc = function() {
						if ($scope.processtype == undefined) {
							swal("Select Type of Processs");
							return false;
						} else if ($scope.interviewstatus == undefined) {
							swal("Select Offer Status");
							return false;
						}
						/* else if($scope.employeeName == undefined || $scope.employeeName == ''){
						   swal("Enter Employee Name");
						    return false;
						 }*/
						else if ($scope.employeeDob == undefined) {
							swal("Select Date Of Birth");
							return false;
						} else if ($scope.employeeType == undefined) {
							swal("Select Employee Type");
							return false;
						} else if ($scope.skill == undefined) {
							swal("Select Skill");
							return false;
						} else if ($scope.onboardeddate == undefined) {
							swal("Select Joining Date");
							return false;
						} else if ($scope.fixedCtc == undefined
								|| $scope.fixedCtc == '') {
							swal("Enter Fixed CTC");
							return false;
						} else if ($scope.variableCtc == undefined
								|| $scope.variableCtc == '') {
							swal("Enter Variable CTC");
							return false;
						} else if ($scope.totalCtc == undefined
								|| $scope.totalCtc == '') {
							swal("Enter Total CTC");
							return false;
						} else if ($scope.designation == undefined
								|| $scope.designation == '') {
							swal("Enter Designation");
							return false;
						} else if ($scope.jobLocation == undefined) {
							swal("Select Job Location");
							return false;
						} else {
							$('#previewData').modal('show');
							var id = $window.localStorage
									.getItem("candidateid");
							$scope.isMetroCity = "no";
							//	            	   var name=  $scope.employeeName;

							//	            	   var status = "Offer Release";

							var ctcStatus = {
								"employeeName" : $scope.employeeName,
								"employeeId" : $scope.employeeId,
								"employeeType" : $scope.employeeType,
								"skill" : $scope.skill,
								"fixedCtc" : $scope.fixedCtc,
								"variableCtc" : $scope.variableCtc,
								"totalCtc" : $scope.totalCtc,
								"joiningDate" : $scope.onboardeddate,
								"customerId" : $scope.clientId,
								"customerName" : $scope.clientName,
								"employeeDob" : $scope.employeeDob,
								"spouseName" : $scope.spouseName,
								"spouseDob" : $scope.spouseDob,
								"kid1Name" : $scope.child1Name,
								"kid1Dob" : $scope.child1Dob,
								"kid2Name" : $scope.child2Name,
								"kid2Dob" : $scope.child2Dob,
								"fatherName" : $scope.fatherName,
								"fatherDob" : $scope.fatherDob,
								"motherName" : $scope.motherName,
								"motherDob" : $scope.motherDob,
								"isMetroCity" : $scope.isMetroCity,
								"jobLocation" : $scope.jobLocation,
								"designation" : $scope.designation
							}

							var deferred = $q.defer();

							$http
									.post("rest/addCandidate/getCtcDetails",
											ctcStatus)
									.success(
											function(response) {

												console.log(response);
												$scope.ctcdeduction = response.result.deduction;
												$scope.employeeEarning = response.result.employeeEarning;
												$scope.employeedetails = response.result.employee;
												console.log("CTC status"
														+ $scope.ctcdeduction);
												console
														.log("employeeEarning status"
																+ $scope.employeeEarning);
												console
														.log("employeedetails"
																+ $scope.employeedetails);
												/* if (response.status="null"){
												  swal("something went wrong");
												 }*/
											});

							//  $location.path("/CandidateList1");
						}
					}
					//	               $scope.redirect = function(){
					//	            	   $location.path("/CandidateList1");
					//	               }

					$scope.saveFinalCtc = function() {
						var id = $window.localStorage.getItem("candidateid");

						//	            	   var status = "Offer Release";
						var interviewStatus = $scope.interviewstatus;
						var offerStatus = $scope.interviewstatus;
						var deferred = $q.defer();
						$http
								.post(
										"rest/addCandidate/updatingOfferStatus/"
												+ id + "/" + $routeParams.recID
												+ "/" + offerStatus + "/"
												+ $routeParams.uid)
								.success(
										function(response) {
											console.log(response);
											if (response.status = "StatusSuccess") {
												swal("On Borad Status updated Successfully")
											}
										});

						$location.path("/CandidateList1");
					}

					//				
					//                  $scope.save_10thMarkmemo=function(myFile)
					//                  
					//				  {
					//                	  var file = myFile;  
					//                	  
					//					  var id = $window.localStorage.getItem("candidateid");
					//					  if(file==undefined)
					//					  {
					//						  $scope.mes1="Please Select File";
					//						  return;
					//					  }
					//					   
					//					  var deferred = $q.defer();
					//					  var hd = {
					//						       headers: {'Content-Type': 'multipart/form-data' }
					//						}
					//					  var formData = new FormData();
					//					  formData.append('file', file);
					//					  $http.post("rest/addCandidate/upload/"+id,formData,hd).success(function(response){
					//						  deferred.resolve(response);	
					//						  }).error(function(err){
					//						  deferred.reject(err);
					//						  })
					//						  return deferred.promise;
					//				  }

					/*					         $http.post("rest/addCandidate/upload/"+id,file).then(function(response) {
					 console.log(response);
					 if(response.data.status=="StatusSuccess"){
					 $scope.uploadS1=true;
					 $scope.uploadE1=false;
					 $scope.mes1="";								 
					 }
					 else{
					 $scope.uploadS1=false;
					 $scope.uploadE1=true; 
					 }
					
					
					 }); */

					//Custom directive for Image Upload

					/*                  app.directive('fileModel', ['$parse', function ($parse) {
					
					 return {
					
					 restrict: 'A',
					 link: function(scope, element, attrs) {
					 var model = $parse(attrs.fileModel);
					 var modelSetter = model.assign;
					 element.bind('change', function(){
					 scope.$apply(function(){
					 modelSetter(scope, element[0].files[0]);
					 });
					 });
					 }
					 };
					 }]);*/

					// save_12thMarkmemo
					$scope.save_12thMarkmemo = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes2 = "Please Select File";
							return;
						}

						$http.post("rest/addCandidate/post_12thMarkmemo/" + id,
								file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS2 = true;
								$scope.uploadE2 = false;
								$scope.mes2 = "";
							} else {
								$scope.uploadS2 = false;
								$scope.uploadE2 = true;
							}
						});
					}

					// post_highestMarkmemo

					$scope.save_highestMarkmemo = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes3 = "Please Select File";
							return;
						}

						$http.post(
								"rest/addCandidate/post_highestMarkmemo/" + id,
								file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS3 = true;
								$scope.uploadE3 = false;
								$scope.mes3 = "";
							} else {
								$scope.uploadS3 = false;
								$scope.uploadE3 = true;
							}

						});

					}

					// post_latestSalaryslips

					$scope.save_latestSalaryslips = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes4 = "Please Select File";
							return;
						}

						$http.post(
								"rest/addCandidate/post_latestSalaryslips/"
										+ id, file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS4 = true;
								$scope.uploadE4 = false;
								$scope.mes4 = "";
							} else {
								$scope.uploadS4 = false;
								$scope.uploadE4 = true;
							}

						});

					}

					// post_salaryCertificates

					$scope.save__bankStatements = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes5 = "Please Select File";
							return;
						}

						$http.post(
								"rest/addCandidate/post_bankStatements/" + id,
								file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS5 = true;
								$scope.uploadE5 = false;
								$scope.mes5 = "";
							} else {
								$scope.uploadS5 = false;
								$scope.uploadE5 = true;
							}

						});

					}

					// post_bankStatements

					$scope.save_offeredLetter = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes6 = "Please Select File";
							return;
						}

						$http.post(
								"rest/addCandidate/post_offeredLetter/" + id,
								file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS6 = true;
								$scope.uploadE6 = false;
								$scope.mes6 = "";
							} else {
								$scope.uploadS6 = false;
								$scope.uploadE6 = true;
							}

						});

					}

					// post_offeredandReliving

					$scope.save_RelievingLetter = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes7 = "Please Select File";
							return;
						}

						$http.post(
								"rest/addCandidate/post_RelievingLetter/" + id,
								file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS7 = true;
								$scope.uploadE7 = false;
								$scope.mes7 = "";
							} else {
								$scope.uploadS7 = false;
								$scope.uploadE7 = true;
							}

						});

					}

					// post_passportCopy

					$scope.save_passportCopy = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes8 = "Please Select File";
							return;
						}

						$http.post("rest/addCandidate/post_passportCopy/" + id,
								file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS8 = true;
								$scope.uploadE8 = false;
								$scope.mes8 = "";
							} else {
								$scope.uploadS8 = false;
								$scope.uploadE8 = true;
							}

						});

					}

					// post_form16Certificate
					$scope.save_form16Certificate = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes9 = "Please Select File";
							return;
						}

						$http.post(
								"rest/addCandidate/post_form16Certificate/"
										+ id, file).then(function(response) {
							console.log(response);
							if (response.data.status == "StatusSuccess") {
								$scope.uploadS9 = true;
								$scope.uploadE9 = false;
								$scope.mes9 = "";
							} else {
								$scope.uploadS9 = false;
								$scope.uploadE9 = true;
							}

						});

					}
					$scope.uploadZip = function(file) {
						var id = $window.localStorage.getItem("candidateid");
						if (file == undefined) {
							$scope.mes11 = "Please Select File";
							return;
						}

						$http
								.post("rest/addCandidate/uploadZip/" + id, file)
								.then(
										function(response) {
											console.log(response);
											if (response.data.status == "StatusSuccess") {
												$scope.uploadS11 = true;
												$scope.uploadE11 = false;
												$scope.mes11 = "";
											} else {
												$scope.uploadS11 = false;
												$scope.uploadE11 = true;
											}

										});

					}

					$scope.finalSubmit = function() {
						if ($scope.uploadS1 && $scope.uploadS2
								&& $scope.uploadS3 && $scope.uploadS4
								&& $scope.uploadS5 && $scope.uploadS6
								&& $scope.uploadS7 && $scope.uploadS8
								&& $scope.uploadS9) {
							var id = $window.localStorage
									.getItem("candidateid");
							$scope.reqId;
							$scope.amid;

							$http
									.post(
											'rest/addCandidate/updatingstatus/test/'
													+ id + '/' + $scope.reqId
													+ '/' + $scope.amid)
									.then(
											function(response) {
												console.log(response);
												if (response.data.status == "StatusSuccess") {
													$scope.responseMes = "All documents uploaded successfully";
													$scope.ok = true;
												} else {
													$scope.responseMes = "Internal Server Error"
													$scope.ok = true;
												}

											});
							$location.path("/CandidateList1");
						} else {
							$scope.mes10 = "Upload All Document";
							return;
						}
					}

					//offere accepted details

					$scope.saveAccept = function() {
						var id = $window.localStorage.getItem("candidateid");

						if ($scope.offer == 'rejected') {
							var values = {
								offereStatus : $scope.offer,
								offRejectedReasion : $scope.reasion
							}

							$http.post("rest/addCandidate/accepteddata/" + id,
									values).then(function(response) {
								console.log(response);
								if (response.data.status == "StatusSuccess") {
									$scope.uploadS1 = true;
									$scope.uploadE1 = false;
									$scope.mes1 = "";
								} else {
									$scope.uploadS1 = false;
									$scope.uploadE1 = true;
								}

							});
						} else if ($scope.offer == 'accepted') {
							var values = {
								offereStatus : $scope.offer,
								doj : $scope.doj1
							}

							$http.post("rest/addCandidate/accepteddata/" + id,
									values).then(function(response) {
								console.log(response);
								if (response.data.status == "StatusSuccess") {
									$scope.uploadS1 = true;
									$scope.uploadE1 = false;
									$scope.mes1 = "";
								} else {
									$scope.uploadS1 = false;
									$scope.uploadE1 = true;
								}

							});
						}
					}

					// candidate details
					$scope.candidates = function() {
						var a = angular.fromJson($window.localStorage
								.getItem('candidateid'));
						console.log(a);
						$rootScope.candidate_id = a;
						console.log($rootScope.candidate_id);
						console.log(" exis candidate");
						var promise = TypeOfProcessService.getcandidateData(a);
						console.log(promise);
						promise
								.then(function(response) {
									console.log(response.data.result);

									$scope.candidateDetails = response.data.result;
									$scope.candidateName = $scope.candidateDetails.firstName
											+ ' '
											+ $scope.candidateDetails.lastName;
									//									$scope.companyname = $scope.candidateDetails.client.clientName;
									$scope.candidateemail = $scope.candidateDetails.email;
									$scope.phone = $scope.candidateDetails.mobile;
									$scope.total = $scope.candidateDetails.totalExperience;
									$scope.relevent = $scope.candidateDetails.relevantExperience;
									$scope.comapany = $scope.candidateDetails.currentCompanyName;
									$scope.ctc = $scope.candidateDetails.currentCTC;
									var date = new Date(
											$scope.candidateDetails.doj)
											.getDate();
									var month = new Date(
											$scope.candidateDetails.doj)
											.getMonth();
									var year = new Date(
											$scope.candidateDetails.doj)
											.getYear();
									$scope.doj1 = date + "/" + month + "/"
											+ year;
									//$scope.doj1=$scope.candidateDetails.doj;

									/*$scope.requirement = $scope.candidateDetails.bdmReq[0].requirementDescription;*/
									$scope.requirement = $scope.reqCanData[0].bdmReq.nameOfRequirement;
									$scope.clientName = $scope.reqCanData[0].bdmReq.client.clientName;
									//$window.localStorage.setItem("processtype",$scope.candidateDetails.candidateStatus);
									$rootScope.process = $scope.candidateDetails.candidateStatus;
									$scope.process1();
									console.log($scope.candidateDetails);
									$scope.cid = response.data.result.id;
									console.log($scope.cid);

									$scope.canid = $scope.candidateDetails.id;
									//									$scope.bid=$scope.candidateDetails.bdmReq.id;
									$scope.bid = $scope.reqId
									console.log($scope.bid)
									$scope.rcid = $scope.candidateDetails.user.id;
									$scope.amid = $window.localStorage
											.getItem("usid");

								});

					}

					$rootScope.processDetails = [];

					$scope.process1 = function() {
						//$scope.status=localStorage.getItem('canStatus'); 
						var rr = $window.localStorage.getItem("role");
						var process = $rootScope.process
						TypeOfProcessService
								.getprocessData(rr, process)
								.then(
										function(response) {
											$rootScope.processDetails = response.data.result;

										});
					}

					$scope.ojas = "Ojas Innovative Technologies India Pvt Ltd ";
					$scope.ojas1 = "MJR Magnifique, 9th Floor, BLOCK - C, Raidurgam X Roads,, Hyderabad, Telangana 500008";

					// interview type

					$scope.interviewtype = function() {
						console.log(" exis interviewtype");
						var promise = TypeOfProcessService.getinterviewData();
						//console.log('kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk',promise);
						promise
								.then(function(response) {
									//console.log('jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', response.data.result);
									$scope.interviewDetails = response.data.result;

									console
											.log(
													"[get interview type from 'TypeOfProcessService'] interviewDetails ",
													$scope.interviewDetails);
								});

					}
					$scope.hiringChange = function() {

						if ($scope.fbk.recRound == "Hire") {
							$http
									.get('rest/addstatus/findstatusforc2h')
									.then(
											function(response) {
												$scope.status = response.data.result;
												console
														.log(
																"rest/addstatus/findstatusforc2h",
																response.data.result);
											});
						} else {
							$http.get('rest/addstatus/findstatus').then(
									function(response) {
										$scope.status = response.data.result;
										console.log(
												"rest/addstatus/findstatus",
												response.data.result);
									});
						}
					}
					$scope.interviewchange = function() {

						//						 swal($scope.processtype.status );
						if ($scope.processtype.status == "interview schedule") {
							document.getElementById("form_process").style.display = "block";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Interview FeedBack") {
							document.getElementById("form_proc").style.display = "block";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Profile has been Accepted by Lead") {
							document.getElementById("form_approved").style.display = "block";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Submit to Lead") {

							document.getElementById("form_sub").style.display = "block";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

							if ($scope.candidateDetails.candidateStatus == "registered") {
								$scope.changestatus = "submitted to AM";

							}
						} else if ($scope.processtype.status == "Rejected") {
							document.getElementById("form-amreject").style.display = "block";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Query") {
							document.getElementById("form-AmPending").style.display = "block";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Submitted to Customer") {
							document.getElementById("form-subcust").style.display = "block";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Customer Shortlisted") {
							document.getElementById("form-custshort").style.display = "block";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";

						} else if ($scope.processtype.status == "Customer Rejected") {
							document.getElementById("form-custreject").style.display = "block";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";

						} else if ($scope.processtype.status == "Customer feedback") {
							document.getElementById("form_feedback").style.display = "block";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Offered") {
							document.getElementById("form_offered").style.display = "block";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Document Collection") {
							document.getElementById("form_upload").style.display = "block";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						} else if ($scope.processtype.status == "Offer Status") {
							document.getElementById("form_offereAcept").style.display = "block";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
						} else {
							document.getElementById("form-custreject").style.display = "none";
							document.getElementById("form-custshort").style.display = "none";
							document.getElementById("form-subcust").style.display = "none";
							document.getElementById("form-AmPending").style.display = "none";
							document.getElementById("form_process").style.display = "none";
							document.getElementById("form_proc").style.display = "none";
							document.getElementById("form_sub").style.display = "none";
							document.getElementById("form_approved").style.display = "none";
							document.getElementById("form-amreject").style.display = "none";
							document.getElementById("form_feedback").style.display = "none";
							document.getElementById("form_offered").style.display = "none";
							document.getElementById("form_upload").style.display = "none";
							document.getElementById("form_Release").style.display = "none";
							document.getElementById("form_offereAcept").style.display = "none";

						}

					}
					$scope.interviewchangetest = function() {
					}
					//$scope.interviewstatus();

					// get client name
					$scope.getclientName = function() {
						console.log("idgetting");
						console.log($scope.candidatename);
						console.log($scope.candidatename.client);
						$scope.companyname = $scope.candidatename.client.cname;

					}
					// save interview schedule

					$scope.saveInterviewSchedule = function() {

						$scope.mid = $rootScope.candidate_id;
						var id = $scope.mid;
						console.log($scope.mid);

						TypeOfProcessService.sendIntervieMail(id);
						$scope.process1();
						$location.path("/CandidateList1");

						//$window.location.reload();

					}
					$scope.submitInterviewProcess = function() {
						debugger
						if ($scope.spocName == undefined
								|| $scope.spocName == '') {
							swal('Please enter Spoc Name');
						} else if ($scope.interviewDate == undefined) {
							swal('Please Select Interview Date');
						} else if ($scope.interviewTime == undefined) {
							swal('Please enter Interview Time');
						} else if ($scope.typeofinterview == undefined) {
							swal('Please Select Interview Type');
						} else if ($scope.round == undefined) {
							swal('Please Select Round');
						} else if ($scope.interviewlocation == undefined
								|| $scope.interviewAddress == '') {
							swal('Please Enter Interview Location');
						} else if ($scope.interviewAddress == undefined
								|| $scope.interviewAddress == '') {
							swal('Please Enter Interview Address');
						} else {
							var obj = {
								candidate : {
									id : $rootScope.candidate_id
								},
								requirement : {
									id : $scope.reqId
								},
								spocName : $scope.spocName,
								interviewDate : $scope.interviewDate,
								interviewTime : $scope.interviewTime,
								nameOfRound : $scope.round,
								interviewType : {
									id : $scope.typeofinterview
								},
								interviewLocation : $scope.interviewlocation,
								address : $scope.interviewAddress,
								userId : $routeParams.uid,
							}
							$http
									.post('rest/interviewDetails', obj)
									.then(
											function(response) {
												console
														.log(
																"[post] rest/interviewDetails",
																obj);
												if (response.data.status == "StatusSuccess") {
													swal("Submitted Successfully")
													$location
															.path('/CandidateList1')
												} else {

													swal("Error")
												}

											});
						}
					}
					//					// update interview status

					$scope.updateInterviewstatus = function() {
						var candidate_Id = $window.localStorage
								.getItem('candidateid');

						$http.post(
								'rest/interviewDetails/updateInterviewStatusDetails/'
										+ candidate_Id + '/'
										+ $routeParams.recID + '/'
										+ $routeParams.uid).then(
								function(response) {
									console.log("update interview status",
											response);
									$location.path("/CandidateList1")
								});

					}
					//==================================================================================		
					//submitting the rejected details	
					$scope.submitRejected = function() {
						if ($scope.reason == undefined || $scope.reason == "") {
							swal("Enter Reason");
							return false;
						} else {
							var rej = {
								reason : $scope.reason,
								candidate : {
									id : $rootScope.candidate_id
								},
								bdmReq : {
									id : $scope.reqId
								},
								mappedUser : {
									id : $scope.mappedUserId
								}
							}
							$http
									.post('rest/timeSlots', rej)
									.then(
											function(response) {
												console.log("submitRejected",
														response);
												$location
														.path('/CandidateList1');
											});

						}
					}
					//	==========================================================================================

					$scope.savecustShort = function() {

						// date format
						/*var a = moment($scope.time).format('HH:mm:ss');
						console.log(a);*/
						//$scope.b = a;
						//console.log($scope.b);
						$scope.eid = $rootScope.candidate_id;
						var interviewvalues = {
							requirementId : $scope.reqId,
							userId : $routeParams.uid,
							candidateid : $scope.eid,
							candidateiname : $scope.candidateiname,
							typeofprocess : $scope.processtype.status,
						}
						TypeOfProcessService.sendinterviewData(interviewvalues);
						$scope.process1();
						$location.path("/CandidateList1");
						/*if($scope.typeofinterview.modeofInterview=='F2F')
						{
							 
						var interviewvalues = {
							candidateid : $scope.eid,
							candidateiname : $scope.candidatename,
							candidateEmail : $scope.candidateemail,
							typeofprocess : $scope.processtype.status,
							companyname : $scope.companyname,
							typeofinterview : $scope.typeofinterview.modeofInterview,
							address : $scope.addr,
							city : $scope.address,
							district : $scope.client[1].City,
							state : $scope.client[1].State,
							country : $scope.client[1].Country,
							pincode : $scope.pincode,
							interviewdate : $scope.idate,
							time : $scope.time1,
							contactperson : $scope.person,
							mobilenumber : $scope.mobile,
							webxId:$scope.webx
						};
						console.log(interviewvalues);*/
						/*TypeOfProcessService.sendinterviewData(interviewvalues);
						$scope.process1();
						$location.path("/CandidateList1");
						//}
						else
						
						{
							var interviewvalues = {
							candidateid : $scope.eid,
							candidateiname : $scope.candidatename,
							candidateEmail : $scope.candidateemail,
							typeofprocess : $scope.processtype.status,
							companyname : $scope.companyname,
							typeofinterview : $scope.typeofinterview.modeofInterview,
							address : $scope.addr,
							city : $scope.address,
							district :"",
							state :"",
							country :"",
							pincode : $scope.pincode,
							interviewdate : $scope.idate,
							time : $scope.time1,
							contactperson : $scope.person,
							mobilenumber : $scope.mobile,
							webxId:$scope.webx
						};
						console.log(interviewvalues);
						TypeOfProcessService.sendinterviewData(interviewvalues);
						$scope.process1();
						$location.path("/CandidateList1");
							
							
						}*/
					}

					//navigation

					$scope.navigationToOffeLetter = function() {

						$location.path("/offerLetter");
					}

					$scope.getInterviewShedule = function() {

						console.log("getInterviewShedule");
						var nid = angular.fromJson($window.localStorage
								.getItem('candidateid'));
						console.log(";;;;;;;;;;;;;;;" + nid);
						var promise = TypeOfProcessService
								.getInterviewDetails(nid);
						promise
								.then(function(response) {
									$scope.Interviewdetails = response.data.result;
									//console.log($scope.Interviewdetails);
									if ($scope.Interviewdetails !== null) {
										$scope.addr1 = $scope.Interviewdetails.address;
										console.log($scope.addr1);
										$scope.City = $scope.Interviewdetails.city;

										$scope.candidateemail = $scope.Interviewdetails.candidateEmail;
										$scope.person1 = $scope.Interviewdetails.contactperson;
										$scope.Country = $scope.Interviewdetails.country;
										$scope.address = $scope.Interviewdetails.district;

										$scope.hj = new Date(
												$scope.Interviewdetails.interviewdate);
										$scope.idate1 = ($scope.hj.getDay()
												+ "/" + $scope.hj.getMonth()
												+ "/" + $scope.hj.getFullYear());
										$scope.mobile1 = $scope.Interviewdetails.mobilenumber;
										$scope.pincode1 = $scope.Interviewdetails.pincode;
										console
												.log($scope.Interviewdetails.pincode1);
										$scope.State = $scope.Interviewdetails.state;
										console
												.log($scope.Interviewdetails.time);
										var s = $scope.Interviewdetails.time;
										var a = moment(s).format('HH:mm:ss');
										$scope.b = a;
										$scope.time1 = $scope.b;
										$scope.typeofinterview = $scope.Interviewdetails.typeofinterview;
										$scope.webxid = $scope.Interviewdetails.webxId;
									}
								});
						//$scope.process1();
					}
					$scope.getInterviewShedule();

					$scope.getDetails = function() {
						var pin = $scope.pincode;
						var promise = TypeOfProcessService.getDetails(pin);
						promise.then(function(response) {
							$scope.client = response.Data;
							console.log($scope.client);
						});
						//$scope.process1();
					}

					//$scope.process1();
					//$scope.candidates();
					$scope.interviewtype();

					$scope.get = function() {
						cid = $scope.cid;
						console.log(cid);
						var promise1 = TypeOfProcessService.getaddress(cid);
						promise1.then(function(response) {
							console.log(response.data);
						});
						//();
					}

					$scope.fid = $rootScope.candidate_id;
					$scope.fbk = {};
					$scope.cgk = {};

					console.log($scope.fid);
					$scope.saveInterviewFeedback = function() {

						var feedbackdata = {
							candidateid : $scope.candidateDetails.id,
							candidateiname : $scope.candidateiname,
							candidateemail : $scope.candidateDetails.email,
							typeofprocess : $scope.processtype.status,
							companyname : $scope.clientName,
							requirementType : $scope.internal,
							nameOfRound : $scope.round,
							interviewStatus : $scope.interviewstatus,
							clientFeedback : $scope.clientfeedback,
							internalFeedback : $scope.internalfeedback,
							recommendedNextRound : $scope.fbk.recRound,
							recommendedProcess : $scope.cgk.status.status,
							requirementId : $scope.reqId,
							userId : $routeParams.uid

						};
						console.log("saveInterviewFeedback", feedbackdata);

						TypeOfProcessService.sendFeedbackData(feedbackdata);
						//$scope.process1();
						$location.path("/CandidateList1");
						//$scope.process1();
						// $window.location.reload();
						//$scope.process1();

					}
					//rejected process
					$scope.saveInterviewFeedbackforRejeted = function() {

						var feedbackrejectedddata = {
							candidateid : $scope.candidateDetails.id,
							candidateiname : $scope.candidateiname,
							candidateemail : $scope.candidateDetails.email,
							typeofprocess : $scope.processtype.status,
							companyname : $scope.clientName,
							requirementType : $scope.internal,
							nameOfRound : $scope.round,
							interviewStatus : $scope.interviewstatus,
							clientFeedback : $scope.clientfeedback,
							internalFeedback : $scope.internalfeedback,
							requirementId : $scope.reqId,
							userId : $routeParams.uid

						};
						console.log("feedbackrejectedddata",
								feedbackrejectedddata);

						TypeOfProcessService
								.sendFeedbackData(feedbackrejectedddata);
						//$scope.process1();
						$location.path("/CandidateList1");
						//$scope.process1();
						// $window.location.reload();
						//$scope.process1();

					}

					$scope.getInterviewFeedback = function() {

						var gid = angular.fromJson($window.localStorage
								.getItem('candidateid'));
						var promise = TypeOfProcessService
								.getFeedbackDetails(gid);
						promise
								.then(function(response) {
									$scope.Feddbackdetails = response.data.result;
									console.log($scope.Feddbackdetails);
									if ($scope.Feddbackdetails !== null) {
										$scope.internal1 = $scope.Feddbackdetails.requirementType;
										$scope.round1 = $scope.Feddbackdetails.nameOfRound;
										$scope.interviewstatus1 = $scope.Feddbackdetails.interviewStatus;
										$scope.clientfeedback1 = $scope.Feddbackdetails.clientFeedback;
										$scope.internalfeedback1 = $scope.Feddbackdetails.internalFeedback;
									}
								});
						//$scope.process1();
					}
					//$scope.getInterviewFeedback();

					$scope.saveFeedback = function() {
						$scope.mid = $rootScope.candidate_id;
						var id = $scope.mid;
						//						var interviewfbvalues ={
						//								candidateid : $scope.mid,
						//								candidateiname : $scope.candidatename,
						//								typeofprocess : $scope.processtype.status,
						//						}
						console.log($scope.mid);
						TypeOfProcessService.sendFeedbackMail(id);
						$scope.process1();
						$location.path("/CandidateList1");
					}

					$scope.saveoffered = function() {

						var offereddata = {
							candidateId : $scope.canid,
							rqcId : $scope.rcid,
							amId : $scope.amid,
							bdmId : $scope.bid,
							candidateName : $scope.candidatename,
							companyName : $scope.companyname,
							typeofProcess : $scope.processtype.status,
							candidateCtc : $scope.Candidatectc,
							letterName : $scope.inputParams.name,
							offeredLetter : $scope.inputParams.file

						};
						TypeOfProcessService.sendOfferedData(offereddata);
						// $scope.process1();						
						$location.path("/CandidateList1");
						// $scope.process1();						
						//$window.location.reload();

						//$scope.process1();
					}

					$scope.uid = $window.localStorage.getItem("usid");
					var loginId = $scope.uid;
					$scope.saveApproved = function() {
						//   $window.localStorage.setItem('candidateID',response.data.result.candidate.id);
						var reqId = $window.localStorage.setItem(
								'requirementID',
								response.data.result.requirement.id);
						$scope.mid = $rootScope.candidate_id;
						var id = $scope.mid;
						console.log($scope.mid);
						var approvedvalues = {
							firstName : $scope.candidateDetails.firstName,
							lastName : $scope.candidateDetails.lastName,
							candidateStatus : $scope.processtype.status,
							email : $scope.candidateDetails.email,
							user : {
								email : $scope.candidateDetails.user.email
							}
						};
						console.log(approvedvalues);
						TypeOfProcessService.sendapprovedData(approvedvalues,
								id, reqId, loginId);
						//$scope.process1();
						$location.path("/CandidateList1");
						//$scope.process1();

						//$window.location.reload();
						// $scope.process1();
					}

					$scope.saveSbmitted = function() {

						$scope.nid = $rootScope.candidate_id;
						var id = $scope.nid;
						console.log($scope.nid);
						var submittedvalues = {
							firstName : $scope.candidateDetails.firstName,
							lastName : $scope.candidateDetails.lastName,
							candidateStatus : $scope.processtype.status,
							user : {
								email : $scope.candidateDetails.user.email
							}

						};
						console.log(submittedvalues);
						TypeOfProcessService.sendsubmittedData(submittedvalues,
								id);
						swal("data submitted Successfully");
						// $scope.process1();
						$location.path("/CandidateList1");
						// $scope.process1();
						//$window.location.reload();
						//$scope.process1();

					}

					$scope.saveRelease = function() {

						$scope.lid = $rootScope.candidate_id;
						var id = $scope.lid;
						console.log($scope.lid);
						var values = {
							doj : $scope.releaseDate,
							offereLetter : $scope.filerelase.offerrelasing
						};
						$http
								.post('rest/addCandidate/offereletter/' + id,
										values)
								.then(
										function(response) {
											console.log(response);
											if (response.data.status == "StatusSuccess") {
												$scope.responseMes = "all documents upload successfully";
												$scope.ok = true;
											} else {

												$scope.responseMes = "Internal Server Error"
												$scope.ok = true;
											}

										});
						swal("data submitted Successfully");
						$location.path("/CandidateList1");
					}

					$scope.saveAmReject = function() {

						$scope.nid = $rootScope.candidate_id;
						var id = $scope.nid;
						console.log($scope.nid);
						var rejectedvalues = {
							candidateid : $scope.nid,
							candidateiname : $scope.candidateiname,
							userEmail : $scope.candidateDetails.user.email,
							typeofprocess : $scope.processtype.status,
							statusNodes : $scope.rejected

						};
						console.log(rejectedvalues);
						TypeOfProcessService.sendrejectedData(rejectedvalues,
								id);
						$scope.process1();
						$location.path("/CandidateList1");
						//  $scope.process1();
						//$window.location.reload();
						//$scope.process1();

					}

					$scope.savesubcust = function() {

						$scope.nid = $rootScope.candidate_id;
						var canId = $scope.nid;
						var reqId = $scope.reqId;
						var canUserId = $routeParams.uid;

						TypeOfProcessService.sendcustsubData(canId, reqId,
								canUserId, $scope.processtype.status);
						//	$scope.process1();
						$location.path("/CandidateList1");
						//   $scope.process1();
						//  $window.location.reload();
						//$scope.process1();

					}

					$scope.savecustreject = function() {

						$scope.nid = $rootScope.candidate_id;
						var canId = $scope.nid;
						var reqId = $scope.reqId;
						var canUserId = $routeParams.uid;
						/*var custrejectvalues = {
							firstName : $scope.candidateDetails.firstName,
							lastName : $scope.candidateDetails.lastName,
							candidateStatus : $scope.processtype.status,
							user : {
								email : $scope.candidateDetails.user.email
							}

						};*/
						/*console.log(custrejectvalues);*/
						TypeOfProcessService.sendCustrejectedData(canId, reqId,
								canUserId, $scope.processtype.status);
						//								$scope.process1();
						$location.path("/CandidateList1");
						//$scope.process1();
						//$window.location.reload();
						//$scope.process1();

					}
					$scope.saveAmPending = function() {

						$scope.nid = $rootScope.candidate_id;

						var ampendingvalues = {
							candidateid : $scope.nid,
							candidateiname : $scope.candidateiname,

							userEmail : $scope.candidateDetails.user.email,

							typeofprocess : $scope.processtype.status,

							quryQuestion : $scope.query

						};

						console.log(ampendingvalues);
						TypeOfProcessService.sendAmpendingData(ampendingvalues);
						swal("Query send  Successfully");
						$scope.process1();
						$location.path("/CandidateList1");
						//$scope.process1();
						//$window.location.reload();
						//$scope.process1();
					}

					//cancel navigation

					$scope.navigationToCancel = function() {
						$location.path("/CandidateList1");
					}

				});

app.filter('ampmtime', function() {
	return function(value) {
		if (!value) {
			return '';
		}
		var hours = new Date(value).getHours();
		var minutes = new Date(value).getMinutes();
		var ampm = hours >= 12 ? 'PM' : 'AM';
		hours = hours % 12;
		hours = hours ? hours : 12; // the hour '0' should be '12'
		minutes = minutes < 10 ? '0' + minutes : minutes;
		var strTime = hours + ':' + minutes + ' ' + ampm;
		return strTime;
	}
});

//directive for image Upload:

app.directive('fileModel', [ '$parse', function($parse) {

	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
} ]);