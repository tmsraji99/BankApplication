app.service("costtypeService", function($http, $q) {

	this.sendCostData = function(cvalue) {
		var deferred = $q.defer();
		$http.post('rest/costtype',cvalue).then(function(data) {
			deferred.resolve(data);
			console.log(data);
		});
	}
});



app.service("costService", function($resource,$http,$location,$q) {
	//return $resource('/rpo-0.3.0-SNAPSHOT/rest/costtype',{})
	
	this.getcostExistingData=function(){
		var deferred=$q.defer();
	      $http({
	    	  method:'GET',
	        url:'rest/costtype',
	        headers:{
				'content-type':'application/json'
			},
	      }).then(function(response){
	    	  deferred.resolve(response);
	      });
	      return deferred.promise;
	}
	//for edit
	this.edit=function(val,id){
		var deferred=$q.defer();
    	$http.post("/rpo-0.3.0-SNAPSHOT/rest/costtype/"+id,val).then(function(data){
    		deferred.resolve(data);
    		console.log(data);
		});
	}
	
    	

});