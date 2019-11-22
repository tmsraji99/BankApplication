app.service('AMServices',function($http,$q,$window){
	
	this.getData=function(id){
			
			var deferred=$q.defer();
			var promise = $http.get("rest/addClientContact/getDashboard/menu/"+id).then(function(data){
				deferred.resolve(data);
			});
		return deferred.promise;
		}
	});
    
      
  		
  		
  