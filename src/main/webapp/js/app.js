var app = angular
		.module(
				'exampleApp',
				[ 'ngSanitize', 'ui.select','ngRoute','dx', 'smart-table', 'ngCookies', 'exampleApp.services',
						'ngMessages', 'am.multiselect','ui.bootstrap',
						'angularUtils.directives.dirPagination',
						'ngFileUpload', 'ui.bootstrap', 'ui.bootstrap1',
						'angularjs-dropdown-multiselect9',
						'angularjs-dropdown-multiselect8',
						'angularjs-dropdown-multiselect7',
						'angularjs-dropdown-multiselect6',
						'angularjs-dropdown-multiselect5',
						'angularjs-dropdown-multiselect4',
						'angularjs-dropdown-multiselect3',
						'angularjs-dropdown-multiselect2',
						'angularjs-dropdown-multiselect1',
						'angularjs-dropdown-multiselect'
						
						])

		.config(
				[
						'$routeProvider',
						'$locationProvider',
						'$httpProvider',
						function($routeProvider, $locationProvider,
								$httpProvider) {
							// 'ng-dropdown-multiselect6',
							$routeProvider
									.when(
											'/admin',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/admin');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/Admin-dashboard.html'

											})
											.when(
											'/billingModel',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/billingModel');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/views/billingModel.html',
												controller : 'billingModelCtrl'

											})
											.when(
											'/resourcePool',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Lead" || $rootScope.user.role === "recruiter") {
																			$location
																					.path('/resourcePool');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/pooledResources.html',
												controller : 'resourcePoolCtrl'
											})
											.when(
											'/incentivesConfiguration',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Management") {
																			$location
																					.path('/incentivesConfiguration');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/incentivesConfiguration.html',
												controller : 'incentiveConfigCtrl'

											})
											.when(
											'/addincentivesConfiguration',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Management") {
																			$location
																					.path('/addincentivesConfiguration');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/addincentivesConfig.html',
												controller : 'incentiveConfigCtrl'

											})
											.when(
											'/editincentivesConfiguration/:incentivesId',
											{
										
												templateUrl : 'partials/editincentivesConfiguration.html',
												controller : 'editincentiveConfigCtrl',
											/*	resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/editincentivesConfiguration/:id');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												}*/

											})
											.when(
											'/services',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/services');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/views/servicesList.html',
												controller : 'servicesCtrl'

											})
											.when(
											'/paymentTerms',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/paymentTerms');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/views/paymentTerms.html',
												controller : 'paymentTermsCtrl'

											})
											

									// modified
									.when(
											'/bdm',

											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');

														} else {

															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);

																		if ($rootScope.user.role === "BDM") {
																			$location
																					.path('/bdm');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});

														}
													}
												},
												templateUrl : 'partials/BDM-dashboard.html',
												controller : 'AdvSearchController'

											})
										.when(
											'/ListOfNoticeperiod',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/ListOfNoticeperiod');
																	
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/ListOfNoticeperiod.html',
												controller : 'listNoticePeriodController'

											})
											
									.when(
											'/roles',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/roles');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/views/roles.html',
												controller : 'designationListController'

											})
											.when(
											'/addRoles',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/addRoles');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/addRoles.html',
												controller : 'designationController'

											})
										.when(
											'/joblocations',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/joblocations');
																		
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/views/jobLocations.html',
												controller : 'locationListController'

											})
												
									.when(
											'/bdmlead',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');

														} else {

															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);

																		if ($rootScope.user.role === "BDM" ||$rootScope.user.role === "AM" ||$rootScope.user.role === "Lead"
																			|| $rootScope.user.role === "Management" ||$rootScope.user.role === "MIS" ||$rootScope.user.role === "FinanceLead") {
																			$location
																					.path('/bdmlead');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});

														}
													}
												},
												templateUrl : 'partials/AM-dashboard.html',
												// controller : 'AMController'
												controller : 'ListReqCtrl'
											})

									.when(
											'/recruiter',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');

														} else {

															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);

																		if ($rootScope.user.role === "recruiter"|| $rootScope.user.role === "Lead"
																	|| $rootScope.user.role === "AM"|| $rootScope.user.role === "BDM") {
																			$location
																					.path('/recruiter');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});

														}
													}
												},
												templateUrl : 'partials/Recruiter-dashboard.html',
												controller : 'RecriterDashBoardCtrl'

											})
									// modified
									.when('/edit/:id', {
										templateUrl : 'partials/edit.html',
										controller : EditController

									})
									.when(
											'/login',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															$window,
															UserService) {

														var accessToken = $cookieStore
																.get('accessToken');

														if (accessToken !== undefined) {
															// $rootScope.accessToken
															// = accessToken;
															$cookieStore
																	.remove('accessToken');

															/*
															 * UserService.get(function(user) {
															 * if()
															 * $rootScope.user =
															 * user;
															 * console.log($rootScope.user.role);
															 * var
															 * role=$rootScope.user.role;
															 * switch (role) {
															 * case
															 * 'Admin':$location.path("/admin");
															 * break; case
															 * 'BDM':$location.path("/bdm");
															 * break; case
															 * 'recruiter':$location.path("/recruiter");
															 * break;
															 * 
															 * case
															 * 'bdmlead':$location.path("/bdmlead");
															 * break; default:
															 * $window.location.href =
															 * '#' } });
															 */

														} else {
															$location
																	.path('/login');
														}
													}
												},
												templateUrl : 'partials/login.html',
												controller : LoginController

											})
									.when('/location', {
										templateUrl : 'partials/location.html',
										controller : LocationController

									})

									.when(
											'/skill',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/skill');
																
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/AddSkills.html',
												controller : 'SkillController'
											})

									.when(
											'/listSkill',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/listSkill');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},

												templateUrl : 'partials/Skills.html',
												controller : 'listSkillControllers'

											})

									.when(
											'/clientEdit',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM") {
																			$location
																					.path('/clientEdit');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/editClient.html',
												controller : 'ClientController'

											})
									.when(
											'/addClient',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				 || $rootScope.user.role === "AM") {
																			$location
																					.path('/addClient');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/AddClient.html',
												controller : 'ClientController'
											})
									.when(
											'/bdmreqdtls',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
															
																		if ($rootScope.user.role === "BDM"
																				 || $rootScope.user.role === "AM" || $rootScope.user.role === "Lead") {
																			$location
																					.path('/bdmreqdtls');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},

												templateUrl : 'partials/requirements.html',
												controller : "bdmCtrl"

											})
											
											
									.when(
											'/bdmreqdtls/:id',
											{
												templateUrl : 'partials/requirements.html',
												controller : "bdmCtrl"

											})
											

									.when(
											'/editBdmreqdtls/:id',
											{
												templateUrl : 'partials/EditRequirements.html',
												controller : "reqEditCtrl"

											})
									.when(
											'/viewBdmreqdtls',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				 || $rootScope.user.role === "AM") {
																			$location
																					.path('/viewBdmreqdtls');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/viewRequirement.html',
												controller : "ListReqCtrl",
												cache : true

											})
									.when(
											'/addDesignation',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/addDesignation');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/addDesignation.html',
												controller : 'designationController',
												cache : true
											})
									.when(
											'/ListDesignation',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/ListDesignation');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/designationList.html',
												controller : 'designationListController'

											})
