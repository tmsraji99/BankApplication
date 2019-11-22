app.service("typeOFAddressService",function($http,$q){	
this.typeAddressSave=function(value){
	var deferred=$q.defer();
	$http.post('rest/typeofaddress',value).then(function(data) {
			deferred.resolve(data);
			console.log(data);
	});
}
this.getAddressData=function(){
	var deferred1=$q.defer();
	
	$http({                                                                                           
 		   method : 'GET',
        url : 'rest/typeofaddress',                    
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
      url:'rest/typeofaddress/'+id,
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
	var result=$http.post('rest/typeofaddress/'+id, update).then(function(response){
		deferred.resolve(response);
		console.log(response);
	});	
}
});