app.service("addressService",function($http,$q) {	
	this.getAddressType=function(){
		var deferred=$q.defer();	
		$http({                                                                                           
	 		   method : 'GET',
	        url : 'rest/typeofaddress',                    
	        headers: {
	            'Content-Type': 'application/json'
	        }
	        }).then(function(response){
			deferred.resolve(response);
			console.log(response);		
		});	
		return deferred.promise;
	}
	
	this.addAddressValues=function(address){
		var deferred=$q.defer();
		var result=$http.post('rest/addressdetails',address).then(function(response){
			deferred.resolve(response);
			console.log(response);
		});	
	}
	
	this.getContactAddressType=function(id){
		var deferred=$q.defer();	
		$http({                                                                                           
	 		   method : 'GET',
	        url : 'rest/Contact_address_map/'+id,                    
	        headers: {
	            'Content-Type': 'application/json'
	        }
	        }).then(function(response){
			deferred.resolve(response);
			console.log(response);		
		});	
		return deferred.promise;
	}
	
	this.getTypeOfAddress=function(id){
		var deferred=$q.defer();	
		$http({                                                                                           
	 		   method : 'GET',
	        url : 'rest/addressdetails/'+id,                    
	        headers: {
	            'Content-Type': 'application/json'
	        }
	        }).then(function(response){
			deferred.resolve(response);
			console.log(response);		
		});	
		return deferred.promise;
	}
	
	this.addAddressEditValues=function(editvalues, id){
		var deferred=$q.defer();
		var result=$http.post('rest/addressdetails/'+id, editvalues).then(function(response){
			deferred.resolve(response);
			console.log(response);
		});	
	}
	
});
	

