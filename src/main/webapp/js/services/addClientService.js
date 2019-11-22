
app.service('clientService',function($http,$q,$resource){
	
	//return $resource("rest/client",{
	 //});
	 this.getCa=function(){
	var deffer11=$q.defer();
	$http.get("rest/client").then(function(data){	
		deffer11.resolve(data);
	});
		return deffer11.promise;
		};
			
	this.editClient=function(updateData,id){
		
		var deferred1=$q.defer();
		var config={
				headers:{
				'content type':'application/json',			
				}
		}
		var result=$http.post('rest/client/'+id,updateData).then(function(response){
			deferred1.resolve(response.statusText);
			 return deferred1.promise;
		});
		return result;		
	}
	this.getClientType=function(){
	var deffer=$q.defer();
	$http.get("rest/customerType").then(function(data){		
		deffer.resolve(data);
	});
		return deffer.promise;
		};
/*	this.getClientType=function(){
			var deffer=$q.defer();
			$http.get("rest/customerType").then(function(data){
				
				deffer.resolve(data);
				console.log("hello");
				console.log(data);
			});
				return deffer.promise;
				};*/
	this.addClient1=function(client){
		/*console.log(JSON.stringify(client));*/
		var deferred=$q.defer();
		/*var config={
				headers:{
				'content type':'application/json',
				
				}
		}*/
		/*var data=JSON.parse(client);*/
	var result=$http.post('rest/client',client).then(function(response){
			deferred.resolve(response.statusText);
			return deferred.promise;
	})
		
		return result;
	}
	/*this.getDetails=function(pin){
		var deferred2=$q.defer();
		var config={
				headers:{
				'content type':'application/json',
				
				}
		}
	var result=$http.get('https://www.whizapi.com/api/v2/util/ui/in/indian-city-by-postal-code?pin='+pin+'&project-app-key=8umyz1c8dhcdusdgxmnqb75x').then(function(response){
			deferred2.resolve(response.data);
			return deferred2.promise;
	})
		return result;
		
	}
		this.getDetails1=function(pin){
		var deferred2=$q.defer();
		var config={
				headers:{
				'content type':'application/json',
				
				}
		}
	var result=$http.get('https://www.whizapi.com/api/v2/util/ui/in/indian-city-by-postal-code?pin='+pin+'&project-app-key=8umyz1c8dhcdusdgxmnqb75x').then(function(response){
			deferred2.resolve(response.data);
			return deferred2.promise;
	})
		return result;
		
	}*/
});
