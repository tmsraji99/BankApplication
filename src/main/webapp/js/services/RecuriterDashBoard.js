app.service('RecuriterDashBoard',function($http,$q,$window){

		this.getData1=function(){
			 var id=$window.localStorage.getItem("usid");
			var deferred=$q.defer();
			var promise = $http.get("rest/addClientContact/getDashboard/"+id).then(function(data){
				deferred.resolve(data);
			});
		return deferred.promise;
		}
	
	
	});
    
      
  		
  		
  