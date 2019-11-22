app.service('userlistServices', function($http,$q,$resource) {
	//return $resource('rest/userReg', {}

	//);
	
	this.getUserList=function()
	{
		console.log("varun");
		var deferred11=$q.defer();
	    

	    $http.get("rest/Reg").then(function(data){
		deferred11.resolve(data);
		console.log(data)
		
	});
	
     return deferred11.promise;
	
}
	
	
	
});
app.service('updateUserService',function($http,$q){
	this.updateUserList=function(id,list)
	{
		console.log("jyothi");
		console.log(list);
	var deferred1=$q.defer();

	$http.post("rest/Reg/"+id,list).then(function(data){
		deferred1.resolve(data);
	});
	 return deferred1.promise;

	
	}
});


app.service('getUserByReportingId',function($http,$q){
	this.getUserByReporting=function(loginId)
	{
	$http.get("rest/Reg/getUserByReportingId/"+loginId,list).then(function(data){
		deferred1.resolve(data);
	
	});
	
	return deferred1.promise;
	
	}
});
app.service('inactiveUserServices',function($http,$q){
	this.inactiveStatus=function(id){
		
		var deferred1=$q.defer();
		$http.delete("rest/Reg/"+id).then(function(data){
			deferred1.resolve(data);
		});
	return deferred1.promise;
		
	}
});




























