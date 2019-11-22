app
		.controller(
				"resourcePoolCtrl",
				function($scope, $window, ListReqSer, $timeout, $http, $q,
						$location) {
					$scope.searchText = function(excel) {
						debugger;
						if (excel == undefined) {
							swal("Select Excel and Upload");
							return false;
						} else {
							var file = excel;
							var deferred = $q.defer();
							var hd = {
								transformRequest : angular.identity,
								headers : {
									'Content-Type' : undefined
								}
							}
							var formData = new FormData();
							formData.append('file', file);
							$http
									.post(
											"http://localhost:8089/elastic-search/candiate/uploadResource",
											formData, hd)
									.success(function(response) {
										console.log(response);
										if (response.status2 == "SUCCESS") {
											swal("Successfully Uploaded");
										}
									});
						}
					}
					$scope.pageIndex = 1; // Current page number. First page
					// is 1.-->
					$scope.pageSizeSelected = 10; // Maximum number of items
					// per page.
					$scope.selected = {};
					$scope.maxSize = 2; // Limit number for pagination display
										// number.
					$scope.totalRecords = 0; // Total number of items in all
												// pages. initialize as a zero
					$scope.pageSize = '10';
					$scope.searchText1 = function(skill, designation, location,
							noticePeriod, salary, currentLocation,
							currentCompanyName, exp) {
						debugger;
						if ($scope.skill == undefined || $scope.skill == "") {
							swal("Please Enter Skill and Search");
							return false;
						}
						if ($scope.designation == undefined) {
							$scope.designation = null;
						}
						if ($scope.exp == undefined) {
							$scope.exp = null;
						}
						if ($scope.salary == undefined) {
							$scope.salary = null;
						}
						if ($scope.location == undefined) {
							$scope.location = null;
						}
						if ($scope.noticePeriod == undefined) {
							$scope.noticePeriod = null;
						}
						if ($scope.currentLocation == undefined) {
							$scope.currentLocation = null;
						}
						if ($scope.currentCompanyName == undefined) {
							$scope.currentCompanyName = null;
						}
						var skillsObj = {
							skill : $scope.skill,
							designation : $scope.designation,
							exp : $scope.exp,
							salary : $scope.salary,
							location : $scope.location,
							noticePeriod : $scope.noticePeriod,
							currentLocation : $scope.currentLocation,
							currentCompany : $scope.currentCompanyName
						}
						$scope.isLoading = true;
						$http
								.post(
										"http://localhost:8089/elastic-search/candiate/getResourcessssss/"
												+ $scope.pageIndex + "/"
												+ $scope.pageSize, skillsObj)
								.success(
										function(response) {
											$scope.isLoading = false;
											console.log(response.totalRecords);
											console.log(response.candidates);
											$scope.totalRecords = response.totalRecords;
											$scope.totalPages = response.totalPages;
											console.log($scope.totalPages);
											$scope.result = response.candidates;
											if (response.candidates.length >= 0) {
												swal('Records Found');
											}
											if (response.candidates.length == 0) {
												swal('No Records Found');
											}
											$scope.skills =
										 $scope.result[0].skills;
											/*
											 * $scope.locations =
											 * $scope.result[0].location;
											 */
											/*angular
													.forEach(
															$scope.result,
															function(value,
																	$index) {
																$scope.skills = [];
																$scope.skills
																		.push(value.skills[$index].skillName);
																console
																		.log($scope.skills);
															});*/

										});
					}
					$scope.pageChanged = function() {
						$scope.searchText1();
					};
					$scope.clearText = function() {
						$scope.skill = "", $scope.designation = "",
								$scope.exp = "", $scope.salary = "",
								$scope.location = "";
						$scope.noticePeriod = "";
						$scope.currentLocation = "";
						$scope.currentCompanyName = "";
					}
					$scope.mappingRequirement = function(data) {
						console.log(data);
						var reqId = data;
						$location.path("/matchData/" + reqId);
					}
				});
