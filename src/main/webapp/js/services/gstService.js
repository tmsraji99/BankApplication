app.service("gstservice",function($http,$q){
	this.addgstData =function(obj)
	{
	  var deferred=$q.defer();
	  var promise = $http.post("rest/GST/addGst",obj).then(function(data){
			deferred.resolve(data);
	   });
	  return deferred.promise;		
	}
	this.getgstData =function(obj)
	{
	  var deferred=$q.defer();
	  var promise = $http.get("rest/GST/getAllGst").then(function(data){
				deferred.resolve(data);
	  });
	return deferred.promise;	
	}
	this.deletegstData =function(_id)
	{
	  var deferred=$q.defer();
	  var promise =  $http.delete('rest/GST/delete/'+_id).then(function(data){
		deferred.resolve(data);
		 });
		return deferred.promise;
	}
	this.getbyidgstData =function(_id)
	{
      var deferred=$q.defer();
      var promise =  $http.delete('rest/GST/delete/'+_id).then(function(data){
	   deferred.resolve(data);
	  });
			return deferred.promise;
	}
	this.updateGstdata =function(obj,_id)
	{
	    var deferred=$q.defer();
	    var promise =$http.post('rest/GST/update/'+_id,obj).then(function(data){
		   deferred.resolve(data);
		  });
		return deferred.promise;
	}

})