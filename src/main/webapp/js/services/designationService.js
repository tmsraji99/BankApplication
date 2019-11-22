app.service("designationService",function($http,$q,$resource){
	//return $resource('rest/designation',{})
	
	
	this.getDesignationList=function()
	{
		console.log("varun");
		var deferred11=$q.defer();
	    

	    $http.get("rest/designation").then(function(data){
		deferred11.resolve(data);
		console.log(data.data)
		
	});
	
     return deferred11.promise;
	
}
	
	
	
	
	
	
	
	
	
});
app.service("designService",function($http,$q,$location){	
this.sendData=function(value){
	var deferred=$q.defer();
	$http.post('rest/designation',value).then(function(data) {
			deferred.resolve(data);
			console.log(data);
		
	});
	
	
	
}
 this.editdesignation=function(val,id){
    	var deferred=$q.defer();
    	$http.post("rest/designation/"+id,val).then(function(data){
    		deferred.resolve(data);
    		console.log(data);
    	});
    }
});