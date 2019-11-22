app.controller("assignCtrl",
		function($scope, $http, $rootScope, $location, assignService,
				clientService, bdmService, bdmReqService, $timeout, $window,
				userlistServices) {
			$scope.getrole1 = $window.localStorage.getItem("role");
			$scope.recarray = [];
			$scope.req = [];
			$scope.example17settings = {
				styleActive : true,
				enableSearch : true,
				showSelectAll : true,
				keyboardControls : true,
				showEnableSearchButton : true,
				scrollableHeight : '300px',
				scrollable : true
			};
			$scope.clientName = $window.localStorage.getItem("reqclientname");
			$scope.clientId = $window.localStorage.getItem("reqclient_id");
			$scope.requirementName = $window.localStorage
					.getItem("reqrequirementName");
			$scope.requirementId = $window.localStorage
					.getItem("reqrequirement_id");
			$scope.ok = false;
			$scope.Assignwrk = function() {
				if ($scope.targetam == undefined || $scope.targetam == "") {
					swal('Please Enter Target');
					return false;
				} else if ($scope.req.length == 0) {
					swal('Please Select Users');
					return false;
				} else {
					var assignwrk = {

						"target" : $scope.targetam,
						"client" : {
							"id" : $scope.clientId
						},
						"bdmReq" : {
							"id" : $scope.requirementId
						},
						"users" : $scope.req
					}

					/*
					 * if (assignwrk == undefined) { swal(); return; } else {
					 */
					$http.post('rest/allocaterequirment', assignwrk).then(
							function(response) {
								if (response.data.status == "DuplicateRecord") {
									swal('Already assigned');
									return false;
								}
								if (response.data.status == "StatusSuccess") {
									swal('added assignment');
									$location.path('/Assignwork');
								} else {
									swal('error')
								}
							});
				}
				// }
			}
			// $scope.redirect=function(){

			// $location.path("/Assignwork");
			// $window.location.reload();
			// }

			var fun1 = function() {
				var promise = clientService.getCa();
				promise.then(function(data) {
					$scope.client = data.data.result;
					console.log($scope.client);
				});
			}
			fun1();
			$timeout(fun1);
			// $scope.requirements = bdmService.query();

			$scope.requirements = bdmService.getClientNames();
			$scope.getRequirement = function(id) {
				var promise = bdmReqService.getClientRelatedRequirements(id);
				promise.then(function(data) {
					$scope.requirement = data.data.result;
				});
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
				})
			})
			$scope.indrecruiters = function() {
				$scope.id = $window.localStorage.getItem("usid");
				$scope.role = $window.localStorage.getItem("role");
				$http.get("rest/Reg/getRecruitersByReportingId/" + $scope.id)
						.success(function(response) {
							$scope.indrecruiters = response.result;
						});
			}
			$scope.indrecruiters();
		});
/* ============================================================================== */

/*
 * app.filter('unique', function() { return function(collection, keyname) { var
 * output = [], keys = []; angular.forEach(collection, function(item) { var key =
 * item[keyname]; if(keys.indexOf(key) === -1) { keys.push(key);
 * output.push(item); } }); return output; }; });
 * 
 * 
 */

