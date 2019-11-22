app.service('AdvSearchService', function($http,$q) {	
	this.getExistingData=function(){
		
		var deferred6=$q.defer();
		
		$http.get("rest/Bdmrequire").
		  then(function(data){
			
			deferred6.resolve(data);
			console.log(data);
			
			
		});
		
		return deferred6.promise;
			
	}
});