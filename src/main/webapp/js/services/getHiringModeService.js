/*app.service('getHiringModeService',function($resource){
	
	return $resource('rest/typeofhiring', {})
	
		
});
app.service('editHiringModeService',function($http,$q){
	this.editSkill=function(id,HiringModeName)
	{
	var deferred=$q.defer();
	console.log(id);
	console.log(HiringModeName);
	$http.post("http://localhost:8086/rpo-0.3.0-SNAPSHOT/rest/typeofhiring/"+id,HiringModeName).then(function(data){
		deferred.resolve(data);
	});
	
	}
})

*/

app.service('getHiringModeService', function($http, $q){
	this.getModeDetails=function(){
		var deferred=$q.defer();
		    $http({                                                                                           
	 		   method : 'GET',
	 		  url : 'rest/typeofhiring',                    
            headers: {
                'content-Type': 'application/json'
            }   
        }).then(function(response) {
        	deferred.resolve(response);
            console.log(response.data);      
        });
		    return deferred.promise;
		 }
	
	
	this.editHiringMode=function(id){
	
		var deferred1=$q.defer();
		    $http({                                                                                           
	 		   method : 'GET',
            url : 'rest/typeofhiring/'+id,                    
            headers: {
                'content-Type': 'application/json'
            } , 
		    
        }).then(function(response) {
        
        	deferred1.resolve(response);
            console.log(response.data);      
        });
		    return deferred1.promise;
	}	
	
	
	this.updateHiringMode=function(id, update)
	{
	var deferred2=$q.defer();
	console.log(update);
	console.log(id);

	
	$http.post('rest/typeofhiring/'+id, update).then(function(data){
		deferred2.resolve(data);
		console.log(data);
	});
	
	}

		this.deleteHiringMode=function(items){
			var deferred=$q.defer();
			console.log(items);
			alert("now the status changed to delete");
			$http.delete('rest/typeofhiring/' + items.id, items).then(function(data){
				
				alert("now the status changed to delete");
				deferred.resolve(data);
			});
			return deferred.promise;
		}
	
	
});


