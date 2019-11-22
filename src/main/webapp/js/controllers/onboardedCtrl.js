app
		.controller(
				'onboardedController',
				function($scope, $rootScope, $http, clientService1, $location,
						$rootScope, clientService, $window, $timeout,
						clientContactService, rpoFactory) {

					$scope.pageSize = '10';
					// pagination
					$scope.maxSize = 2; // Limit number for pagination display
										// number.
					$scope.totalCount = 0; // Total number of items in all
											// pages. initialize as a zero
					$scope.pageIndex = 1; // Current page number. First page
											// is 1.-->
					$scope.pageSizeSelected = 10; // Maximum number of items
													// per page.
					$scope.isLoading = true;
					$scope.getOnboardfun = function() {
						debugger;
						$scope.useRole = localStorage.getItem('role');
						$scope.loginId = localStorage.getItem('usid');
						$http
								.get(
										'rest/candidateReqMapping/getCandidatesOnboardList/'
												+ $scope.useRole + '/'
												+ $scope.loginId + '/'
												+ $scope.pageIndex + '/'
												+ $scope.pageSizeSelected
												+ '?sortingOrder='
												+ $scope.sortingOrder
												+ '&sortingField='
												+ $scope.sortingField
												+ '&searchText='
												+ $scope.searchtext
												+ '&searchField='
												+ $scope.searchcategory)
								.then(
										function(response) {

											$scope.isLoading = false;
											$scope.onboardedlist = response.data.result;
											$scope.totalCount = response.data.totalRecords;
											if (response.data.result == null) {
												$scope.noData = "No Content Found";
											}
										});
					}
					$scope.clearText = function() {
						$scope.by = "", $scope.order = "",
								$scope.searchtext = "",
								$scope.searchcategory = "", $scope
										.getOnboardfun();
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
					$scope.viewInfo = function(lists) {

						$scope.isLoading = true;
						$scope.requirementId = lists.bdmReqId
						$http
								.get("rest/addCandidate/" + lists.candidateid)
								.then(
										function(response) {
											$scope.candidatelist1 = response.data.result;
											$scope.isLoading = false;
										});
					}
					$timeout($scope.getOnboardfun());

					$timeout(function() {
						$scope.getOnboardfun();
					}, 1000);
					$scope.pageChanged = function() {
						$scope.getOnboardfun();
						console.log('Page changed to: ' + $scope.pageIndex);
					};

					$scope.id = 1;
					$scope.options = [ {
						'id' : 1,
						'pageSize' : 10
					}, {
						'id' : 2,
						'pageSize' : 20
					}, {
						'id' : 3,
						'pageSize' : 30
					}, {
						'id' : 4,
						'pageSize' : 40
					}, {
						'id' : 5,
						'pageSize' : 50
					} ]
				});