app
		.controller(
				'reqEditCtrl',
				function($scope, $http, $timeout, $location, $routeParams,
						reqEditSer, bdmService, listSkillService,
						designationService, listqualificationservice,
						listNoticePeriodService, addModeService,
						getHiringModeService, $rootScope, $location, $window) {
					$scope.yes = true;
					$scope.no = false;
					$scope.open1 = function() {
						$scope.popup1.opened = true;
						// date picker
						$scope.yes = false;
						$scope.no = true;
					};
					$scope.open2 = function() {
						$scope.popup2.opened = true;
						// date picker
						$scope.yes = false;
						$scope.no = true;
					};

					$scope.enddateflag = true;
					$scope.endDate = function() {
						$scope.enddateflag = false;
					};

					$scope.popup1 = {
						opened : false
					};
					$scope.popup2 = {
						opened : false
					};

					$scope.formats = [ 'dd MMMM yyyy', 'yyyy/MM/dd',
							'dd.MM.yyyy', 'shortDate' ];
					$scope.format = $scope.formats[1];

					// experience validations
					$scope.difference = function(maxExperience, minExperience) {
						if (minExperience > maxExperience) {
							$scope.messege = "min experience can't be greater than max experience";
							$scope.maxExperience = null;
						} else {
							$scope.messege = "";
						}
					}
					$scope.showType = true;

					$scope.myFun = function() {

						$scope.showType = false;
					}

					$scope.showType5 = true;
					$scope.myFun5 = function() {

						$scope.showType5 = false;
					}
					$scope.showType15 = true;
					$scope.fun15 = function() {
						$scope.showType15 = false;
					}

					$scope.difference1 = function(maxExperience,
							relavantExperience) {
						if (relavantExperience > maxExperience) {
							$scope.messege = "relevant experience can't be greater than total experience";
							$scope.relavantExperience = null;
						} else {
							$scope.messege = "";
						}
					}

					$http.get("rest/location").then(function(response) {
						$scope.locationList = response.data;
					})
					/*var relavantExperience
					var minrelavantExperience = relavantExperience.spli(5,7);
					var maxrelavantExperience= relavantExperience.slice(0,4);*/
					/*var minrelavantExperience = relavantExperience.split("-");
					swal(minrelavantExperience);
					console.log(year);
					*/
					$scope.updateRequirement = function() {

						if ($scope.clientName == undefined) {
							swal("Please Select  Customer Name");
							return false;
						} else if ($scope.addContact1 == undefined) {
							swal("Please Select  Customer Spoc Name ");
							return false;
						} else if ($scope.nameOfRequirement == undefined
								|| $scope.nameOfRequirement == '') {
							swal("Please Enter   Name Of Requirement");
							return false;
						} else if ($scope.qualiarray.length == 0) {
							swal("Please Select  Qualifications  ");
							return false;
						} else if ($scope.skillarray.length == 0) {
							swal("Please Select  Skill  ");
							return false;
						} else if ($scope.minrelavantExperience == undefined) {
							swal("Please Select Min Relavant Experience");
							return false;
						} 
						else if ($scope.maxrelavantExperience == undefined) {
							swal("Please Select Max Relavant Experience");
							return false;
						} else if ($scope.numberOfPositions == undefined
								|| $scope.numberOfPositions == '') {
							swal("Please Enter  Number Of Positions");
							return false;
						}
						else if ($scope.typeOfHiring == undefined) {
							swal("Please select Type Of hiring");
							return false;
						} else if ($scope.typeOfHiring != undefined) {
							if ($scope.typeOfHiring == 'permanent') {
								if ($scope.amountType == undefined
										|| $scope.amountType == '') {
									swal("Please Enter Amount ");
									return false;
								}
							} else if ($scope.typeOfHiring == 'Contract to Hire') {
								if ($scope.minBudgetRate == undefined
										|| $scope.minBudgetRate == '') {
									swal("Please Enter  Min Budget ");
									return false;
								}
								if ($scope.maxBudgetRate == undefined
										|| $scope.maxBudgetRate == '') {
									swal("Please Enter  Max Budget  ");
									return false;
								}
							} else if ($scope.typeOfHiring == 'contract') {
								if ($scope.minBudget == undefined
										|| $scope.minBudget == '') {
									swal("Please Enter  Min Budget  ");
									return false;
								}
								if ($scope.maxBudget == undefined
										|| $scope.maxBudget == '') {
									swal("Please Enter  Max Budget  ");
									return false;
								}
							}
						}
						if ($scope.status == undefined) {
							swal("Please Select Status");
							return false;
						} else if ($scope.noticePeriod == undefined
								|| $scope.noticePeriod == '') {
							swal("Please Enter  Notice Period ");
							return false;
						} else if ($scope.locationarray.length == 0) {
							swal("Please Select Job Locations");
							return false;
						}
						else if ($scope.requirementDescription == undefined
								|| $scope.requirementDescription == '') {
							swal("Please Enter Requirement Description");
							return false;
						}
						else if ($scope.jobDes == undefined
								|| $scope.jobDes == '') {
							swal("Please Enter  Job Description");
							return false;
						}
						// $scope.conctactid= JSON.parse($scope.contact_Name1)
						$scope.value = {
							requirementStartdate : $scope.requirementStartdate,
							requirementEndDate : $scope.requirementEndDate,
							nameOfRequirement : $scope.nameOfRequirement,
							designations : $scope.desigarray,
							skills : $scope.skillarray,
							qualifications : $scope.qualiarray,
							certificates : $scope.certiarray,
							requirementType : $scope.requirementType,
							relavantExperience : $scope.minrelavantExperience+"-"+$scope.maxrelavantExperience,
							numberOfPositions : $scope.numberOfPositions,
							typeOfHiring : $scope.typeOfHiring,
							minBudgetRate : $scope.minBudgetRate,
							maxBudgetRate : $scope.maxBudgetRate,
							minBudget : $scope.minBudget,
							maxBudget : $scope.maxBudget,
							newtype : $scope.newtype,
							famount : $scope.famount,
							amountType : $scope.amountType,
							status : $scope.status,
							locations : $scope.locationarray,
							noticePeriod : $scope.noticePeriod,
							requirementDescription : $scope.requirementDescription,
							jobDes : $scope.jobDes,
							client : {
								id : $scope.clientName
							},
							addContact : {
								id : $scope.contactId,
							},
							id : parseInt($scope.id12)
						}
						$http
								.post("rest/Bdmrequire/", $scope.value)
								.then(
										function(response) {
											if (response.data.status == "StatusSuccess") {
												swal("Updated Successfully");
												$location.path("/bdmlead");
											} else {
												swal("Internal Server Error")
											}
										});
					}
					// start date and end date validation
					$scope.checkErr = function(requirementStartdate,
							requirementEndDate) {
						$scope.errMessage = '';
						// var curDate = new Date();
						if (new Date(requirementEndDate) < new Date(
								requirementStartdate)) {
							$scope.errMessage = 'End Date should be greater than start date';
							$scope.requirementEndDate = null;
						}
					};
					// end validation

					/*
					 * $scope.hiringdropfun = function(){
					 * $scope.typeOfHiring=$scope.typeOfHiring;
					 * if($scope.typeOfHiring=="Contract to Hire"){
					 * $scope.ContracttoHire=true; $scope.permanent=false;
					 * $scope.contarct=false;
					 *  } else if($scope.typeOfHiring=="permanent"){
					 * $scope.ContracttoHire=false; $scope.permanent=true;
					 * $scope.contarct=false; $scope.permanent="slab";
					 *  } else if($scope.typeOfHiring=="contract"){
					 * $scope.ContracttoHire=false; $scope.permanent=false;
					 * $scope.contarct=true;
					 * 
					 *  } }
					 */
					$scope.newType = [ 'Per Annum', 'Per Month' ]

					$scope.id12 = $routeParams.id;
					var ss;

					var editId = $routeParams.id;
					console.log(editId);

					/* $scope.clientName; */

					var promise = reqEditSer.getEditableData($scope.id12);

					promise
							.then(function(data) {
								$scope.ListReqData = data;
								ss = $scope.ListReqData.data.result;

								var temparr1 = ss.skills;
								var temparr2 = ss.designations;
								var temparr3 = ss.qualifications;
								var temparr4 = ss.certificates;
								var temparr5 = ss.interviewMode;
								var temparr6 = ss.locations;

								$scope.requirementStartdate = ss.requirementStartdate;
								$scope.requirementEndDate = ss.requirementEndDate;
								$scope.contactId = ss.addContact.id;
								$scope.maxBudget = ss.maxBudget;
								$scope.minBudget = ss.minBudget;
								$scope.newtype = ss.newtype;
								/* $scope.clientName=ss.client.id; */
								$scope.clientName = ss.client.id;
								var promise = bdmService
										.clientcontactm($scope.clientName);
								promise.then(function(response) {
									$scope.chiru = response.data.result;
								})

								$scope.addContact1 = ss.addContact.id
								$scope.status = ss.status;
								$scope.jobDes = ss.jobDes;
								$scope.amountType = ss.amountType;
								$scope.locationName = ss.locations.id;
								/* $scope.endDate=ss.endDate */
								$scope.contact_Name1 = ss.addContact.contact_Name;
								$scope.contact_Name = ss.addContact.id;
								console.log(ss.client.id);
								$window.localStorage.setItem("idd",
										ss.client.id);
								$scope.email = ss.addContact.email;
								$scope.mobile = ss.addContact.mobile;
								$scope.nameOfRequirement = ss.nameOfRequirement;
								$scope.slab = ss.slab;
								$scope.samount = ss.samount;
								$scope.famount = ss.famount;
								/* $scope.startDate=ss.startDate; */
								$scope.budgetrate = ss.budgetrate;
								/* $scope.endDate=ss.endDate; */
								$scope.minBudgetRate = ss.minBudgetRate;
								$scope.maxBudgetRate = ss.maxBudgetRate
								console.log($scope.endDate);
								$scope.requirementType = ss.requirementType;
								$scope.skillarray = temparr1;
								$scope.desigarray = temparr2;
								$scope.qualiarray = temparr3;
								$scope.interviewmodearray = temparr5;
								$scope.certiarray = temparr4;
								$scope.locationarray = temparr6;
								$scope.noticePeriod = ss.noticePeriod;
								$scope.typeOfHiring = ss.typeOfHiring;
								$scope.requirementDescription = ss.requirementDescription;
								$scope.totalExperience = ss.totalExperience;
								var testresult = ss.relavantExperience;
									var res = testresult.split("-");
								$scope.minrelavantExperience =  res.slice(0,1);
								$scope.maxrelavantExperience =  res.slice(1,3);	
								/*$scope.minrelavantExperience = ss.relavantExperience;*/
								/*$scope.maxrelavantExperience = ss.relavantExperience;*/
								$scope.numberOfPositions = ss.numberOfPositions;
								$scope.numberOfRounds = ss.numberOfRounds;
								$scope.netPeriod = ss.netPeriod;
								$scope.gross_Amount = ss.gross_Amount;
							});

					var fun1 = function() {
						var promise = reqEditSer.getEditableData($scope.id12);

						promise
								.then(function(data) {
									$scope.ListReqData = data;
									ss = $scope.ListReqData.data.result;
									console.log(ss);

									var temparr1 = ss.skills;

									var temparr2 = ss.designations;

									var temparr3 = ss.qualifications;

									var temparr4 = ss.certificates;

									var temparr5 = ss.interviewMode;
									var temparr5 = ss.locations;

									$scope.requirementStartdate = ss.requirementStartdate;
									$scope.requirementEndDate = ss.requirementEndDate;

									$scope.clientName = ss.client.id;
									$scope.clientId = ss.client.id;

									$scope.contact_Name1 = ss.addContact.contact_Name;
									$scope.contact_Name = ss.addContact.id;
									console.log(ss.client.id);
									$window.localStorage.setItem("idd",
											ss.client.id);

									$scope.email = ss.addContact.email;
									$scope.mobile = ss.addContact.mobile;
									$scope.nameOfRequirement = ss.nameOfRequirement;

									if (ss.billRate == null) {
										$scope.billRate = "IncludeTax";
									} else {
										$scope.billRate = ss.billRate;
									}
									$scope.conversionFee = ss.conversionFee;
									$scope.minimumContract = ss.minimumContract;
									if (ss.permanent == null) {
										$scope.permanent = "fixed";
									} else {
										$scope.permanent = ss.permanent;
									}
									$scope.tax = ss.tax;
									$scope.slab = ss.slab;
									$scope.samount = ss.samount;
									$scope.famount = ss.samount;

									$scope.requirementType = ss.requirementType;
									$scope.skillarray = temparr1;

									$scope.desigarray = temparr2;
									$scope.qualiarray = temparr3;
									$scope.interviewmodearray = temparr5;
									$scope.certiarray = temparr4;

									$scope.jobLocation = ss.jobLocation;
									$scope.noticePeriod = ss.noticePeriod;
									$scope.typeOfHiring = ss.typeOfHiring;

									if (ss.newtype == null) {
										$scope.newtype = "Retrospective";
									} else {
										$scope.newtype = ss.newtype;
									}

									$scope.salaryBand = "pm";
									$scope.salaryBand = ss.salaryBand;

									$scope.requirementDescription = ss.requirementDescription;
									$scope.totalExperience = ss.totalExperience;
									$scope.relavantExperience = ss.relavantExperience;
									$scope.numberOfPositions = ss.numberOfPositions;
									$scope.numberOfRounds = ss.numberOfRounds;

									$scope.netPeriod = ss.netPeriod;
									$scope.gross_Amount = ss.gross_Amount;

								});
					}

					// $timeout(function () {
					// fun1();
					// }, 3000);

					// $scope.save0button =true;
					$scope.desigarray = [];
					$scope.skillarray = [];
					$scope.certiarray = [];
					$scope.qualiarray = [];
					$scope.locationarray = [];
					$scope.interviewmodearray = [];
					$scope.example16settings = {
						styleActive : true,
						enableSearch : true,
						showSelectAll : true,
						keyboardControls : true,
						showEnableSearchButton : true,
						scrollableHeight : '300px',
						scrollable : true
					};

					// $scope.example1model = {selectionLimit: 5}

					var promiseq = listNoticePeriodService.getnoticeperiod();
					promiseq.then(function(response) {

						$scope.noticePeriods = response.data.result;
						console.log($scope.noticePeriods);
					});

					$scope.getRequirement = function(id) {

						var promise = bdmService.clientcontactm(id);

						promise.then(function(response) {
							$scope.chiru = response.data.result;
							console.log($scope.chiru);
						});

					}
					$scope.locationName;
					$scope.getLocationId = function(x) {
						$scope.locationName = x;
					}

					$scope.getRequirementContact = function(addContact1) {
						console.log(addContact1);

						$scope.val = addContact1;
						console.log($scope.val);
						// var jhansi = JSON.parse($scope.val);
						// console.log(jhansi);
						$scope.contactPerson = $scope.val[0].contact_Name;
						console.log($scope.contactPerson);
						$scope.email = $scope.val[0].email;
						$scope.mobile = $scope.val[0].mobile;
						$scope.contact = $scope.val[0].id;

					}
					$scope.show10 = true;
					$scope.fun10 = function() {
						$scope.show10 = false;
					}

					$scope.showSlab = $scope.permanent;

					$scope.slab = [ {

					} ];
					$scope.show1 = true;
					$scope.show2 = false;
					$scope.showFun1 = function() {
						$scope.show1 = true;
						$scope.show2 = false;
						console.log("show1" + $scope.show1);
						console.log("show2" + $scope.show2);
					}
					$scope.showFun2 = function() {
						$scope.show1 = false;
						$scope.show2 = true;
						console.log("show1" + $scope.show1);
						console.log("show2" + $scope.show1);
					}
					var promise = getHiringModeService.getModeDetails();

					promise.then(function(response) {
						$scope.hiremode = response.data.result;
						console.log($scope.hiremode);
					});

					$scope.getskillsfun = function() {
						var promise1 = listSkillService.getskill();
						promise1.then(function(data) {
							$scope.skillbdm = data.data.result;

						});
					}
					$scope.getskillsfun();

					var promise1 = addModeService.getModeData();
					promise1.then(function(data) {
						$scope.interviewbdm = data.data.result;

					});

					$scope.addNew = function(slab) {
						$scope.slab.push({});
					};
					$scope.removeRaw = function(slabIndex) {
						console.log(slabIndex);
						$scope.slab.splice(slabIndex, 1);
					};
					var promise = designationService.getDesignationList();
					promise.then(function(data) {
						$scope.desigbdm = data.data.result;
					});
					
					$scope.getquali = function() {
						var promise = listqualificationservice
								.getqualification();
						promise.then(function(data) {
							$scope.qualificbdm = data.data.result;
						});
					}
					$scope.getquali();
					
					var promise = bdmService.getCertificateNames();
					promise.then(function(data) {
						$scope.certificatebdm = data.data.result;
						console.log($scope.certificatebdm);

					});

					var promise = bdmService.getClientNames555();
					promise.then(function(data) {
						$scope.clientnamesbdm = data.data.result;
					});

					$scope.ok = false;
					$scope.redirect = function() {
						$location.path("/viewBdmreqdtls");
						$window.location.reload();
					}

					// ////////////////////////////////////////////

					$scope.bdmreqdtls1 = function(reqdtls) {
						$scope.responseMes = "Proceesing...";
						$scope.bdmSkill = $scope.skill;

						var temparrccmapping = [];
						angular.forEach($scope.contact, function(id, value) {
							console.log($scope.contact)
							temparrccmapping.push({
								"id" : id
							});
						});

						var temparrslab = [];
						angular.forEach($scope.slab, function(id, value) {
							temparrslab.push({
								"id" : id
							});
						});

						var temparr = [];
						angular.forEach($scope.bdmSkill, function(id, value) {

							temparr.push({
								"id" : id
							});
						});

						// $scope.totalExperience =
						// $scope.maxExperience+"-"+$scope.maxExperience;
						$scope.totalExperience = $scope.totalExperience;
						$scope.requirementType = "Niche";

						$scope.id = $window.localStorage.getItem("usid");
						console.log("user_id" + $scope.id);

						/*
						 * var collectionDate = $scope.endDate; $scope.date =
						 * new Date(collectionDate);
						 */

						var jobstring = $scope.jobLocation;
						$scope.jobLocation = jobstring.toString();

						$scope.reqdtls = {

							client : {
								id : $scope.clientName
							},
							addContact : {
								id : $scope.contact_Name
							},
							user : {
								id : $scope.id
							},
							requirementStartdate : $scope.requirementStartdate,
							requirementEndDate : $scope.requirementEndDate,
							nameOfRequirement : $scope.nameOfRequirement,
							minimumContract : $scope.minimumContract,
							requirementType : $scope.requirementType,
							skills : $scope.skillarray,
							certificates : $scope.certiarray,
							designations : $scope.desigarray,
							qualifications : $scope.qualiarray,
							interviewMode : $scope.interviewmodearray,
							locations : $scope.locationarray,
							noticePeriod : $scope.noticePeriod,
							typeOfHiring : $scope.typeOfHiring,
							salaryBand : $scope.salaryBand,
							conversionFee : $scope.conversionFee,
							requirementDescription : $scope.requirementDescription,
							totalExperience : $scope.totalExperience,
							relavantExperience : $scope.relavantExperience,
							numberOfPositions : $scope.numberOfPositions,
							numberOfRounds : $scope.numberOfRounds,
							gross_Amount : $scope.gross_Amount,
							netPeriod : $scope.netPeriod,
							budgetrate : $scope.budgetrate

						}

						console.log(JSON.stringify($scope.reqdtls));
						console.log();
						if ($scope.minimumContract == undefined
								|| $scope.conversionFee == undefined) {
							$scope.message = "this is mandatory field";
							swal($scope.message);
							return;

						} else {

							if ($scope.reqdtls == undefined) {

								$scope.mes1 = "Please Fill All Details";
								swal($scope.mes1);
								return;

							}

							$http
									.post("rest/Bdmrequire/" + editId,
											$scope.reqdtls)
									.then(
											function(response) {
												console.log(response);
												if (response.data.status == "StatusSuccess") {
													$scope.responseMes = "Requirement Updated Successfully";

													$scope.ok = true;
												} else {
													$scope.responseMes = "Internal Server Error"
													$scope.ok = true;
												}

											});

						}

					}

					$scope.bdmreqdtls21 = function(reqdtls) {
						$scope.responseMes = "Proceesing...";
						$scope.bdmSkill = $scope.skill;

						var jobstring = $scope.jobLocation;
						$scope.jobLocation = jobstring.toString();

						var temparrccmapping = [];
						angular.forEach($scope.contact, function(id, value) {
							console.log($scope.contact)
							temparrccmapping.push({
								"id" : id
							});
						});

						var temparrslab = [];
						angular.forEach($scope.slab, function(id, value) {
							temparrslab.push({
								"id" : id
							});
						});

						// $scope.totalExperience =
						// $scope.maxExperience+"-"+$scope.maxExperience;

						$scope.requirementType = "Niche";

						$scope.id = $window.localStorage.getItem("usid");
						console.log("user_id" + $scope.id);
						/*
						 * var collectionDate = $scope.requirementEndDate;
						 * $scope.date = new Date(collectionDate);
						 */
						$scope.reqdtls = {

							client : {
								id : $scope.clientName
							},
							addContact : {
								id : $scope.contact_Name
							},
							user : {
								id : $scope.id
							},
							requirementStartdate : $scope.requirementStartdate,
							requirementEndDate : $scope.requirementEndDate,
							newtype : $scope.newtype,
							nameOfRequirement : $scope.nameOfRequirement,
							permanent : $scope.permanent,
							// slab:$scope.slab,
							samount : $scope.samount,
							famount : $scope.famount,
							requirementType : $scope.requirementType,
							skills : $scope.skillarray,
							certificates : $scope.certiarray,
							designations : $scope.desigarray,
							qualifications : $scope.qualiarray,
							interviewMode : $scope.interviewmodearray,
							locations : $scope.locationarray,
							noticePeriod : $scope.noticePeriod,
							typeOfHiring : $scope.typeOfHiring,
							requirementDescription : $scope.requirementDescription,
							totalExperience : $scope.totalExperience,
							relavantExperience : $scope.relavantExperience,

							numberOfPositions : $scope.numberOfPositions,
							numberOfRounds : $scope.numberOfRounds,
							gross_Amount : $scope.gross_Amount,
							netPeriod : $scope.netPeriod,
							budgetrate : $scope.budgetrate

						}

						console.log($scope.reqdtls);

						if ($scope.famount == undefined
								&& ($scope.slab[0].amount == undefined
										|| $scope.slab[0].startEnd == undefined || $scope.slab[0].startFrom == undefined)) {
							$scope.message = "this is mandatory field";
							$scope.message1 = "All fields are mandatory in the table";
							swal($scope.message1);

							return;
						}

						else {

							if ($scope.reqdtls == undefined) {

								$scope.mes1 = "Please Fill All Details";
								swal($scope.mes1);
								return;

							}

							$http
									.post("rest/Bdmrequire/" + editId,
											$scope.reqdtls)
									.then(
											function(response) {
												console.log(response);
												if (response.data.status == "StatusSuccess") {
													$scope.responseMes = "Requirement Updated Successfully";
													$scope.ok = true;
												} else {
													$scope.responseMes = "Internal Server Error"
													$scope.ok = true;
												}

											});

						}

					}

					// ///////////////////////////////

					$scope.bdmreqdtls22 = function(reqdtls) {
						$scope.responseMes = "Proceesing...";
						$scope.bdmSkill = $scope.skill;

						var jobstring = $scope.jobLocation;
						$scope.jobLocation = jobstring.toString();

						var temparrccmapping = [];
						angular.forEach($scope.contact, function(id, value) {
							console.log($scope.contact)
							temparrccmapping.push({
								"id" : id
							});
						});

						var temparrslab = [];
						angular.forEach($scope.slab, function(id, value) {
							temparrslab.push({
								"id" : id
							});
						});

						// $scope.totalExperience =
						// $scope.maxExperience+"-"+$scope.maxExperience;

						$scope.requirementType = "Niche";

						$scope.id = $window.localStorage.getItem("usid");
						console.log("user_id" + $scope.id);
						// var collectionDate = $scope.requirementEndDate;
						// $scope.date = new Date(collectionDate);
						$scope.reqdtls = {

							client : {
								id : $scope.clientName
							},
							addContact : {
								id : $scope.contact_Name
							},
							user : {
								id : $scope.id
							},
							requirementStartdate : $scope.requirementStartdate,
							requirementEndDate : $scope.requirementEndDate,
							newtype : $scope.newtype,
							nameOfRequirement : $scope.nameOfRequirement,
							permanent : $scope.permanent,
							slab : $scope.slab,
							samount : $scope.samount,
							// famount:$scope.famount,
							requirementType : $scope.requirementType,
							skills : $scope.skillarray,
							certificates : $scope.certiarray,
							designations : $scope.desigarray,
							qualifications : $scope.qualiarray,
							interviewMode : $scope.interviewmodearray,
							locations : $scope.locationarray,
							noticePeriod : $scope.noticePeriod,
							typeOfHiring : $scope.typeOfHiring,
							requirementDescription : $scope.requirementDescription,
							totalExperience : $scope.totalExperience,
							relavantExperience : $scope.relavantExperience,

							numberOfPositions : $scope.numberOfPositions,
							numberOfRounds : $scope.numberOfRounds,
							gross_Amount : $scope.gross_Amount,
							netPeriod : $scope.netPeriod,
							budgetrate : $scope.budgetrate

						}

						console.log($scope.reqdtls);

						if ($scope.famount == undefined
								&& ($scope.slab[0].amount == undefined
										|| $scope.slab[0].startEnd == undefined || $scope.slab[0].startFrom == undefined)) {
							$scope.message = "this is mandatory field";
							$scope.message1 = "All fields are mandatory in the table";
							swal($scope.message1);
							return;
						}

						else {

							if ($scope.reqdtls == undefined) {

								$scope.mes1 = "Please Fill All Details";
								swal($scope.mes1);
								return;

							}

							$http
									.post("rest/Bdmrequire/" + editId,
											$scope.reqdtls)
									.then(
											function(response) {
												console.log(response);
												if (response.data.status == "StatusSuccess") {
													$scope.responseMes = "Requirement Updated Successfully";
													$scope.ok = true;
												} else {
													$scope.responseMes = "Internal Server Error"
													$scope.ok = true;
												}

											});

							// reqEditSer.addBdmdtls($scope.reqdtls,editId);
							// swal("Requirement Saved Successfully");
							// $location.path("/viewBdmreqdtls");
						}

					}

					// //////////////////////////////////////

					$scope.bdmreqdtls3 = function(reqdtls) {
						$scope.responseMes = "Proceesing...";
						$scope.bdmSkill = $scope.skill;
						// $scope.totalExperience =
						// $scope.maxExperience+"-"+$scope.maxExperience;
						$scope.totalExperience = $scope.totalExperience;
						var jobstring = $scope.jobLocation;
						$scope.jobLocation = jobstring.toString();

						var temparrccmapping = [];
						angular.forEach($scope.contact, function(id, value) {
							console.log($scope.contact)
							temparrccmapping.push({
								"id" : id
							});
						});

						var temparrslab = [];
						angular.forEach($scope.slab, function(id, value) {
							temparrslab.push({
								"id" : id
							});
						});

						$scope.id = $window.localStorage.getItem("usid");
						console.log("user_id" + $scope.id);
						/*
						 * var collectionDate = $scope.requirementEndDate;
						 * $scope.date = new Date(collectionDate);
						 */
						$scope.reqdtls = {

							client : {
								id : $scope.clientName
							},
							addContact : {
								id : $scope.contact_Name
							},
							user : {
								id : $scope.id
							},
							requirementStartdate : $scope.requirementStartdate,
							requirementEndDate : $scope.requirementEndDate,
							nameOfRequirement : $scope.nameOfRequirement,
							billRate : $scope.billRate,
							tax : $scope.tax,
							tax_amount_type : $scope.taxCostType,
							requirementType : $scope.requirementType,
							skills : $scope.skillarray,
							certificates : $scope.certiarray,
							designations : $scope.desigarray,
							qualifications : $scope.qualiarray,
							interviewMode : $scope.interviewmodearray,
							locations : $scope.locationarray,
							noticePeriod : $scope.noticePeriod,
							typeOfHiring : $scope.typeOfHiring,
							requirementDescription : $scope.requirementDescription,
							totalExperience : $scope.totalExperience,
							relavantExperience : $scope.relavantExperience,
							numberOfPositions : $scope.numberOfPositions,
							numberOfRounds : $scope.numberOfRounds,
							gross_Amount : $scope.gross_Amount,
							netPeriod : $scope.netPeriod,
							budgetrate : $scope.budgetrate

						}
						console.log($scope.tax == undefined);
						if ($scope.tax == undefined) {
							$scope.message = "this is mandatory field";
							swal($scope.message);
							return;
						}

						else {

							if ($scope.reqdtls == undefined) {

								$scope.mes1 = "Please Fill All Details";
								swal($scope.mes1);
								return;

							}

							$http
									.post("rest/Bdmrequire/" + editId,
											$scope.reqdtls)
									.then(
											function(response) {
												console.log(response);
												if (response.data.status == "StatusSuccess") {
													$scope.responseMes = "Requirement Updated Successfully";
													$scope.ok = true;
												} else {
													$scope.responseMes = "Internal Server Error"
													$scope.ok = true;
												}

											});

						}

					}

				});
app.filter('unique', function() {
	return function(collection, keyname) {
		var output = [], keys = [];
		angular.forEach(collection, function(item) {
			var key = item[keyname];
			if (keys.indexOf(key) === -1) {
				keys.push(key);
				output.push(item);
			}
		});
		return output;
	};
});
