app.service('userRegService', function($http,$q) {
	this.addRegData=function(userDetails)
	{
	var deferred=$q.defer();
	console.log(userDetails);
	$http.post("rest/Reg",userDetails).then(function(data){
		deferred.resolve(data);
	});
	return deferred.promise;
	}
	
	this.getDegList=function(userDetails)
	{
	var deferred=$q.defer();
	console.log(userDetails);
	$http.get("rest/designation",userDetails).then(function(data){
		deferred.resolve(data);
	});
	return deferred.promise;
	}
	
	this.getUserList=function()
	{
	var deferred=$q.defer();
	$http.get("rest/Reg").then(function(data){
		deferred.resolve(data);
	});
	return deferred.promise;
	}
	
	
});