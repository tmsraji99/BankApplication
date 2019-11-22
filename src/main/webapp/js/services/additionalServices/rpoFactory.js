app.factory('rpoFactory',['$http','$q',function($http,$q,$scope){ 
	return {
		
		//biiling Model Get
		billingModel:function(){
//			var mul = {
//			transformRequest: angular.identity,
//			       headers: {'Content-Type': undefined }
//			}
			var deferred = $q.defer();
			$http.get("rest/BillingModel").success(function(response){
				deferred.resolve(response);
				}).error(function(err){
				deferred.reject(err);
			})
			return deferred.promise;
			},
			//editBilling Address
		billingEditModel:function(id,reqObj){
			var deferred = $q.defer();
			$http.post("rest/BillingModel/"+id,reqObj).success(function(response){
				deferred.resolve(response);
				}).error(function(err){
				deferred.reject(err);
			})
			return deferred.promise;
			},	
			//billingModel
		billingSaveModel:function(reqObj){
			var deferred = $q.defer();
			$http.post("rest/BillingModel/",reqObj).success(function(response){
				deferred.resolve(response);
				}).error(function(err){
					deferred.reject(err);
			})
			return deferred.promise;
			},	
		servicesGet:function(){
				var deferred = $q.defer();
				$http.get("rest/services").success(function(response){
					deferred.resolve(response);
					}).error(function(err){
					deferred.reject(err);
				})
				return deferred.promise;
				},
		serviceSave:function(reqObj){
			var deferred = $q.defer();
			$http.post("rest/services",reqObj).success(function(response){
				deferred.resolve(response);
				}).error(function(err){
					deferred.reject(err);
			})
			return deferred.promise;
			},	
		serviceEdit:function(id,reqObj){
			var deferred = $q.defer();
			$http.post("rest/services"+id,reqObj).success(function(response){
				deferred.resolve(response);
				}).error(function(err){
					deferred.reject(err);
			})
			return deferred.promise;
			},
		paymentGet:function(){
				var deferred = $q.defer();
				$http.get("rest/PaymentTerms").success(function(response){
					deferred.resolve(response);
					}).error(function(err){
					deferred.reject(err);
				})
				return deferred.promise;
				},
		paymentSave:function(reqObj){
					var deferred = $q.defer();
					$http.post("rest/PaymentTerms",reqObj).success(function(response){
						deferred.resolve(response);
						}).error(function(err){
							deferred.reject(err);
					})
					return deferred.promise;
					},	
		paymentEdit:function(id,reqObj){
					var deferred = $q.defer();
					$http.post("rest/PaymentTerms"+id,reqObj).success(function(response){
						deferred.resolve(response);
						}).error(function(err){
							deferred.reject(err);
					})
					return deferred.promise;
					},
		getPrimaryList:function(){
					var deferred = $q.defer();
					$http.get("rest/Reg/listBDMandLead").success(function(response){
						deferred.resolve(response);
						}).error(function(err){
							deferred.reject(err);
					})
					return deferred.promise;
					},
		getLeadsList:function(){
			
						var deferred = $q.defer();
						$http.get("rest/Reg/getUserNamesByRole?role=Lead").success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
					
		getAccountMangerList:function(){
						var deferred = $q.defer();
						$http.get("rest/Reg/listAM").success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		getTypeOfaddress:function(){
					var deferred = $q.defer();
					$http.get("rest/typeofaddress").success(function(response){
						deferred.resolve(response);
						}).error(function(err){
							deferred.reject(err);
					})
					return deferred.promise;
					},
					
					
					//add customer Details
		customerDetails:function(reqObj){
			
						var deferred = $q.defer();
						$http.post("rest/client",reqObj).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
						
		addAddressDetails:function(reqObj){
						var deferred = $q.defer();
						$http.post("rest/addressdetails",reqObj).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		getAddressDetails:function(id){
						var deferred = $q.defer();
						$http.get("rest/addressdetails/readbasedoncpid/"+id).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		getAddressDetailsById:function(id){
						var deferred = $q.defer();
						$http.get("rest/addressdetails/"+id).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		updateCustomerDetails:function(id,reqObj){
						var deferred = $q.defer();
						$http.post("rest/client/"+id,reqObj).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		updateAddressDetails:function(id,reqObj){
						var deferred = $q.defer();
						$http.post("rest/addressdetails/"+id,reqObj).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		getByIdCustEdit:function(id){
			
						var deferred = $q.defer();
						$http.get("rest/client/"+id).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
						
		//candidate Api S Starts here
						
						
		cadidateAdd:function(reqObj){
						var deferred = $q.defer();
						$http.post("rest/addCandidate",reqObj).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		getClientAll:function(id,status){
						var deferred = $q.defer();
						$http.get("rest/allocaterequirment/getClientsByRecById/"+id+"/"+status).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		getReqByClient:function(userId,id,status){
						var deferred = $q.defer();
						$http.get("rest/allocaterequirment/getReqByRecIdandClientId/"+userId+"/"+id+"/"+status).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		candidateMapping:function(reqObj){
					var deferred = $q.defer();
					$http.post("rest/candidateReqMapping/",reqObj).success(function(response){
						deferred.resolve(response);
						}).error(function(err){
							deferred.reject(err);
					})
					return deferred.promise;
					},
		candidateGetByid:function(id){
					var deferred = $q.defer();
					$http.get("rest/addCandidate/"+id).success(function(response){
						deferred.resolve(response);
						}).error(function(err){
							deferred.reject(err);
					})
					return deferred.promise;
					},
		requirementById:function(id,status,pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory){
					var deferred = $q.defer();
					
					$http.get("rest/allocaterequirment/getReqByRecIdandUserId/"+id+"/"+status+'/'+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory).success(function(response){
						deferred.resolve(response);
						}).error(function(err){
							deferred.reject(err);
					})
					return deferred.promise;
					},
		reqGetByiD:function(id){
						var deferred = $q.defer();
						$http.get("rest/Bdmrequire/"+id).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
		candidatesByRole:function(role,status,id){
			
						var deferred = $q.defer();
						$http.get("rest/addCandidate/list/"+role+"/"+status+"/"+id).success(function(response){
							deferred.resolve(response);
							}).error(function(err){
								deferred.reject(err);
						})
						return deferred.promise;
						},
						
						
						
						//canreqdetails
						
						canreqGetByiD:function(id){
							var deferred = $q.defer();
							$http.get("rest/Bdmrequire/"+id).success(function(response){
								deferred.resolve(response);
								}).error(function(err){
									deferred.reject(err);
							})
							return deferred.promise;
							},
			canreqdidatesByRole:function(id){
							var deferred = $q.defer();
							$http.get("rest/addCandidate/"+id).success(function(response){
								deferred.resolve(response);
								}).error(function(err){
									deferred.reject(err);
							})
							return deferred.promise;
							},
						
						
	}
	
}]);