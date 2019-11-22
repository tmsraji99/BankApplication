app.service('invoiceServices', function($http,$q,$resource) {
	return $resource('rest/invoice', {}

	);
	
});
app.service('invoiceService1', function($http,$q,$resource) {
	return $resource('rest/invoice/address', {}

	);
	
});

/*app.service('invoiceService2', function($http,$q,$resource,$scope) {
	return $resource('/rest/addCandidate/'+$scope.id, {}

	);
	
});*/