app.controller(
				'CandidatelistController',
				function($scope, candidateSer,
						clientService, bdmService, rpoFactory,
						candidatemaplistservices, bdmReqService,
						updateCandidatesrvService, Upload, $rootScope,
						$location, $window, $routeParams, $timeout,
						TypeOfProcessService, $rootScope, $http) {

					$scope.view = function(list) {
						var candId = list.candidateid;
						var userId = list.userId;
					}
					$scope.sortColumn = "lastUpdatedDate";
					$scope.reverseSort = true;
					$scope.sortData = function(column) {
						
						$scope.reverseSort = ($scope.sortColumn == column) ? !$scope.reverseSort
								: false;
						$scope.sortColumn = column;
					}
					$scope.getSortClass = function(column) {
						if ($scope.sortColumn == column) {
							return $scope.reverseSort ? 'arrow-down'
									: 'arrow-up'
						}
						return '';
					}
					$scope.viewReqInfo = function(list) {
						
						var reqid = list.bdmReqId;
						rpoFactory
								.reqGetByiD(reqid)
								.then(
										function(res) {
											$scope.reqDetails = res.result;
											$scope.skills = $scope.reqDetails.skills;
											$scope.qualifications = $scope.reqDetails.qualifications;
											$scope.designations = $scope.reqDetails.designations;
											$scope.locations = $scope.reqDetails.locations;
										})
						console.log($scope.reqDetails);
					}
					$scope.pageSize = '10';
					$scope.isLoading = true;
					/*
					 * $scope.isLoading=false; if($rootScope.loading==true){
					 * $scope.mystyle={'opacity':'0.5'}; } else{
					 * $scope.mystyle={'opacity':'1'}; }
					 */
					$scope.status = "Active";
					$scope.open1 = function() {
						$scope.popup1.opened = true;
					};

					$scope.popup1 = {
						opened : false
					};

					$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
							'dd.MM.yyyy', 'shortDate' ];
					$scope.format = $scope.formats[0];

					$scope.getrole = $window.localStorage.getItem("role");
					$scope.getuserid = $window.localStorage.getItem("usid");

					if ($scope.getrole == "recruiter") {
						$scope.edithide = false;
						$scope.addcandidate = false;
					}

					else if ($scope.getrole == "bdmlead") {
						$scope.edithide = true;
						$scope.addcandidate = true;
					}
					$scope.redirect = function() {
						if ($scope.getrole == "recruiter") {
							$location.path("/recruiter");
						} else if ($scope.getrole == "bdmlead") {
							$location.path("/bdmlead");
						}
					}

					$scope.difference = function(totalExperience,
							relevantExperience) {
						if (relevantExperience > totalExperience) {

							$scope.messege = "relevant experience can't be greater than total experience";
							$scope.relevantExperience = null;
						} else {
							$scope.messege = "";
						}
					}

					$scope.canReqDetails = function(list) {
						
						var reqId = list.bdmReqId;
						var canId = list.candidateid;
						var userId = list.userId;
						$scope.requiremementId =list.bdmReqId;
						$scope.userid = list.userId;
						$location.path('/canreqDetails/' + reqId + '/' + canId
								+ '/' + userId);
					}

					$scope.changeRequirement = function(list) {
						
						console.log(list);
						var reqId = list;
						$location.path("/matchData/" + reqId);
					}
					// $scope.candidatelist = CandidatelistServices.query();
					// console.log($scope.candidatelist);
					var iddd;
					$scope.funnn = function(id) {
						iddd = id;
					}

					/*var promise = candidateSer.getNoticePeriod();
					promise.then(function(data) {
						$scope.noticePeriodes = data.data.result;
						console.log($scope.noticePeriodes);
						$scope.fun1();
					});
					candidateSer.getNoticePeriod();
					var promise = candidateSer.getContactType();
					promise.then(function(data) {
						$scope.currentJobTypes = data.data.result;
						console.log($scope.currentJobTypes);
						$scope.fun1();
					});
					candidateSer.getContactType();
*/
					$scope.downloadPDF = function(id) {
						
						var dlnk = document.getElementById('dwnldLnk');
						var promise = candidateSer.getResume(id);
						promise.then(function(data) {
							console.log("Response>>>>>>>>>>>",
									data.data.result.resume);
							dlnk.href = data.data.result.resume;
							dlnk.click();
						});
					}
					// pagination
					$scope.maxSize = 2; // Limit number for pagination display
										// number.
					$scope.totalCount = 0; // Total number of items in all
											// pages. initialize as a zero
					$scope.pageIndex = 1; // Current page number. First page
											// is 1.-->
					$scope.pageSizeSelected = 40; // Maximum number of items
													// per page.

					$scope.fun1 = function() {
						
						/*
						 * $scope.isLoading=true;
						 */$scope.role = localStorage.getItem("userRole");
						$scope.loginId = localStorage.getItem("loginId");
						var promise = candidatemaplistservices
								.getCandidateData($scope.getrole,
										$scope.getuserid, $scope.pageIndex,
										$scope.pageSizeSelected, $scope.by,
										$scope.order, $scope.searchtext,
										$scope.searchcategory);
						promise.then(function(response, result) {
							$rootScope.candidatelist = response.data.result;
							$scope.isLoading = false;
							$scope.nameOfRound = response.data.result;
							$scope.totalCount = response.data.totalRecords;
							
							if(response.data.status == "DataIsEmpty"){
								$scope.noData = "No Submission Found";
							}
							//		
							// for(i =0 ; i<result.length; i++){
							//		
							// $scope.nameOfRound=data.data.result[i].nameOfRound;
							// console.log("mmmmmmmmmmmmmmmmmmmmmmmmmm",$scope.nameOfRound);
							// }
							// console.log(data.data.result($index),"ccccccccccccc");
							//		
							console.log($rootScope.candidatelist);
						});

					}
					// $scope.fun1();
					$scope.searchText = function() {
						
						$scope.isLoading = true;
						$scope.fun1();
						$scope.isLoading = false;
					}
					$scope.clearText = function() {
						$scope.isLoading = true;
						$scope.by = "", $scope.order = "",
								$scope.searchtext = "",
								$scope.searchcategory = "", $scope.fun1();
						$scope.isLoading = false;
					}
					/*
					 * $scope.searchsub=function(searchitem) {
					 * 
					 *  $scope.searchtext=searchitem.searchtext;
					 * $scope.searchcategory=searchitem.searchcategory;
					 * $scope.getrole = $window.localStorage.getItem("role");
					 * $scope.getuserid = $window.localStorage.getItem("usid");
					 * $http.get("rest/candidateReqMapping/searchCandidatesList/"+$scope.getrole+"/"+$scope.getuserid+"/"+$scope.searchtext+"/"+$scope.searchcategory+"/"+$scope.pageIndex+"/"+$scope.pageSizeSelected).success(function(response){
					 * $scope.totalCount = response.totalRecords;
					 * $scope.candidatelist=response.result;
					 * $scope.nodata=response.res; console.log("Submission
					 * list",$scope.candidatelist); });
					 * 
					 * 
					 *  }
					 */

					$scope.pageChanged = function() {
						$scope.isLoading = true;
						$scope.fun1();
						$scope.isLoading = false;
						console.log('Page changed to: ' + $scope.pageIndex);
					};
					$timeout(function() {
						$scope.fun1();
					}, 2000);
					$scope.newUserlist = {};
					/*$scope.userid = function(list) {
						$scope.newUserlist = list;
						console.log($scope.newUserlist);
						console.log($scope.newUserlist.filename);
						$scope.qualiarray = $scope.newUserlist.education;
						$scope.certiarray = $scope.newUserlist.certificates;
						$scope.skillarray = $scope.newUserlist.skills;
						console.log($scope.skillarray);
						$scope.skillarray1 = [];
						$scope.skillarray2 = [];
						angular.forEach($scope.skillarray,
								function(key, value) {
									if (key.flag == true) {
										$scope.primarySkills = key;

										$scope.skillarray1
												.push($scope.primarySkills);
									} else {
										$scope.secondarySkills = key;
										$scope.skillarray2
												.push($scope.secondarySkills);
									}
								})
						console.log($scope.skillarray1);
						console.log($scope.skillarray2);
						$scope.noticePeriod = $scope.newUserlist.noticePeriod;
						console.log($scope.noticePeriod);
						$scope.fun1();
					}*/
					
					
					

					/*$scope.updateCandidatelist = function() {
						$scope.isLoading = true;
						$scope.skillarray3 = $scope.skillarray1
								.concat($scope.skillarray2);
						updateList = {
							client : {
								id : $scope.newUserlist.client.id
							},
							bdmReq : {
								id : $scope.newUserlist.bdmReq.id
							},
							user : {
								id : $scope.newUserlist.user.id
							},
							firstName : $scope.newUserlist.firstName,
							lastName : $scope.newUserlist.lastName,
							mobile : $scope.newUserlist.mobile,
							email : $scope.newUserlist.email,
							altenateEmail : $scope.newUserlist.altenateEmail,
							alternateMobile : $scope.newUserlist.alternateMobile,
							submittionDate : $scope.newUserlist.submittionDate,
							education : $scope.educationList,
							totalExperience : $scope.newUserlist.totalExperience,
							relevantExperience : $scope.newUserlist.relevantExperience,
							currentCTC : $scope.newUserlist.currentCTC,
							expectedCTC : $scope.newUserlist.expectedCTC,
							salaryNegotiable : $scope.newUserlist.salaryNegotiable,
							noticePeriod : $scope.newUserlist.noticePeriod,
							pincode : $scope.newUserlist.pincode,
							country : $scope.newUserlist.country,
							state : $scope.newUserlist.state,
							city : $scope.newUserlist.city,
							address : $scope.newUserlist.address,
							willingtoRelocate : $scope.newUserlist.willingtoRelocate,
							skypeID : $scope.newUserlist.skypeID,
							currentJobType : $scope.newUserlist.currentJobType,
							payRollCompanyName : $scope.newUserlist.payRollCompanyName,
							currentCompanyName : $scope.newUserlist.currentCompanyName,
							pancardNumber : $scope.newUserlist.pancardNumber,
							certificationscheck : $scope.newUserlist.certificationscheck,
							gender : $scope.newUserlist.gender,
							title : $scope.newUserlist.title,
							candidateStatus : $scope.newUserlist.candidateStatus,
							candidateSource : $scope.newUserlist.candidateSource,
							skills : $scope.skills1,
							certificates : $scope.certNamelist,
							filename : $scope.newUserlist.filename,
							resume : $scope.newUserlist.resume
						};
						console.log($scope.newUserlist.id);
						updateCandidatesrvService.updateCandidateList(
								$scope.newUserlist.id, updateList);
						// $scope.fun1();
						swal("data updated Successfully");
						// $scope.fun1();
						$location.path('/CandidateList1');
						// $scope.fun1();
						$window.location.reload();
						$scope.fun1();
					}*/
					$scope.viewInfo = function(lists) {
						
						$scope.isLoading = true;
						$scope.requirementId =lists.bdmReqId
						$http
								.get("rest/addCandidate/" + lists.candidateid)
								.then(
										function(response) {
											debugger;
											$scope.candidatelist1 = response.data.result;
											$scope.isLoading = false;
										});
					}
					$scope.submitStatus = function() {
						
						if($scope.confirmStatus==undefined){
							swal("Select Status");
							return false;
						}
						else if($scope.onBoardingDate ==undefined || $scope.onBoardingDate == ""){
							swal("Enter Date");
							return false;
						}
						else{
						var onboardStatus = {
							"status" : $scope.confirmStatus,
							"onBoardingDate" : $scope.onBoardingDate,
							"candidateId" : $scope.candidatelist1.id,
							"reqId":$scope.requirementId ,
							"userId":$scope.candidatelist1.user.id
						}
						$http.post('rest/addCandidate/confirmBoardStatus',
								onboardStatus).then(function(response) {
							$scope.statusList = response.data.result;
							$scope.statusMessage = response.data.status;
							if ($scope.statusMessage = "StatusSuccess") {
								swal('Updated Successfully')
							}
							$scope.isLoading = false;
							$window.location.reload();
							$scope.fun1();
						})
					}}
					// $scope.candidatelist = lists;
					// console.log("hai");
					// console.log($scope.candidatelist);
					// $scope.process1();
					// $scope.fun1();
					$scope.updatedata = function(list) {
						$scope.isLoading = true;
						localStorage.setItem('canStatus',
								list.candidateStatus);
						localStorage.setItem('canStatus',
								list.candidateStatus);
						var rr = $window.localStorage.getItem("role");
						var process = list.candidateStatus;
						console.log(list.id);
						$scope.cid = list.candidateid;
						console.log($scope.cid);
						$window.localStorage.setItem('candidateid', $scope.cid);
						$scope.process1 = function() {
							var promise = TypeOfProcessService.getprocessData(
									rr, process);
							console.log(promise);
							promise
									.then(function(response) {
										$scope.isLoading = false;
									
										$rootScope.processDetails = response.data.result;
										
									});
						}
					/*	$timeout(function() {
							$scope.process1();
						});*/
						$scope.$watch("processDetails",
								function handleFooChange(newValue, oldValue) {
									console.log("vm.fooCount:", newValue);
									console.log("vm.fooCount:", oldValue);

								});
						// $scope.fun1();
						$location.path("/typeofProcess/" + list.candidateid
								+ "/" + list.userId+"/"+list.bdmReqId);
					}

					$scope.onHolddata = function(id) {
						var promise = TypeOfProcessService.getquesservice(id);
						console.log(promise);
						promise.then(function(response) {
							console.log(response.data.result);
							$scope.getques = response.data.result;
							console.log($scope.getques);
							$window.localStorage.setItem("cname",
									$scope.getques.candidateiname);
							$window.localStorage.setItem("email",
									$scope.getques.userEmail);
							$window.localStorage.setItem("ques",
									$scope.getques.quryQuestion);
							$window.localStorage.setItem("candiid",
									$scope.getques.candidateid);
							$window.localStorage.setItem("type",
									$scope.getques.typeofprocess);
							$window.localStorage.setItem("ans",
									$scope.getques.quryAnswer);
						});
						$location.path("/onHold");
					}
					$scope.details = function() {
						$scope.candiname = $window.localStorage
								.getItem("cname");
						$scope.candiemail = $window.localStorage
								.getItem("email");
						$scope.candiques = $window.localStorage.getItem("ques");
						$scope.candidateid = $window.localStorage
								.getItem("candiid");
						$scope.candidatetype = $window.localStorage
								.getItem("type");
						$scope.queryAnswer = $window.localStorage
								.getItem("ans");

						if ($scope.queryAnswer = $window.localStorage
								.getItem("ans") == "null") {
							$scope.queryAnswer = "";
						} else {
							$scope.queryAnswer = $window.localStorage
									.getItem("ans");
						}
					}
					$scope.onHolddata1 = function(id) {
						var promise = TypeOfProcessService.getquesservice(id);
						console.log(promise);
						promise.then(function(response) {
							console.log(response.data.result);
							$scope.getques = response.data.result;
							console.log($scope.getques);
							$window.localStorage.setItem("cname1",
									$scope.getques.candidateiname);
							$window.localStorage.setItem("email1",
									$scope.getques.userEmail);
							$window.localStorage.setItem("ques1",
									$scope.getques.quryQuestion);
							$window.localStorage.setItem("candiid1",
									$scope.getques.candidateid);
							$window.localStorage.setItem("type1",
									$scope.getques.typeofprocess);
							$window.localStorage.setItem("ans1",
									$scope.getques.quryAnswer);
						});
						$location.path("/onHold1");
					}
					$scope.g = $window.localStorage.getItem("ans1");
					$scope.a = $window.localStorage.getItem("cname1");
					$scope.b = $window.localStorage.getItem("email1");
					$scope.c = $window.localStorage.getItem("ques1");
					$scope.d = $window.localStorage.getItem("candiid1");
					$scope.f = $window.localStorage.getItem("type1");
					console.log($scope.queryAnswer);
					$scope.saveOnHold = function() {
						console.log($scope.getques);
						var amholddata = {
							candidateid : $scope.candidateid,
							candidateiname : $scope.candiname,
							userEmail : $scope.candiemail,
							quryQuestion : $scope.candiques,
							typeofprocess : $scope.candidatetype,
							quryAnswer : $scope.queryAnswer
						};
						console.log(amholddata);
						TypeOfProcessService.sendAmholdData(amholddata,
								$scope.candidateid);
					}
					$scope.flags = false;
					$scope.expandSelected = function(details) {
						$scope.candidatelist.forEach(function(val) {
							val.expanded = false;
						})
						details.expanded = true;
						$http
								.get(
										'rest/addCandidate/getRequiremntListByCandidateId/'
												+ details.candidateid)
								.then(
										function(response) {
											$scope.statusress = response.data.status;
											if ($scope.statusress == 'StatusSuccess') {
												$scope.flags = true;
												$scope.ListReqDataa = response.data.result;
											} else {
												$scope.ListReqDataa = [];
												$scope.flags = false;
											}
										});
					}
					$scope.navigationToCandidate = function(list) {
						console.log(list);
						var reqId = list.id;
						$location.path("/matchCandidate/" + reqId);
					}
				});
