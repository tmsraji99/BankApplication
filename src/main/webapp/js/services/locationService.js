app.service('locationService', function($http,$q) {		
	this.addLocation=function(jobLocation)
	{
	var deferred=$q.defer();
	console.log(jobLocation);
	$http.post("rest/location",jobLocation).then(function(data){
		deferred.resolve(data);
		console.log(data);
	});	
	}
});