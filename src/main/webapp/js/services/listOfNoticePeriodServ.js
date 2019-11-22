app.service('listNoticePeriodService',function($http,$q){
	this.getnoticeperiod=function()
	{
	var deferred=$q.defer();
	var promise = $http.get("rest/noticePeriod").then(function(data){
	deferred.resolve(data);
	});
	return deferred.promise;
		
	}
	
	//return $resource('/rpo-0.3.0-SNAPSHOT/rest/noticePeriod', {})

		
});


app.service('editNoticePeriodService',function($http,$q){
	this.editNP=function(id,noticePeriod)
	{
	var deferred=$q.defer();
	console.log(id);
	console.log(noticePeriod);
	$http.post("rest/noticePeriod/"+id,noticePeriod).then(function(data){
		deferred.resolve(data);
	});
	
	}
	
});





