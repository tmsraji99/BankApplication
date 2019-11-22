app.service("addCertificateService",function($http,$q) {	
	this.addCertificateData=function(list){
		var deferred=$q.defer();
		var result=$http.post('rest/certificates',list).then(function(response){
			deferred.resolve(response);
			console.log(response);
		});	
	}
	
	
	this.getCertificateData=function(){
		var deferred1=$q.defer();
		
		$http({                                                                                           
	 		   method : 'GET',
	        url : 'rest/certificates',                    
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
          url:'rest/certificates/'+id,
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
		var result=$http.post('rest/certificates/'+id, update).then(function(response){
			deferred.resolve(response);
			console.log(response);
		});	
	}
	
});