.when(
											'/requestforCandidate',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter") {
																			$location
																					.path('/requestforCandidate');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/requestforCandidate.html',
											controller : 'requestforCandidateCtrl'

											})
											.when(
											'/reqCandList',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Lead"
																			|| $rootScope.user.role === "recruiter") {
																			$location
																					.path('/reqCandList');
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/reqCandList.html',
												controller : 'reqCandListCtrl'
											})
									.when(
											'/listSkill',
											{

												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/listSkill');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/Skills.html',
												controller : 'listSkillControllers'

											})

									.when(
											'/addNoticeperiod',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/addNoticeperiod');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/addNoticeperiod.html',
												controller : 'NoticePeriodController'

											})
											.when(
											'/addLocations',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/addLocations');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/addLocation.html',
												controller : 'addLocationcontroller'

											})
									.when(
											'/ListOfNoticeperiod',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/ListOfNoticeperiod');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/ListOfNoticeperiod.html',
												controller : 'listNoticePeriodController'

											})
									.when(
											'/status',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/status');
																	
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/status.html',
												controller : 'StatusController'

											})
									.when(
											'/ListOfStatus',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/ListOfStatus');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/ListOfStatus.html',
												controller : 'listStatusController'

											})
									.when(
											'/addqualification',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/addqualification');
																	
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/AddQualification.html',
												controller : 'AddqualificationController'
											})

									.when(
											'/listqualification',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/listqualification');
																			
																		} else {
																			$location
													
																			.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/ViewQualification.html',
												controller : 'listqualificationController'
											})

									.when(
											'/users',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin" || $rootScope.user.role === "BDM"|| $rootScope.user.role === "AM") {
																			$location
																					.path('/users');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/user.html',
												controller : 'userRegistrationCtrl'
											})
									.when(
											'/listOfUsers',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin" ||$rootScope.user.role === "BDM"|| $rootScope.user.role === "AM") {
																			$location
																					.path('/listOfUsers');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/listOfUsers.html',
												controller : 'userController',
												cache : false

											})
									.when(
											'/AddCustomerType',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/AddCustomerType');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/AddCustomerType.html',
												controller : 'CustomerTypeCtrl'

											})
											.when(
											'/500',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM" ||$rootScope.user.role === "AM"
																			|| $rootScope.user.role === "Lead" ||$rootScope.user.role === "recruiter") {
																			$location
																					.path('/500');
																		
																		} else {
																			$location
																					.path('/error');
																		}
																	});
														}
													}
												},
												templateUrl : 'partials/internal-server-error.html',
											})
									.when(
											'/ListOfCustomerType',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/ListOfCustomerType');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/ListOfCustomerType.html',
												controller : 'CustomerTypeCtrl'

											})

									.when(
											'/addCandidate',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter" || $rootScope.user.role === "Lead") {
																			$location
																					.path('/addCandidate');
																	
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/addCandidate.html',
												controller : "candidateCtrl"

											})
											.when('/matchData/:id',{
												templateUrl : 'partials/views/matchRequirement.html',
												controller : "matchRequirementCtrl"

											})
											.when('/editCanndidate/:id',{
												templateUrl : 'partials/editCandidate.html',
												controller : "editcandidatectrl"

											})
											.when('/matchCandidate/:id/:uid',{
												templateUrl : 'partials/views/matchCandidate.html',
												controller : "matchCandidateCtrl"

											})
											.when('/canreqDetails/:rId/:cId/:uId',{
												templateUrl : 'partials/canreqDetails.html',
												controller : "canreqDetailsCtrl"

											})
									.when(
											'/CandidateList1',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter"
																				|| $rootScope.user.role === "BDM" || $rootScope.user.role === "AM" || $rootScope.user.role === "Lead"
																					|| $rootScope.user.role === "MIS" || $rootScope.user.role === "Management") {
																			$location
																					.path('/CandidateList1');																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/CandidateList.html',
												controller : "CandidatelistController",
												

											})
											
												.when(
											'/submission',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter"
																				|| $rootScope.user.role === "BDM") {
																			$location
																					.path('/submission');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/submission.html',
												controller : "RecriterDashBoardCtrl",
												

											})
												.when(
											'/incentive',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter"
																			|| $rootScope.user.role === "BDM" 	|| $rootScope.user.role === "AM" || $rootScope.user.role === "Lead"
																			|| $rootScope.user.role === "Management" 	|| $rootScope.user.role === "MIS" || $rootScope.user.role === "FinanceLead") {
																			/*$location
																					.path('/submission');*/
																		
																		} else {
																			$location
																					.path('/error');
																		}
																	});
														}
													}
												},
												templateUrl : 'partials/incentives.html',
												controller : "incentivesCtrl",												
											})
												.when(
											'/calcIncentive',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ( $rootScope.user.role === "AM"|| $rootScope.user.role === "Management") {
																			/*$location
																					.path('/submission');*/
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/calc-incentives.html',
												controller : "calcincentivesCtrl",												
											})											

									.when(
											'/client',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				|| $rootScope.user.role === "AM") {
																			$location
																					.path('/client');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/AddClient.html',
												controller : 'ClientController'
											})

									.when(
											'/getclient',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		
																		if ($rootScope.user.role === "BDM" || $rootScope.user.role === "AM"
																			|| $rootScope.user.role === "Management" || $rootScope.user.role === "MIS"
																				|| $rootScope.user.role === "FinanceLead") {
																			$location
																					.path('/getclient');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/BDMLeadViewCustomer.html',
												controller : 'clientController1'
											})
											.when(
											'/onboardedlist',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		
																		if ($rootScope.user.role === "BDM" ||$rootScope.user.role === "Lead"
																				|| $rootScope.user.role === "AM"|| $rootScope.user.role === "recruiter"
																				||	 $rootScope.user.role === "MIS" || $rootScope.user.role === "Management"
																				||  $rootScope.user.role ==="FinanceLead" || $rootScope.user.role ==="FinanceExecutive") {
																			$location
																					.path('/onboardedlist');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/onboardedlist.html',
												controller : 'onboardedController'
											})
											.when(
											'/bankDetails',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		
																		if ($rootScope.user.role === "Management"
																				||  $rootScope.user.role ==="FinanceLead") {
																			$location
																					.path('/bankDetails');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/management&Finance/bankDetails.html',
												/*controller : 'onboardedController'*/
											})
											.when(
											'/addbankDetails',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		
																		if ($rootScope.user.role === "Management"
																				||  $rootScope.user.role ==="FinanceLead") {
																			$location
																					.path('/addbankDetails');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/management&Finance/addBankDetails.html',
												/*controller : 'onboardedController'*/
											})
											.when(
											'/editbankDetails',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		
																		if ($rootScope.user.role === "Management"
																				||  $rootScope.user.role ==="FinanceLead") {
																			$location
																					.path('/editbankDetails');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/management&Finance/editBankDetails.html',
												/*controller : 'onboardedController'*/
											})
												.when(
											'/addGST',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		
																		if ($rootScope.user.role === "Management"
																				||  $rootScope.user.role ==="FinanceLead") {
																			$location
																					.path('/addGST');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/management&Finance/Gst.html',
   										controller : 'gstCtrl'
											})
													.when(
											'/bdmleaddash',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				|| $rootScope.user.role === "AM") {
																			$location
																					.path('/bdmleaddash');
																		
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/bdmdashboard.html',
												controller : 'bdmleadDashboardCtrl'
											})
											.when(
											'/generateTracker',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				|| $rootScope.user.role === "AM" || $rootScope.user.role === "Admin") {
																			$location
																					.path('/generateTracker');
																			console
																					.log("hai");
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/generateTracker.html',
												controller : 'genateTrkerCtrl'
											})

									.when(
											'/editClient/:id',
											{
												templateUrl : 'partials/AddClient.html',
												controller : 'ClientController'
												/*templateUrl : 'partials/editClient.html',*/
												/*controller : 'editClientController'*/
											})

									.when(
											'/InterviewMode',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/InterviewMode');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/modeofinterview.html',
												controller : 'addModeController'
											})
									.when(
											'/InterviewModeList',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/InterviewModeList');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/modeofinterview-list.html',
												controller : 'addModeListController'
											})

									.when(
											'/addCertificateList',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/addCertificateList');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/certification.html',
												controller : 'addCertificateController'
											})
									.when(
											'/certificateList',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "Admin") {
																			$location
																					.path('/certificateList');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/listofcertifications.html',
												controller : 'ListCertificateController'
											})
									.when(
											'/addClientContact',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		
																		if ($rootScope.user.role === "BDM"
																				 || $rootScope.user.role === "AM") {
																			$location
																					.path('/addClientContact');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/AddClientInfo.html',
												controller : 'ClientContactController'
											})
											
											.when(
											'/addClientContact/:id',
											{
												templateUrl : 'partials/AddClientInfo.html',
												controller : 'ClientContactController'
											})
											
									.when(
											'/addaddress',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM") {
																			$location
																					.path('/addaddress');
																	
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/address.html',
												controller : 'AddClientAddressController'
											})
									.when(
											'/viewClientContact',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				|| $rootScope.user.role === "AM"||$rootScope.user.role === "Management"
																					|| $rootScope.user.role === "FinanceLead") {
																			$location
																					.path('/viewClientContact');
																		
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/viewClientContact.html',
												controller : 'AddClientAddressController'
											})
									.when(
											'/viewClientContactAddress/:id1',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM") {
																			$location
																					.path('/viewClientContactAddress/:id1');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/ViewClientContactAddress.html',
												controller : 'GetClientAddressController'
											})

									.when('/Assignlistwork', {
										controller : 'assignCtrl',
										templateUrl : 'partials/Assign.html'

									})
									.when('/po', {
										controller : 'poCtrl',
										templateUrl : 'partials/PO.html'

									})
									.when('/invoice', {
										// controller : '',
										templateUrl : 'partials/Invoice.html'

									})
									.when(
											'/Assignwork',
											{
												templateUrl : 'partials/AssignedWork.html',
												controller : 'assignListCtrl'
											})

									.when(
											'/AssignedWork visit',
											{
												templateUrl : 'partials/AssignedWork visit.html',
												controller : 'assignListCtrl'
											})

									.when(
											'/assignTask',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter" || $rootScope.user.role === "Lead") {
																			$location
																					.path('/assignTask');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/AssignTask.html',
												controller : 'assignTaskController'

											})
									.when(
											'/TypeOfProcess',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				|| $rootScope.user.role === "recruiter") {
																			$location
																					.path('/TypeOfProcess');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/TypeOfProcess.html',
												controller : 'TypeOfProcessController'

											})
											
											.when('/typeofProcess/:id/:uid/:recID', {
												templateUrl : 'partials/TypeOfProcess.html',
												controller : 'TypeOfProcessController'

											})
									.when(
											'/offerLetter',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "BDM"
																				|| $rootScope.user.role === "recruiter") {
																			$location
																					.path('/offerLetter');
																	
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/offerLetter.html',
												controller : 'offerLetter'

											})
									.when('/onHold', {
										templateUrl : 'partials/AmOnHold.html',
										controller : 'CandidatelistController'

									})
									.when(
											'/onHold1',
											{
												templateUrl : 'partials/quiryanswer.html',
												controller : 'CandidatelistController'

											})
									.when(
											'/TypeOfProcessList',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter") {
																			$location
																					.path('/TypeOfProcessList');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/TypeOfProcessList.html',
												controller : 'TypeOfProcessListController'
											})
									.when(
											'/IntreviewFeedbackList',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');
														} else {
															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);
																		if ($rootScope.user.role === "recruiter") {
																			$location
																					.path('/IntreviewFeedbackList');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});
														}
													}
												},
												templateUrl : 'partials/IntreviewFeedbackList.html',
												controller : 'InterviewFeedbackList'
											})
									.when(
											'/InterviewFeedBackListtotal',
											{
												templateUrl : 'partials/InterviewFeedBackListtotal.html',
												controller : 'InterviewFeedbackList'
											})
											
											.when(
											'/listOfCandidate',
											{
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															UserService) {
														var originalPath = $location
																.path();
														var accessToken = $cookieStore
																.get('accessToken');
														if (accessToken === undefined) {
															$rootScope.accessToken = accessToken;
															$location
																	.path('/login');

														} else {

															UserService
																	.get(function(
																			user) {
																		$rootScope.user = user;
																		console
																				.log($rootScope.user.role);

																		if ($rootScope.user.role === "recruiter" || $rootScope.user.role === "Lead" ) {
																			$location
																					.path('/listOfCandidate');
																			
																		} else {
																			$location
																					.path('/error');
																		}

																	});

														}
													}
												},
												templateUrl : 'partials/listOfCandidates.html',
												controller : 'candidatelistController'

											})
									// modified
									.when('/edit/:id', {
										templateUrl : 'partials/edit.html',
										controller : EditController

									})
									// .when('/invoice', {

									// templateUrl : 'partials/invoice.html',
									// controller : "invoiceController"
									// })
									.when(
											'/reqTrack',
											{
												templateUrl : 'partials/RequirementDetails.html',
												controller : 'candiReqList'
											}).when('/profile', {

										templateUrl : 'partials/profile.html',
										controller : "userprofileController"
									}).when('/error', {
										templateUrl : 'partials/error.html',
									// controller : "userprofileController"
									}).otherwise(
											{
												
												resolve : {
													"check" : function(
															$rootScope,
															$location,
															$cookieStore,
															$window,
															UserService) {

														var accessToken = $cookieStore
																.get('accessToken');

														if (accessToken !== undefined) {
															// $rootScope.accessToken
															// = accessToken;
															$cookieStore
																	.remove('accessToken');

															/*
															 * UserService.get(function(user) {
															 * if()
															 * $rootScope.user =
															 * user;
															 * console.log($rootScope.user.role);
															 * var
															 * role=$rootScope.user.role;
															 * switch (role) {
															 * case
															 * 'Admin':$location.path("/admin");
															 * break; case
															 * 'BDM':$location.path("/bdm");
															 * break; case
															 * 'recruiter':$location.path("/recruiter");
															 * break;
															 * 
															 * case
															 * 'bdmlead':$location.path("/bdmlead");
															 * break; default:
															 * $window.location.href =
															 * '#' } });
															 */

														} else {
															$location
																	.path('/login');
														}
													}
												},	
										templateUrl : 'partials/login.html',
									// controller : IndexController
										controller : LoginController
									});

							$locationProvider.hashPrefix('!');

							/*
							 * Register error provider that shows message on
							 * failed requests or redirects to login page on
							 * unauthenticated requests
							 */
							$httpProvider.interceptors.push(function($q,
									$rootScope, $location) {
								return {
									'responseError' : function(rejection) {
										var status = rejection.status;
										var config = rejection.config;
										var method = config.method;
										var url = config.url;

										if (status == 401) {
											$location.path("/login");
										} else {
											$rootScope.error = method + " on "
													+ url
													+ " failed with status "
													+ status;
										}

										return $q.reject(rejection);
									}
								};
							});

							/*
							 * Registers auth token interceptor, auth token is
							 * either passed by header or by query parameter as
							 * soon as there is an authenticated user
							 */
							$httpProvider.interceptors
									.push(function($q, $rootScope, $location) {
										return {
											'request' : function(config) {
												var isRestCall = config.url
														.indexOf('rest') == 0
														|| config.url
																.indexOf('rest') == 20;
												console
														.log(config.url
																+ "     "
																+ config.url
																		.indexOf('rest'));
												// console.log(config.url.indexOf('rest'));
												// console.log(isRestCall);
												if (isRestCall
														&& angular
																.isDefined($rootScope.accessToken)) {
													var accessToken = $rootScope.accessToken;
													if (exampleAppConfig.useAccessTokenHeader) {
														config.headers['X-Access-Token'] = accessToken;
													} else {
														config.url = config.url
																+ "?token="
																+ accessToken;
													}
												}
												return config
														|| $q.when(config);
											}
										};
									});

						} ]

		).run(function($rootScope, $location, $cookieStore, UserService) {

			/* Reset error when a new view is loaded */
			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});

			$rootScope.hasRole = function(role) {

				if ($rootScope.user === undefined) {
					return false;
				}

				if ($rootScope.user.role === undefined) {
					return false;
				}

				return $rootScope.user.role;
			};

			
			$rootScope.logout = function() {
		      
				delete $rootScope.user;
				delete $rootScope.accessToken;
				$cookieStore.remove('accessToken');	
				localStorage.clear();
				sessionStorage.clear()
				$location.path("/login");
			};
			 

			/* Try getting valid user from cookie or go to login page */

			var originalPath = $location.path();
			console.log(originalPath);

			$location.path("/login");
			var accessToken = $cookieStore.get('accessToken');
			if (accessToken !== undefined) {
				$rootScope.accessToken = accessToken;
				$location.path(originalPath);
				UserService.get(function(user) {
					$rootScope.user = user;

				});

			} else if (accessToken === undefined) {

				$location.path('\login');
			}

			$rootScope.initialized = true;
		});

