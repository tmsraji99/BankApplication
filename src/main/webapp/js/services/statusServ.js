app.service('statusService', function($http,$q) {
	
	
	this.addStatus=function(Status)
	{
	var deferred=$q.defer();
	console.log(Status);
	$http.post("rest/addstatus",Status).then(function(data){
		deferred.resolve(data);
		console.log(data);
	});
	
	}
});