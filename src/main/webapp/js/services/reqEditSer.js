app.service("reqEditSer",function($http,$q){

	var deferred1=$q.defer();
	
	this.getEditData=function(id){
		
		console.log(id);
		
		var url="rest/Bdmrequire/"+id;
		
		
		$http.get(url).
		  then(function(data){
			
			deferred1.resolve(data);
			console.log(data);
			
			
		});
		
		return deferred1.promise;
			
	}
	
	this.getEditableData=function(editId)
	{
	var deferred=$q.defer();
	
    console.log(editId);
	var url="rest/Bdmrequire/"+editId;
	console.log(url);
	$http.get(url).then(function(data){
		
		deferred.resolve(data);
		console.log(data);
		
	});
	return deferred.promise;
	
	}
	
	
	
	this.addBdmdtls=function(reqdtls,editId)
	{
	var deferred=$q.defer();
	console.log(JSON.stringify(reqdtls));
    console.log(editId);
	var url="rest/Bdmrequire/"+editId;
	console.log(url);
	$http.post(url,reqdtls).then(function(data){
		 
		deferred.resolve(data);
		console.log(data);
	});
	
	}
	
	
	
	

	this.getCertificateNames = function() {

		var deferred6 = $q.defer();

		$http.get("rest/certificates").then(function(data) {

			deferred6.resolve(data);
			console.log(data);

		});

		return deferred6.promise;

	}

	
	this.getClientNames555= function() {

		var deferred55 = $q.defer();

		$http.get("rest/client").then(function(data) {

			deferred55.resolve(data);
			console.log(data);

		});

		return deferred55.promise;
	}
	
	
	
	
	
	this.getClientNames = function() {

		var deferred6 = $q.defer();

		$http.get("rest/Bdmrequire").then(function(data) {

			deferred6.resolve(data);
			console.log(data);

		});

		return deferred6.promise;
	}

	// getCCmapdetails
	/*this.getCCmapdetails = function() {

		var deferred10 = $q.defer();

		$http.get("/rpo-0.3.0-SNAPSHOT/rest/clientcontactmap").then(
				function(data) {

					deferred10.resolve(data);
					console.log("inside getCCMAPDETAILS")
					console.log(data);

				});

		return deferred10.promise;

	}*/
/*	this.contactm = function(id) {

		var deferred12 = $q.defer();

		$http.get("/rpo-0.3.0-SNAPSHOT/rest/addClientContact/" + id).then(
				function(data) {

					deferred12.resolve(data);
					console.log("inside getCCMAPDETAILS")
					console.log(data);

				});

		return deferred12.promise;

	}*/
	
	
	
	

	this.clientcontactm = function(id) {

		var deferred11 = $q.defer();

		$http.get("rest/addClientContact/findContactByClientId/"+id).then(
				function(data) {

					deferred11.resolve(data);
					console.log("inside clientcontactmcriteria")
					console.log(data);

				});

		return deferred11.promise;

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
})