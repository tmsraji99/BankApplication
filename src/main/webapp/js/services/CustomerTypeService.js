app.service('CustomerTypeService', function($http,$q) {
	this.addCustomerTypeData=function(customerTypeDetails)
	{
	var deferred=$q.defer();
	console.log(customerTypeDetails);
	$http.post("rest/customerType",customerTypeDetails).then(function(data){
		deferred.resolve(data);
	});
	}
});
	app.service('listCustomerTypeServices', function($http,$q,$resource) {
		//return $resource('/rpo-0.3.0-SNAPSHOT/rest/customerType', {}			
	this.getCustomerTypelist=function()
	{
		var deferred11=$q.defer();
	    $http.get("rest/customerType").then(function(data){
		deferred11.resolve(data);
	});
     return deferred11.promise;	
}		
	});

	app.service('updateCustomerTypeService',function($http,$q){
		debugger;
		this.updateCustomerTypelist=function(id,list)
		{
		var deferred1=$q.defer();
		$http.post("rest/customerType/"+id,list).then(function(data){
			deferred1.resolve(data);
		});	
		}
	});
