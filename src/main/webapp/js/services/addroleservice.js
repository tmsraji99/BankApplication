/*app.service('roleService', function($http,$q) {
	this.addRole=function(roleName)
	{
	var deferred=$q.defer();
	console.log(roleName);
	$http.post("rest/designation",roleName).then(function(data){
		deferred.resolve(data);
	});
	
	}
});*/

app.service('roleService', function($http,$q) {
	
	
	this.addrole=function(role)
	{
	var deferred=$q.defer();
	console.log(role);
	$http.post("rest/designation",role).then(function(data){
		deferred.resolve(data);
		console.log(data);
	});
	
	}
});