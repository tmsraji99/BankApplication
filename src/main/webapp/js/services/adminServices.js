app.service('adminServices', function($http,$q,$rootScope) {
    this.getUserList=function()
	{	   	
   	 var deferred1=$q.defer();
	    $http.get("rest/Reg").
		then(function(data)
		{
			deferred1.resolve(data);
		});
		 return deferred1.promise;	
    }

	this.updateUserList=function(id,list)
	{
	var deferred1=$q.defer();

	$http.post("rest/Reg/"+id,list).then(function(data){
		deferred1.resolve(data);
	});
	 return deferred1.promise;	
	}

	this.inactiveStatus=function(id){
		
		var deferred1=$q.defer();
		$http.delete("rest/Reg/"+id).then(function(data){
			deferred1.resolve(data);
		});
	return deferred1.promise;		
	}
	
	this.addRegData=function(userDetails)
	{
	var deferred=$q.defer();
	$http.post("rest/Reg",userDetails).then(function(data){
		deferred.resolve(data);
	});	
	}

});