function IndexController($scope, BlogPostService) {

	$scope.blogPosts = BlogPostService.query();

	$scope.deletePost = function(blogPost) {
		blogPost.$remove(function() {
			$scope.blogPosts = BlogPostService.query();
		});
	};
}

function EditController($scope, $routeParams, $location, BlogPostService) {

	$scope.blogPost = BlogPostService.get({
		id : $routeParams.id
	});

	$scope.save = function() {
		$scope.blogPost.$save(function() {
			$location.path('/');
		});
	};
}

function CreateController($scope, $location, BlogPostService) {

	$scope.blogPost = new BlogPostService();

	$scope.save = function() {
		$scope.blogPost.$save(function() {
			$location.path('/');
		});
	};
};

function LoginController($scope, $rootScope, $location, $cookieStore,
		UserService, $window) {
	$scope.signIn = true;
	$scope.rememberMe = false;

	$scope.login = function() {
		
		/* var string1 = removeSpaces(document.getElementById('mainCaptcha').value);
		    var string2 = removeSpaces(document.getElementById('txtInput').value);
		    if (string1 == string2) {
		    	alert('captcha verified');
		        return true;
		    }
		    else {
		    	alert('Please enter valid captcha');
		        return false;
		    }*/
		debugger;
		UserService.authenticate($.param({
			username : $scope.username,
			password : $scope.password
		}), function(authenticationResult) {
			var accessToken = authenticationResult.token;
			$scope.accessToken=authenticationResult.token
			/*function ValidCaptcha() {*/
			   
			/*}	*/
			$rootScope.accessToken = accessToken;
			if ($rootScope.accessToken != "Bad credentials") {
				$rootScope.id = authenticationResult.user.id;
				$rootScope.role=authenticationResult.user.role;
				
				console.log(authenticationResult);
				console.log($rootScope.id);
				localStorage.setItem("role",
						authenticationResult.user.role);
				localStorage.setItem("usid",
						authenticationResult.user.id);
				
				sessionStorage.setItem('userRole',authenticationResult.user.role);
				sessionStorage.setItem('UserId',authenticationResult.user.id);
				

				var date = new Date();
				date.setTime(date.getTime() + (60 * 1000));
				$cookieStore.put('accessToken', accessToken, {
					expires : date
				});

				UserService.get(function(user) {
					$rootScope.user = user;
					console.log(user);
				});

				console.log(authenticationResult.user.role);
				var role = authenticationResult.user.role;

				switch (role) {
				case 'Admin':
					$location.path("/admin");
					break;
				case 'recruiter':
					$location.path("/recruiter");
					break;
				case 'BDM':
					$location.path("/recruiter");
					break;
				case 'AM':
					$location.path("/recruiter");
					break;
				case 'Lead':
					$location.path("/recruiter");
					break;
				case 'Management':
					$location.path("/bankDetails");
					break;
				case 'MIS':
					$location.path("/CandidateList1");
					break;
				case 'FinanceLead':
					$location.path("/bankDetails");
					break;
				case 'FinaneceExecutive':
					$location.path("/onboardedlist");
					break;


				default:
					$window.location.href = '#'
				}
			} else {
				$scope.errorLogin = true;
				$scope.loginError = "Invalid Credentials!!";
			}

		});
	};
};
//function Captcha() {
//    var alpha = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
//    var i;
//    for (i = 0; i < 6; i++) {
//        var a = alpha[Math.floor(Math.random() * alpha.length)];
//        var b = alpha[Math.floor(Math.random() * alpha.length)];
//        var c = alpha[Math.floor(Math.random() * alpha.length)];
//        var d = alpha[Math.floor(Math.random() * alpha.length)];
//        var e = alpha[Math.floor(Math.random() * alpha.length)];
//        var f = alpha[Math.floor(Math.random() * alpha.length)];
//        var g = alpha[Math.floor(Math.random() * alpha.length)];
//    }
//    var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' ' + f + ' ' + g;
//    document.getElementById("mainCaptcha").value = code
//}
//function removeSpaces(string) {
//    return string.split(' ').join('');
//}
function LocationController($scope, locationService) {

	$scope.location = locationService.query();
	console.log($scope.location);

}
function SkillController($scope, skillService, $location) {
	console.log("skills Controller");

	$scope.addSkillSet = function(skillName) {
		console.log(skillName);
		console.log("skills going to services");
		skillService.addSkill(skillName);
		$location.path('/listSkill');

	}

}


