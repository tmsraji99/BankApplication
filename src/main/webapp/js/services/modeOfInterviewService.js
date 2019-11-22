app.service("addModeService",function($http,$q) {	
	this.addModeData=function(mode){
		var deferred=$q.defer();
			var config={
					headers:{
						'content-type':'application/json'
						}
					}

		var result=$http.post('rest/modeofInterview',mode, config).then(function(response){
			deferred.resolve(response);
			console.log(response);
			console.log("add interviewMode");
			return deferred.promise;
		});	
			return result; 
	}


	this.getModeData=function(){
		var deferred1=$q.defer();
		
		$http({                                                                                           
	 		   method : 'GET',
	        url : 'rest/modeofInterview',                    
	        headers: {
	            'Content-Type': 'application/json'
	        }
	        }).then(function(response){
			deferred1.resolve(response);
			console.log(response);
			
		});	
		return deferred1.promise;
	}	
	
	this.getEditData=function(id){
    	var deferred2=$q.defer();
        $http({
      	  method:'GET',
          url:'rest/modeofInterview/'+id,
          headers:{
  			'content-type':'application/json'
  		},
        }).then(function(response){
      	  deferred2.resolve(response);
        });
        return deferred2.promise;	
    }
	
	this.addUpdatedData=function(id, update){
		var deferred=$q.defer();
		var result=$http.post('rest/modeofInterview/'+id, update).then(function(response){
			deferred.resolve(response);
			console.log(response);
		});	
	}
	
});


