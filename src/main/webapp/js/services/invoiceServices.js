app.service('invoiceServices', function($http,$q,$resource) {
	//return $resource('rest/invoice', {}

	
	this.invoiceServicefun = function() {

		var deferred = $q.defer();

		$http.get("rest/invoice").then(
				function(data) {

					deferred.resolve(data);
				
					console.log(data);

				});

		return deferred.promise;

	}
});
	
	

app.service('invoiceService1', function($http,$q,$resource) {
	//return $resource('rest/invoice/address', {}
	this.addressServicefun = function() {

		var deferred = $q.defer();

		$http.get("rest/invoice/address").then(
				function(data) {

					deferred.resolve(data);
				
					console.log(data);

				});

		return deferred.promise;

	

	}
	
});

/*app.service('invoiceService2', function($http,$q,$resource,$scope) {
	return $resource('/rest/addCandidate/'+$scope.id, {}

	);
	
});*/