var services = angular.module('exampleApp.services', [ 'ngResource' ]);

services.factory('UserService', function($resource) {

	return $resource('rest/user/:action', {}, {
		authenticate : {
			method : 'POST',
			params : {
				'action' : 'authenticate'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}
	});
});

services.factory('locationService', function($resource) {

	return $resource('rest/location', {}

	);
});

services.service('skillService', function($http, $q) {

	this.addSkill = function(skillName) {
		var deferred = $q.defer();
		console.log(skillName);
		$http.post("/rest/skill", skillName).then(function(data) {
			deferred.resolve(data);
		});

	}
});

services.factory('BlogPostService', function($resource) {

	return $resource('rest/blogposts/:id', {
		id : '@id'
	});
});

app.directive('numbersOnly', function() {
	return {
		require : 'ngModel',
		link : function(scope, element, attr, ngModelCtrl) {
			function fromUser(text) {
				if (text) {
					var transformedInput = text.replace(/[^0-9]/g, '');

					if (transformedInput !== text) {
						ngModelCtrl.$setViewValue(transformedInput);
						ngModelCtrl.$render();
					}
					return transformedInput;
				}
				return undefined;
			}
			ngModelCtrl.$parsers.push(fromUser);
		}
	};
});

app.directive('validNumber', function() {
	return {
		require : '?ngModel',
		link : function(scope, element, attrs, ngModelCtrl) {
			if (!ngModelCtrl) {
				return;
			}

			ngModelCtrl.$parsers.push(function(val) {
				if (angular.isUndefined(val)) {
					var val = '';
				}

				var clean = val.replace(/[^-0-9\.]/g, '');
				var negativeCheck = clean.split('-');
				var decimalCheck = clean.split('.');
				if (!angular.isUndefined(negativeCheck[1])) {
					negativeCheck[1] = negativeCheck[1].slice(0,
							negativeCheck[1].length);
					clean = negativeCheck[0] + '-' + negativeCheck[1];
					if (negativeCheck[0].length > 0) {
						clean = negativeCheck[0];
					}

				}

				if (!angular.isUndefined(decimalCheck[1])) {
					decimalCheck[1] = decimalCheck[1].slice(0, 2);
					clean = decimalCheck[0] + '.' + decimalCheck[1];
				}

				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}
				return clean;
			});

			element.bind('keypress', function(event) {
				if (event.keyCode === 32) {
					event.preventDefault();
				}
			});
		}
	};
});
app.directive('simpleCaptcha', function() {
    return {
        restrict: 'E',
        scope: { valid: '=' },
        template: '<input ng-model="a.value" ng-keyup="getkeys($event)" ng-show="a.input" style="width:2em; text-align: center; color: #000" required><span ng-hide="a.input">{{a.value}}</span>&nbsp;{{operation}}&nbsp;<input ng-model="b.value" ng-show="b.input" ng-keyup="getkeys($event)" style="width:2em; text-align: center;" required><span ng-hide="b.input">{{b.value}}</span>&nbsp;=&nbsp;{{result}}',
        controller: function($scope,$rootScope) {
            debugger;
            
            var show = Math.random() > 0.5;
            
            var value = function(max){
                return Math.floor(max * Math.random());
            };
            
            var int = function(str){
                return parseInt(str, 10);
            };
          
            $scope.a = {
                value: show? undefined : 1 + value(4),
                input: show
            };
            $scope.b = {
                value: !show? undefined : 1 + value(4),
                input: !show
            };
            $scope.operation = '+';
            
            $scope.result = 5 + value(5);
            
            var a = $scope.a;
            var b = $scope.b;
            var result = $scope.result;
            
            var checkValidity = function(){
                if (a.value && b.value) {
                    var calc = int(a.value) + int(b.value);
                    $scope.valid = calc == result;
                    
                } else {
                    $scope.valid = false;
                }
              //$scope.$apply(); // needed to solve 2 cycle delay problem;
            };
            $scope.getkeys = function(value){
            	debugger;
            	checkValidity()
            	console.log($scope.valid);
            	if( $scope.valid == false){
            		swal('please enter captcha');
            	}
            }
            
          /*  $scope.$watch('a.value', function(){    
                checkValidity();
            });
            
            $scope.$watch('b.value', function(){    
                checkValidity();
            });*/
            
            
            
        }
    };
});

app.controller('captchaCtrl', function($scope){
  /* $scope.getkeys = function(value){
    	console.log(event);
    	if($scope.valid === false){
    		alert('please enter valid captcha')
    	}
    }*/
});
//app.factory('Excel',function($window){
//    var uri='data:application/vnd.ms-excel;base64,',
//        template='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
//        base64=function(s){return $window.btoa(unescape(encodeURIComponent(s)));},
//        format=function(s,c){return s.replace(/{(\w+)}/g,function(m,p){return c[p];})};
//    return {
//        tableToExcel:function(tableId,worksheetName){
//            var table=$(tableId),
//                ctx={worksheet:worksheetName,table:table.html()},
//                href=uri+base64(format(template,ctx));
//            return href;
//        }
//    };
//})
