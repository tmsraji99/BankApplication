app.service('bdmService', function($resource) {
	return $resource('rest/Bdmrequire', {});
});
app.service('bdmService', function($http, $q) {
	this.addBdmdtls = function(reqdtls) {
		var deferred = $q.defer();
		$http.post("rest/Bdmrequire", reqdtls).then(
				function(data) {					
					deferred.resolve(data);
					console.log(data.data.status);
					if(data.data.status="StatusSuccess"){
						alert("Added Successfully")
					}
					else 
						alert("internal server ERROR")
				});
	}	
	this.reqdertails = function() {
		var deferred61 = $q.defer();
		$http.get("rest/Bdmrequire").then(function(data) {
			deferred61.resolve(data);
			console.log(data);
		});
		return deferred61.promise;
	}	
	this.getCertificateNames = function() {
		var deferred6 = $q.defer();
		$http.get("rest/certificates").then(function(data) {
			deferred6.resolve(data);
			console.log(data);
		});
	return deferred6.promise;

	}
	var userrole= localStorage.getItem('role');
	var userid= localStorage.getItem('usid');
	this.getClientNames555= function() {
		var deferred55 = $q.defer();
		$http.get('rest/client/getClientsByRole/'+userid+'/'+userrole).then(function(data) {
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
					console.log(data);
				});
		return deferred11.promise;
	},
	this.clientcontactactive = function(id,role,userId) {
		var deferred11 = $q.defer();
		$http.get("rest/addClientContact/findActiveContactByClientId/"+id +"/"+role+"/"+userId).then(
				function(data) {
					deferred11.resolve(data);
					console.log(data);
				});
		return deferred11.promise;
	}
});
	app.service('bdmReqService',function($http,$q){
		
		this.getClientRequirement=function(id){
			var deferred=$q.defer();
			$http.get("rest/Bdmrequire/"+id).then(function(data){
				deferred.resolve(data);
				console.log(data);
		})
		return deferred.promise;
		}
		
		this.getClientRelatedRequirements=function(id){
			var deferred=$q.defer();
			$http.get("rest/Bdmrequire/clientreq/"+id).then(function(data){
				deferred.resolve(data);
				console.log(data);
		})
		return deferred.promise;
		}
		})


