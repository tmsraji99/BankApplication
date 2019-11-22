app.service("candidateSer",function($http,$q){	
var deferred=$q.defer();
	
	/*	$http.get("rest/addCandidate").
	  then(function(data){
		
		deferred.resolve(data);
		console.log(data);
	});*/
	
	this.getCandidateData=function()
	{
		return deferred.promise;
	}
	
	//////////////////////////
	
	
	this.getResume=function(id)
	{
		var deferredAA=$q.defer();
	
		$http.get("rest/addCandidate/getResume/"+id).
	     then(function(data){
		
		deferredAA.resolve(data);
		console.log("yyyyyyyyyyyyyyyy",data);
	});
		return deferredAA.promise;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*==================================================================*/
	
	
	var deferred1=$q.defer();
	this.setCandidateData=function(CandidateData){
		
		$http.post("rest/addCandidate",CandidateData).
		  then(function(data){
			
			deferred1.resolve(data);
			console.log(data);
		});
		return deferred1.promise;
			
	}
	/*==================================================================*/

	this.getDetails=function(pin){
		var deferred11=$q.defer();
		var config={
				headers:{
				'content type':'application/json',
				
				}
		}
	var result=$http.get('https://www.whizapi.com/api/v2/util/ui/in/indian-city-by-postal-code?pin='+pin+'&project-app-key=8umyz1c8dhcdusdgxmnqb75x').then(function(response){
			console.log("Country Data");
			deferred11.resolve(response.data);
			return deferred11.promise;
	})
		return result;
		
	}

this.getContactName=function(){
	
	var deferred4=$q.defer();
	
	$http.get("rest/client").
	  then(function(data){
		
		deferred4.resolve(data);
		console.log(data);	
	});
	
	return deferred4.promise;	
}
this.getPrimarySkill=function(){
	
	var deferred5=$q.defer();
	
	$http.get("rest/skill").
	  then(function(data){
		
		deferred5.resolve(data);
		console.log(data);
		
		
	});
	
	return deferred5.promise;
		
}
this.getCertificateNames=function(){
	
	var deferred6=$q.defer();
	
	$http.get("rest/certificates").
	  then(function(data){
		
		deferred6.resolve(data);
		console.log(data);
		
		
	});
	
	return deferred6.promise;
		
}

this.getContactType=function(){
	
	var deferred7=$q.defer();
	
	$http.get("rest/typeofhiring").
	  then(function(data){
		
		deferred7.resolve(data);
		console.log(data);
		
		
	});
	
	return deferred7.promise;
}

this.getEducation=function(){
	
	var deferred8=$q.defer();
	
	$http.get("rest/Qualification").
	  then(function(data){
		
		deferred8.resolve(data);
		console.log(data);
		
		
	});
	
	return deferred8.promise;
}

this.getNoticePeriod=function(){
	
	var deferred9=$q.defer();
	
	$http.get("rest/noticePeriod").
	  then(function(data){
		
		deferred9.resolve(data);
		console.log(data);
		
		
	});
	
	return deferred9.promise;
}


























});