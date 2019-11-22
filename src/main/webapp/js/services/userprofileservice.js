app.service('userprofileService', function($q,$http) {
	
	this.getUser=function(id)
	{
		var deferred=$q.defer();
	    $http.get("rest/Reg/"+id).then(function(data){
		deferred.resolve(data);		
	});
     return deferred.promise;	
}

	this.ChangePassword=function(user,details)
		{
		console.log(details);
		//alert(user);
		var deferred=$q.defer();
	    $http.post("rest/Reg/changepassword/"+user, details).then(function(data){
		deferred.resolve(data);		
	});
     return deferred.promise;	
}
});
