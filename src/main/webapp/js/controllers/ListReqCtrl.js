app
		.controller(
				"ListReqCtrl",
				function($scope, $window, ListReqSer, $timeout, $http, $q,
						userlistServices, assignService, AMServices,
						$rootScope, rpoFactory) {

					var vData;
					$scope.pageSize = '10';
					$scope.selected = {};
					$scope.getfun11 = function() {
						var rr = $window.localStorage.getItem("role");
						if (rr == "BDM") {
							$scope.homeB = true;
							$scope.homeA = false;
						} else if (rr = "bdmlead") {
							$scope.homeB = false;
							$scope.homeA = true;
						}

					}
					$scope.getfun11();

					$timeout(function() {
						$scope.getfun11();
					}, 1000);
					$scope.isLoading = true;
					$scope.viewReqInfo = function(list) {

						var reqid = list.id;
						rpoFactory
								.reqGetByiD(reqid)
								.then(
										function(res) {
											$scope.isLoading = false;
											$scope.reqDetails = res.result;
											$scope.skills = $scope.reqDetails.skills;
											$scope.qualifications = $scope.reqDetails.qualifications;
											$scope.designations = $scope.reqDetails.designations;
											$scope.locations = $scope.reqDetails.locations;
										})

						console.log($scope.reqDetails);
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

					// pagination
					$scope.maxSize = 2; // Limit number for pagination display
										// number.
					$scope.totalCount = 0; // Total number of items in all
											// pages. initialize as a zero
					$scope.pageIndex = 1; // Current page number. First page
											// is 1.-->
					$scope.pageSizeSelected = 10; // Maximum number of items
													// per page.
					/* $scope.isLoading=false; */
					/*
					 * if($rootScope.loading==true){
					 * $scope.mystyle={'opacity':'0.5'}; } else{
					 * $scope.mystyle={'opacity':'1'}; }
					 */
					$scope.getfun1 = function() {
						debugger;
						var promise = ListReqSer.getListReqData(
								$scope.pageIndex, $scope.pageSizeSelected,
								$scope.by, $scope.order, $scope.searchtext,
								$scope.searchcategory);
						promise.then(function(response) {
							console.log(response);
							$scope.isLoading = false;
							$scope.ListReqData = response.data.result;
							$scope.totalCount = response.data.totalRecords;
							vData = $scope.ListReqData;
							$scope.nodata = response.data.res;

							console.log($scope.ListReqData);
						});
					}
					$scope.searchText = function() {
						debugger;
						var role=localStorage.getItem('role');
						var id=localStorage.getItem('usid');
					/*	var promise = ListReqSer.getListReqData(
								$scope.pageIndex, $scope.pageSizeSelected,
								$scope.by, $scope.order, $scope.searchtext,
								$scope.searchcategory);
						promise.then(function(response) {
							console.log(response);
							$scope.isLoading = false;
							$scope.ListReqData = response.data.result;
							$scope.totalCount = response.data.totalRecords;
							vData = $scope.ListReqData;
							$scope.nodata = response.data.res;

							console.log($scope.ListReqData);
						});*/
						$http.get('rest/Bdmrequire/getBdmReqByRole/'+id+'/'+role+'/'+$scope.pageIndex+'/'+ $scope.pageSizeSelected+'?sortingOrder='+$scope.by+'&sortingField='+$scope.order+'&searchText='+ $scope.searchtext+'&searchField='+$scope.searchcategory)
						.then(function(response){		$scope.isLoading = false;
						$scope.ListReqData = response.data.result;
						$scope.totalCount = response.data.totalRecords;
						vData = $scope.ListReqData;
						$scope.nodata = response.data.res;

						console.log($scope.ListReqData);
					});	
					}
					$scope.clearText = function() {
						$scope.by = "", $scope.order = "",
								$scope.searchtext = "",
								$scope.searchcategory = "", $scope.getfun1();
					}
					// $scope.search = function(searchitem){
					// $scope.getfun1();
					// }
					$scope.getfun1();
					// $scope.search=function(searchitem) {
					//			
					//			
					// $scope.searchtext=searchitem.searchtext;
					// $scope.searchcategory=searchitem.searchcategory;
					// $scope.getrole = $window.localStorage.getItem("role");
					// $scope.getuserid = $window.localStorage.getItem("usid");
					// $http.get("rest/Bdmrequire/searchRequirements/"+$scope.getrole+"/"+$scope.getuserid+"/"+$scope.searchtext+"/"+$scope.searchcategory+"/"+$scope.pageIndex+"/"+$scope.pageSizeSelected).success(function(response){
					// $scope.totalCount = response.totalRecords;
					// $scope.ListReqData=response.result;
					// $scope.nodata=response.res;
					// console.log("eee",$scope.ListReqData);
					// });
					// }

					$timeout(function() {
						$scope.getfun1();
					}, 500);
					/*
					 * $timeout(function () { $scope.getfun1(); }, 3000);
					 */
					$scope.pageChanged = function() {
						$scope.getfun1();
					};

					$scope.showSkills = function(id) {
						console.log(id);
						console.log(vData[id].skills);
						$scope.skillsData = vData[id].skills;
						$scope.popUPData = vData[id];
						console.log($scope.popUPData);

						$scope.c2h = false;
						$scope.permanent = false;
						$scope.contract = false;
						if ($scope.popUPData.typeOfHiring === "Contract to Hire") {
							$scope.c2h = true;
							$scope.permanent1 = false;
							$scope.permanent2 = false;
							$scope.contract = false;
						}
						if ($scope.popUPData.typeOfHiring === "permanent") {
							$scope.c2h = false;
							$scope.contract = false;

							if ($scope.popUPData.permanent === "fixed") {
								$scope.permanentAlias = "Percentage";
								$scope.permanent1 = true;
								$scope.permanent2 = false;
							}
							if ($scope.popUPData.permanent === "slab") {
								$scope.permanentAlias = "Slab";
								$scope.permanent2 = true;
								$scope.permanent1 = false;
							}

						}

						if ($scope.popUPData.typeOfHiring === "contract") {
							$scope.c2h = false;
							$scope.permanent = false;
							$scope.contract = true;
						}
					}
					$scope.showDesignation = function(id) {
						console.log(id);
						console.log(vData[id].designations);
						$scope.designationsData = vData[id].designations;
					}
					$scope.showQualifications = function(id) {
						console.log(id);
						console.log(vData[id].qualifications);
						$scope.qualificationsData = vData[id].qualifications;
					}
					$scope.editFun = function(list) {
						$scope.urlForEdit = "#!/editBdmreqdtls/" + list.id;
						$scope.getfun1();
					}
					var promise = userlistServices.getUserList();
					promise.then(function(data) {
						$scope.users = data.data.result;
						console.log($scope.users);
						$scope.recruiters = [];

						angular.forEach($scope.users, function(key, values) {
							console.log(key.role);
							if (key.role === "recruiter") {
								$scope.recruiters.push(key);
								console.log($scope.recruiters);
							}
							console.log($scope.recruiters);
						})
					});

					// $scope.recarray=[];

					$scope.example16settings = {
						styleActive : true,
						enableSearch : true,
						showSelectAll : true,
						keyboardControls : true,
						showEnableSearchButton : true,
						scrollableHeight : '300px',
						scrollable : true
					};

					$scope.ok = false;
					$scope.Assignwrk = function(list) {
						$scope.responseMes = "Proceesing...";

						console.log(list.client.id);
						var assignwrk = {

							"client" : {
								"id" : list.client.id
							},
							"bdmReq" : {
								"id" : list.id
							},
							// "users" :list.recrutier
							"users" : $scope.userarray
						}

						console.log(assignwrk);
						if (assignwrk == undefined) {

							$scope.mes1 = "Please Fill All Details";
							swal($scope.mes1);
							return;

						}

						$http
								.post('rest/allocaterequirment', assignwrk)
								.then(
										function(response) {
											console.log(response);
											if (response.data.status == "StatusSuccess") {
												$scope.responseMes = "Updated Successfully";
												$scope.ok = true;
											} else {
												$scope.responseMes = "Internal Server Error"
												$scope.ok = true;
											}

										});

						// console.log(assignwrk);
						// assignService.addWrk(assignwrk);
					}

					var getRecrutierdetails = function() {

						var roleName = "RECRUITER";

						$http.get('rest/Reg/assign/' + roleName).then(
								function(response) {

									$scope.recrutier1 = response.data;
									console.log($scope.recrutier1);
									var userarr1 = [];

									angular.forEach($scope.recrutier1,
											function(key) {
												console.log(key.id);
												userarr1.push({
													"id" : key.id
												});
											})

									$scope.userarray = userarr1;
								});
					}

					getRecrutierdetails();
					$timeout(getRecrutierdetails);

					$scope.getdetails = function(id) {
						var promise = AMServices.getData(id);

						promise.then(function(data) {
							$scope.list = data.data;
							console.log($scope.list);
						});
					}
					// getfun1();

					// $timeout(function ()
					// {
					// getfun1();
					// }, 2000);

					$scope.assignFun = function(list) {

						$scope.clientname = list.clientName;
						$scope.client_id = list.clientId;
						$scope.requirementName = list.nameOfRequirement;
						$scope.requirement_id = list.id;

						$window.localStorage.setItem("reqclientname",
								$scope.clientname);
						$window.localStorage.setItem("reqclient_id",
								$scope.client_id);
						$window.localStorage.setItem("reqrequirementName",
								$scope.requirementName);
						$window.localStorage.setItem("reqrequirement_id",
								$scope.requirement_id);
					}
					$scope.flag=false;
					 /*var obj = {
				        		"id": $scope.ListReqData1.id
				         }*/
				       $scope.expandSelected=function(person){
				    	   
				    	   console.log(person)
				       /* angular.forEach( $scope.ListReqData,function(val){*/
				           person.expanded=false;
				           //$scope.ListReqData.id=val.id;
				           $http.post('rest/addCandidate/summaryreport',{
					      		"id": person.id
					         }
					      	).then(function(response){
				      	  $scope.statusres=response.data.status;
				    		    if($scope.statusres=='StatusSuccess'){
				    		    	$scope.flag=true;
				    		    $scope.ListReqDataResult=response.data.result;
				    		    $scope.totalrecords=response.data.totalRecords;	
				    		    console.log($scope.ListReqDataResult);
				    		    }
				    		    else{
				    		    	   $scope.ListReqDataResult=[];
				    		    		$scope.flag=false;
				    		    }
				   			
					     		});	
				       /*  })*/
				         person.expanded=true;
				        
				      	/*$http.post('rest/addCandidate/summaryreport',{
				      		"id": $scope.ListReqData.id
				         }
				      	).then(function(response){
				      	  $scope.statusres=response.data.status;
			    		    if($scope.statusres=='StatusSuccess'){
			    		    $scope.flag=true;
			    		    $scope.ListReqDataResult=response.data.result;
			    		    $scope.totalrecords=response.data.totalRecords;	
			    		    console.log($scope.ListReqData);
			    		    }
			    		    else{
			    		    	   $scope.ListReqData=[];
			    		    		$scope.flag=false;
			    		    }
			   			
				     		});	*/
				     
				         
				       }

				})
