app.service('listStatusService',function($q,$http){
	//return $resource('rest/addstatus', {})
	
	
	this.getStatusList=function()
	{
		console.log("varun");
		var deferred11=$q.defer();
	    

	    $http.get("rest/addstatus").then(function(data){
		deferred11.resolve(data);
		console.log(data.data)
		
	});
	
     return deferred11.promise;
	
}






	
});
app.service('editStatusService',function($http,$q){
	this.editStatus1=function(id,status)
	{
	var deferred=$q.defer();
	console.log(id);
	console.log(status);
	$http.post("rest/addstatus/"+id,status).then(function(data){
		deferred.resolve(data);
	});
	
	}
	
});





