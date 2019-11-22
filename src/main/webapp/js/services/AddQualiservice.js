app.service('qualificationService', function($http,$q) {
	
	
	this.addQualification=function(qualificationName)
	{
	var deferred=$q.defer();
	console.log(qualificationName);
	$http.post("rest/Qualification",qualificationName).then(function(data){
		deferred.resolve(data);
		console.log(data);
	});
	
	}
});