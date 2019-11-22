app
		.controller(
				'ClientController',
				function($scope, clientService, $rootScope, $location, $http,
						$window, rpoFactory, $routeParams) {
					/*
					 * $scope.$on('$viewContentLoaded', function () {
					 *  }
					 */
					$scope.serviceLSs = [];
					$scope.settings = {
						smartButtonMaxItems : 2
					};

					$scope.userRole = localStorage.getItem('role');
					$scope.userId = localStorage.getItem('usid');
					$scope.example16settings = {
						styleActive : true,
						enableSearch : true,
						showSelectAll : true,
						keyboardControls : true,
						showEnableSearchButton : true,
						scrollableHeight : '300px',
						displayProp : 'serviceName',
						scrollable : true,
						externalIdProp : ''
					};
					$scope.yes = false;
					$scope.sameaddress = function() {
						if ($scope.yes == true) {
							$('#displayblock').css('display', 'none');
						} else if ($scope.yes == false) {
							$('#displayblock').css('display', 'block');
						}
					}

					$scope.open1 = function() {

						$scope.popup1.opened = true;
					};
					$scope.open2 = function() {

						$scope.popup2.opened = true;
					};
					$scope.popup1 = {
						opened : false
					};
					$scope.popup2 = {
						opened : false
					};

					$scope.ok = false;

					$scope.isShow = false;
					$scope.isUpdate = false;
					$scope.isSent = true;
					$scope.addCustomer = function() {

						/*
						 * if ($scope.userRole == "BDM") { primaryid =
						 * $scope.userId } else{ primaryid =
						 * $scope.primaryContact
						 *  } if ($scope.userRole == "AM") { accId =
						 * $scope.userId } else{ accId = $scope.accountManger
						 *  }
						 */

						if ($scope.clientName == undefined
								|| $scope.clientName == '') {
							swal("Please Enter Customer Name");
							return false;
						} else if ($scope.clientType == undefined) {
							swal("Please Select Customer Type ");
							return false;

						} else if ($scope.customerShortName == undefined
								|| $scope.customerShortName == '') {
							swal("Please Enter Customer Short Name");
							return false;
						} else if ($scope.tdsPercentage == undefined
								|| $scope.tdsPercentage == '') {
							swal("Please Enter TDS Percentage");
							return false;
						} else if ($scope.email == undefined
								|| $scope.email == '') {
							swal("Please Enter Email  ");
							return false;
						} else if ($scope.phone == undefined
								|| $scope.phone == '') {
							swal("Please Enter Phone Number");
							return false;
						} else if ($scope.spocName == undefined
								|| $scope.spocName == '') {
							swal("Please Enter Spoc Name");
							return false;
						} else if ($scope.billingModel == undefined) {
							swal("Please Select Billing Model");
							return false;
						} else if ($scope.startDate == undefined) {
							swal("Please Enter Empanelment Date");
							return false;
						} else if ($scope.paymentTerms == undefined) {
							swal("Please Select Payment Terms");
							return false;
						}
						/*
						 * else if(primaryid==undefined ||primaryid==null){
						 * swal("Please Select Primary BDM"); return false; }
						 * else if(accId==undefined ||accId==null){
						 * swal("Please Select Account Manager "); return
						 * false; } else if($scope.secondaryContact==undefined
						 * ||$scope.secondaryContact==null){ swal("Please
						 * Select Secondary BDM "); return false; } else if
						 * ($scope.leadName==undefined ) { swal("Please Select
						 * Lead"); return false; }
						 */
						$scope.isLoading = true;

						var reqObj = {
							"clientName" : $scope.clientName,
							"customerType" : {
								'id' : $scope.clientType,
							},
							"customershortname" : $scope.customerShortName,
							"tdspercentage" : $scope.tdsPercentage,
							"email" : $scope.email,
							"spocName" : $scope.spocName,
							"phone" : $scope.phone,
							"billingModel" : {
								'id' : $scope.billingModel
							},
							"startDate" : $scope.startDate,
							"endDate" : $scope.endDate,
							"services" : $scope.serviceLSs,
							"leavesAllowed" : $scope.leavesAllowed,
							"paymentTerms" : {
								'id' : $scope.paymentTerms
							}
						/*
						 * "primaryContact":{ 'id': primaryid },
						 * "secondaryContact":{ 'id':$scope.secondaryContact },
						 * "accountManger":{ 'id':accId }, "lead":{
						 * 'id':$scope.leadName }
						 */
						}
						rpoFactory
								.customerDetails(reqObj)
								.then(
										function(response) {
											$scope.customerObj = response.result;
											$scope.resMessage = response.res;
											swal($scope.resMessage);

											/* console.log($scope.resMessage) */
											/* swal($scope.customerObj); */
											$scope.isLoading = false;
											if (response.status == "StatusSuccess") {
												$scope.customerId = $scope.customerObj.id;
												/*
												 * $scope.contactId =
												 * $scope.customerObj.contactId;
												 */
												$rootScope.customerId = $scope.customerId;
												/* console.log($scope.customerId); */
												if ($scope.customerId != undefined) {
													$scope.isUpdate = true;
													$scope.isSent = false;
													$("#msgs").show();
													$("#msgs").css("opacity",
															"4");
												}

												// $scope.isShow = true;
												window
														.setTimeout(
																function() {
																	$("#msgs")
																			.fadeTo(
																					500,
																					0)
																			.slideUp(
																					500,
																					function() {
																						$(
																								this)
																								.hide();
																					});
																}, 8000);
											}
										});

					}
					$("#msgs").hide();
					$scope.isShows = false;
					$scope.addAddressDetails = function() {

						$scope.isLoading = true;
						if ($rootScope.customerId != undefined) {
							$scope.customerId = $rootScope.customerId;
						} else if ($rootScope.customerId == undefined) {
							$scope.customerId = $routeParams.id;
						}
						var reqObj = {
							"typeOfAddress" : {
								"id" : $scope.value
							},
							"addressLane1" : $scope.addressLane1,
							"addressLane2" : $scope.addressLane2,
							"city" : $scope.city,
							"state" : $scope.state,
							"pincode" : $scope.pincode,
							"gst" : $scope.gst,
							"gstpercentage" : $scope.gstpercentage,
							"isSez" : $scope.isSez,
							"cid" : $scope.customerId
						}
						rpoFactory.addAddressDetails(reqObj).then(
								function(response) {
									$scope.addressDetails = response;
									$scope.resMessage = response.res;
									console.log($scope.addressDetails);
									$scope.isLoading = false;
									if (response.status == "StatusSuccess") {
										$scope.isShows = true;
										$("#msgs").show();
										$("#msgs").css("opacity", "4");
										/* $scope.emptyForm(); */
										$("#myFormVal")[0].reset();
										$scope.getAddressDetails();

									}

									window.setTimeout(function() {
										$("#msgs").fadeTo(500, 0).slideUp(500,
												function() {
													$(this).hide();
												});
									}, 8000);
								});
					}
					/* $scope.gstin= "36AABCO4337J1ZD"; */
					$scope.getAddressDetails = function() {
						if ($rootScope.customerId != undefined) {
							$scope.customerId = $rootScope.customerId;
						} else if ($rootScope.customerId == undefined) {
							$scope.customerId = $routeParams.id;
						}
						rpoFactory.getAddressDetails($scope.customerId).then(
								function(response) {
									$scope.customerRes = response.result;
									console.log($scope.customerRes);
								});

					}
					$scope.emptyForm = function() {
						$scope.addressLane1 = '';
						$scope.addressLane2 = '';
						$scope.city = '';
						$scope.state = '';
						$scope.pincode = '';
						$scope.isSez = '';
						$scope.gst = '';
						$scope.gstpercentage = '';
					}

					$scope.editAfterSaveDetails = function() {

						$scope.isLoading = true;
						$scope.afterEdit = false;
						$scope.id = $rootScope.selectionId;

						if ($rootScope.customerId != undefined) {
							$scope.customerId = $rootScope.customerId;
						} else if ($rootScope.customerId == undefined) {
							$scope.customerId = $routeParams.id;
						}
						var updateObj = {
							"typeOfAddress" : {
								"id" : $scope.value
							},
							"addressLane1" : $scope.addressLane1,
							"addressLane2" : $scope.addressLane2,
							"city" : $scope.city,
							"state" : $scope.state,
							"pincode" : $scope.pincode,
							"gst" : $scope.gst,
							"gstpercentage" : $scope.gstpercentage,
							"isSez" : $scope.isSez,
							"cid" : $scope.customerId
						}

						rpoFactory
								.updateAddressDetails($scope.id, updateObj)
								.then(
										function(response) {
											$scope.updateaddressDetails = response;
											$scope.resMessage = response.res;
											console.log($scope.addressDetails);
											$scope.isLoading = false;
											if (response.status == "StatusSuccess") {
												swal("Updated Successfully");
												$scope.afterEdit = false;
												$("#myFormVal")[0].reset();
												$("#msgs").show();
												$("#msgs").css("opacity", "4");
												$scope.getAddressDetails();
											}

											window
													.setTimeout(
															function() {
																$("#msgs")
																		.fadeTo(
																				500,
																				0)
																		.slideUp(
																				500,
																				function() {
																					$(
																							this)
																							.hide();
																				});
															}, 8000);
										});
					}
					$scope.afterEdit = false;

					$scope.editAddress = function(addressDetailss) {
						$scope.afterEdit = true;

						$rootScope.selectionId = addressDetailss.id;
						console.log(addressDetailss);
						/*
						 * $scope.addressLane1 = addressDetailss.addressLane1;
						 * $scope.addressLane2 = addressDetailss.addressLane2;
						 * $scope.city =addressDetailss.city; $scope.state =
						 * addressDetailss.state; $scope.pincode =
						 * addressDetailss.pincode; $scope.isSez =
						 * addressDetailss.isSez; $scope.gst =
						 * addressDetailss.gst; $scope.gstpercentage =
						 * addressDetailss.gstpercentage; var id =
						 * $scope.addressDetailss.typeOfAddress.id; var n =
						 * id.toString(); $scope.value = n;
						 */
						rpoFactory
								.getAddressDetailsById(addressDetailss.id)
								.then(
										function(response) {

											$scope.addressRes = response.result;
											$scope.addressLane1 = $scope.addressRes.addressLane1;
											$scope.addressLane2 = $scope.addressRes.addressLane2;
											$scope.city = $scope.addressRes.city;
											$scope.state = $scope.addressRes.state;
											$scope.pincode = $scope.addressRes.pincode;
											$scope.isSez = $scope.addressRes.isSez;
											$scope.gst = $scope.addressRes.gst;
											$scope.gstpercentage = $scope.addressRes.gstpercentage;
											var id = $scope.addressRes.typeOfAddress.id;
											var n = id.toString();
											$scope.value = n;
											// $scope.typeOfAddress.typeOfAddress
											// =
											// $scope.typeOfAddress.typeOfAddress;

										});

					}

					$scope.updateCustomer = function() {
						if ($rootScope.customerId != undefined) {
							$scope.customerId = $rootScope.customerId;
						} else if ($rootScope.customerId == undefined) {
							$scope.customerId = $routeParams.id;
						}
						/*
						 * if ($scope.userRole == "BDM") { primaryid =
						 * $scope.userId } else{ primaryid =
						 * $scope.primaryContact } if ($scope.userRole == "AM") {
						 * accId = $scope.userId }else{ accId =
						 * $scope.accountManger
						 *  } if ($scope.userRole == "BDM") { primaryid =
						 * $scope.userId } else{ primaryid =
						 * $scope.primaryContact } if ($scope.userRole == "AM") {
						 * accId = $scope.userId } else{ accId =
						 * $scope.accountManger }
						 */
						if ($scope.clientName == undefined
								|| $scope.clientName == '') {
							swal("Please Enter Customer Name");
							return false;
						} else if ($scope.clientType == undefined) {
							swal("Please Select Customer Type ");
							return false;

						} else if ($scope.customerShortName == undefined
								|| $scope.customerShortName == '') {
							swal("Please Enter Customer Short Name");
							return false;
						} else if ($scope.tdsPercentage == undefined
								|| $scope.tdsPercentage == '') {
							swal("Please Enter TDS Percentage  ");
							return false;
						} else if ($scope.email == undefined
								|| $scope.email == '') {
							swal("Please Enter Email  ");
							return false;
						}

						else if ($scope.spocName == undefined
								|| $scope.spocName == '') {
							swal("Please Enter Spoc Name  ");
							return false;
						}

						else if ($scope.billingModel == undefined) {
							swal("Please Select Billing Model");
							return false;
						} else if ($scope.startDate == undefined) {
							swal("Please Enter Empanelment Date");
							return false;
						} else if ($scope.paymentTerms == undefined) {
							swal("Please Select Payment Terms");
							return false;
						}
						/*
						 * else if(primaryid==undefined ||primaryid==null){
						 * swal("Please Select Primary BDM"); return false; }
						 * else if(accId==undefined ||accId==null){
						 * swal("Please Select Account Manager "); return
						 * false; } else if($scope.secondaryContact==undefined
						 * ||$scope.secondaryContact==null){ swal("Please
						 * Select Secondary BDM "); return false; } else
						 * if($scope.leadName==undefined){ swal("Please Select
						 * Lead"); return false; }
						 */
						var updateObj = {
							"clientName" : $scope.clientName,
							"customerType" : {
								'id' : $scope.clientType,
							},
							"customershortname" : $scope.customerShortName,
							"tdspercentage" : $scope.tdsPercentage,
							"email" : $scope.email,
							"phone" : $scope.phone,
							"billingModel" : {
								'id' : $scope.billingModel
							},
							"startDate" : $scope.startDate,
							"endDate" : $scope.endDate,
							"services" : $scope.serviceLSs,
							"leavesAllowed" : $scope.leavesAllowed,
							"paymentTerms" : {
								'id' : $scope.paymentTerms
							},
							/*
							 * "primaryContact":{ 'id':primaryid },
							 * "secondaryContact":{ 'id':$scope.secondaryContact },
							 * "accountManger":{ 'id':accId }, "lead":{
							 * 'id':$scope.leadName },
							 */
							/* "contactId":$scope.contactId, */
							"spocName" : $scope.spocName

						}
						rpoFactory.updateCustomerDetails($scope.customerId,
								updateObj).then(
								function(response) {
									$scope.updatedRes = response.result;

									$scope.resMessage = response.res;
									console.log($scope.updatedRes);
									if (response.status == "StatusSuccess") {
										swal("Updated Successfully");
										$scope.isShows = true;
										$("#msgs").show();
										$("#msgs").css("opacity", "4");
									}

									window.setTimeout(function() {
										$("#msgs").fadeTo(500, 0).slideUp(500,
												function() {
													$(this).hide();
												});
									}, 8000);
								});
					}

					/*
					 * if($scope.yes==true){ var clientSameAdd={
					 * clientName:$scope.clientName, customerType:{
					 * id:$scope.clientType }, phone:$scope.phone,
					 * email:$scope.email, addressDetails: [{ addressLane1:
					 * $scope.Address, addressLane2: $scope.addressLane2, city:
					 * $scope.client[1].City, country: $scope.client[1].Country,
					 * state: $scope.client[1].State, pincode: $scope.pincode,
					 * typeOfAddress: "billing" }, { addressLane1:
					 * $scope.Address, addressLane2: $scope.addressLane2, city:
					 * $scope.client[1].City, country: $scope.client[1].Country,
					 * state: $scope.client[1].State, pincode: $scope.pincode1,
					 * typeOfAddress: "shipping" }] }
					 * console.log(clientSameAdd);
					 * 
					 * 
					 * if(clientSameAdd==undefined) {
					 * 
					 * $scope.mes1="Please Fill All Details"; swal(
					 * $scope.mes1); return;
					 *  }
					 * 
					 * $http.post('rest/client',clientSameAdd)
					 * .then(function(response) { console.log(response);
					 * if(response.data.status=="StatusSuccess"){
					 * $scope.responseMes="Client Added Successfully";
					 * $scope.ok=true; } else{ $scope.responseMes="Internal
					 * Server Error" $scope.ok=true; }
					 * 
					 * 
					 * });
					 * 
					 * 
					 * 
					 *  // var promise=clientService.addClient1(clientSameAdd); //
					 * $location.path("/getclient"); }
					 */

					/*
					 * else if($scope.yes==false){ var clientDiffAdd={
					 * clientName:$scope.clientName, customerType:{
					 * id:$scope.clientType }, phone:$scope.phone,
					 * email:$scope.email, addressDetails: [{ addressLane1:
					 * $scope.Address1, addressLane2: $scope.addressLane21,
					 * city: $scope.client[1].City, country:
					 * $scope.client[1].Country, state: $scope.client[1].State,
					 * pincode: $scope.pincode, typeOfAddress: "billing" }, {
					 * addressLane1: $scope.Address1, addressLane2:
					 * $scope.addressLane21, city: $scope.client1[1].City,
					 * country: $scope.client1[1].Country, state:
					 * $scope.client1[1].State, pincode: $scope.pincode1,
					 * typeOfAddress: "shipping" }] };
					 * console.log(clientDiffAdd);
					 * 
					 * 
					 * if(clientDiffAdd==undefined) {
					 * 
					 * $scope.mes1="Please Fill All Details"; swal(
					 * $scope.mes1); return;
					 *  }
					 * 
					 * $http.post('/rpo-0.3.0-SNAPSHOT/rest/client',clientDiffAdd)
					 * .then(function(response) { console.log(response);
					 * if(response.data.status=="StatusSuccess"){
					 * $scope.responseMes="Client Added Successfully";
					 * $scope.ok=true; } else{ $scope.responseMes="Internal
					 * Server Error" $scope.ok=true; }
					 * 
					 * 
					 * });
					 *  // var promise=clientService.addClient1(clientDiffAdd); //
					 * $location.path("/getclient"); }
					 */
					$scope.redirect = function() {
						$location.path("/getclient");
						$window.location.reload();
					}

					// dropdown api

					$scope.getClientType = function() {
						console.log("getclienttype");

						var promise = clientService.getClientType();
						promise.then(function(data) {
							console.log(data.data);
							$scope.customerTypeData = data.data.result;
							console.log($scope.customerTypeData);
						});
					};
					$scope.getClientType();
					/*
					 * $scope.getLeadName = function(){
					 * console.log("getLeadName");
					 * 
					 * var promise=clientService.getLead();
					 * promise.then(function(data) { console.log("Leads
					 * called"); console.log(data.data);
					 * $scope.leadNames=data.data.result; console.log($scope.
					 * $scope.leadNames); }); }; $scope.getLeadName();
					 */

					$scope.success;
					$scope.updateClient = function() {
						var updateData = {
							clientName : $scope.clientName,
							clientContactName : $scope.clientContactName,
							phone : $scope.phone,
							email : $scope.email,
							country : $scope.client[1].Country,
							state : $scope.client[1].State,
							city : $scope.client[1].City,
							address : $scope.address,
							pincode : $scope.pincode

						}

						console.log(updateData);
						console.log($rootScope.id);
						var id = $rootScope.id;
						var promise = clientService.editClient(updateData, id);
						$scope.clientResponse = response;
						if ($scope.clientResponse == "OK") {
							$scope.success = "client data saved successfully!";
							console.log($scope.clientResponse);
							console.log($scope.success);
							$location.path('/getclient');
						}
					}

					$scope.formChange = function() {
						var addressTId = document.getElementById("addressT");
						var selectedText = addressTId.options[addressTId.selectedIndex].text;
						console.log(selectedText);
						if (selectedText != "Shipping") {
							document.getElementById("gSt").style.display = "none";
							document.getElementById("gstp").style.display = "none";
							document.getElementById("sez").style.display = "none";
							document.getElementById("gstin").style.display = "none";
						} else if (selectedText == "Shipping") {
							document.getElementById("gSt").style.display = "block";
							document.getElementById("gstp").style.display = "block";
							document.getElementById("sez").style.display = "block";
							document.getElementById("gstin").style.display = "block";
						}
					}

					$scope.getDetails = function() {
						var pin = $scope.pincode;
						var promise = clientService.getDetails(pin);
						promise.then(function(response) {
							console.log("country data...");
							$scope.client = response.Data;
							console.log($scope.client);
							console.log($scope.client[1].Country);
						})
					}
					$scope.getDetails1 = function() {
						var pin1 = $scope.pincode1;
						var promise = clientService.getDetails1(pin1);
						promise.then(function(response) {
							console.log("country data...");
							$scope.client1 = response.Data;
							console.log($scope.client1);
							console.log($scope.client1[1].Country);
						})
					}

					// getBilling Details

					$scope.getBillingList = function() {
						rpoFactory.billingModel().then(function(response) {
							$scope.billingList = response;
							$scope.billingListS = $scope.billingList.result;
							$scope.getPaymentList();
							$scope.getServicesList();
							/*
							 * $scope.getPrimaryLists(); $scope.getLeadLists();
							 */
							$scope.getTypeOFaddress();
							$scope.getByIdCustomerEdit();
							/* $scope.getAccountMangerLists(); */
						});
					}
					$scope.getPaymentList = function() {
						rpoFactory.paymentGet().then(function(response) {
							$scope.paymentList = response;
							$scope.paymentListS = $scope.paymentList.result;
						});
					}

					$scope.getServicesList = function() {
						rpoFactory
								.servicesGet()
								.then(
										function(response) {

											$scope.serviceList = response;
											$scope.serviceListS = $scope.serviceList.result;
											console.log("$scope.serviceListS",
													$scope.serviceListS);
											$scope.servicessss = [ {
												"date" : 1548676096000,
												"id" : 2,
												"qualificationName" : "M-Tech",
												"status" : "Active"
											},

											{
												"date" : 1548676096000,
												"id" : 1,
												"qualificationName" : "B-Tech",
												"status" : "Active"
											} ]
											console.log("$scope.servicessss",
													$scope.servicessss);
										});

					}

					/*$scope.getPrimaryLists=function(){
					
					 rpoFactory.getPrimaryList().then(function(response) {
					 $scope.primaryList = response;
					 $scope.primaryListS = $scope.primaryList;
					 console.log($scope.primaryListS);
					 });
					 }
					 $scope.getLeadLists=function(){
					
					 rpoFactory.getLeadsList().then(function(response) {
					 $scope.getLeadsList = response.result;
					 $scope.getLeadsList = $scope.getLeadsList;
					 console.log($scope.getLeadsList);
					 });
					 }
					 $scope.getAccountMangerLists=function(){
					
					 rpoFactory.getAccountMangerList().then(function(response) {
					 $scope.accountMangerList = response;
					 console.log($scope.accountMangerList);
					 });
					 }
					 $scope.getAccountMangerLists();*/
					$scope.getTypeOFaddress = function() {
						rpoFactory.getTypeOfaddress().then(function(response) {
							$scope.typeOfAddress = response;
							$scope.typeOfAddresS = $scope.typeOfAddress.result;
						});
					}
					var absurl = $location.url();
					//swal(absurl);
					var url = absurl.slice(1, 11);
					if (url == "editClient") {
						$scope.getByIdCustomerEdit = function() {
							$scope.customerID = $routeParams.id;
							rpoFactory
									.getByIdCustEdit($scope.customerID)
									.then(
											function(response) {
												$scope.customerGet = response.result;
												console.log($scope.customerGet);
												/*values binding to edit*/
												$scope.clientName = $scope.customerGet.clientName;
												$scope.spocName = $scope.customerGet.spocName;
												var ctid = $scope.customerGet.customerType.id;
												var Ctype = ctid.toString();
												$scope.clientType = Ctype;
												$scope.customerShortName = $scope.customerGet.customershortname;
												$scope.tdsPercentage = $scope.customerGet.tdspercentage;
												$scope.email = $scope.customerGet.email;
												$scope.phone = $scope.customerGet.phone;
												var bmid = $scope.customerGet.billingModel.id;
												var Bmype = bmid.toString();
												$scope.billingModel = Bmype;
												/*	$scope.contactId=$scope.customerGet.contactId;*/
												$scope.startDate = $scope.customerGet.startDate;
												$scope.endDate = $scope.customerGet.endDate;
												$scope.serviceLSs = $scope.customerGet.serviceLSs;
												$scope.leavesAllowed = $scope.customerGet.leavesAllowed;
												var ptid = $scope.customerGet.paymentTerms.id;
												var Ptype = ptid.toString();
												$scope.paymentTerms = Ptype;
												/*var pcid = $scope.customerGet.primaryContact.id;
												var Pcype = pcid.toString();
												$scope.primaryContact = Pcype;
													var scid = $scope.customerGet.secondaryContact.id;
												    var Scype = scid.toString();
												$scope.secondaryContact = Scype;
													var acId = $scope.customerGet.accountManger.id;
													var pacid=acId.toString();
												$scope.accountManger=pacid;
												  var lcype = $scope.customerGet.lead.id;
													$scope.leadName=lcype.toString();*/

												$scope.isUpdate = true;
												$scope.isSent = false;
												$scope.getAddressDetails();
											})
						}

					}

				});
