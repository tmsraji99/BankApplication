app.service('listqualificationservice',function($http,$q){
	
	this.getqualification=function(id){
			
			var deferred=$q.defer();
			var promise = $http.get("rest/Qualification/").then(function(data){
				deferred.resolve(data);
			});
		return deferred.promise;
		}
	this.getmultiLocations=function(){	
		var deferred=$q.defer();
		var promise = $http.get("rest/location").then(function(response){
			deferred.resolve(response);
		});
	return deferred.promise;
		}
	//return $resource('rest/Qualification', {})
	});


      app.service('editqualiService',function($http,$q){
	this.editqualification=function(id,qualificationName)
	{
	var deferred=$q.defer();
      console.log(id);
        console.log(qualificationName);
	$http.post("rest/Qualification/"+id,qualificationName).then(function(data){
	deferred.resolve(data);
	});
	}
      
	app.service('inactiveQualServices',function($http,$q){
		this.inactiveStatus=function(id){
			
			var deferred1=$q.defer();
			$http.delete("rest/Qualification/"+id).then(function(data){
				deferred1.resolve(data);
			});
		return deferred1.promise;
		}
	});
});

      
  		
  		
  