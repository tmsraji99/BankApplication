app.service('assignTaskService', function($resource,$http,$q) {
	return $resource('rest/allocaterequirment', {});
});
	
app.service('assignTaskService1', function($resource,$http,$q) {
	this.getTaskAssignedRequirement=function(id){
		var deferred=$q.defer();
		$http.get("rest/allocaterequirment/assign/"+id).then(function(data){
			deferred.resolve(data);
			console.log(data);
	})
	return deferred.promise;
	}
	
	
});

