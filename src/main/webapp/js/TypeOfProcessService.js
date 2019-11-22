app.service("TypeOfProcessService", function($http, $q) {
	this.getprocessData = function() {

		var deferred = $q.defer();
		$http({
			method : 'GET',
			url : 'rest/addstatus',
			headers : {
				'content-type' : 'application/json'
			},
		}).then(function(response) {
			deferred.resolve(response);
		});
		return deferred.promise;
	}
	// for getting candidate name
	this.getcandidateData = function(a) {
		var deferred2 = $q.defer();
		$http({
			method : 'GET',
			url : 'rest/addCandidate/' + a,
			headers : {
				'content-type' : 'application/json'
			},
		}).then(function(response) {
			deferred2.resolve(response);
		});
		return deferred2.promise;
	}
	// interview type

	this.getinterviewData = function() {
		var deferred3 = $q.defer();
		$http({
			method : 'GET',
			url : 'rest/modeofInterview',
			headers : {
				'content-type' : 'application/json'
			},
		}).then(function(response) {
			deferred3.resolve(response);
		});
		return deferred3.promise;
	}
	// send interview schedule

	this.sendinterviewData = function(interviewvalues) {
		$http({
			method : 'POST',
			url : 'rest/ptype',
			headers : {
				'content-type' : 'application/json'
			},
			data : interviewvalues
		}).then(function(response) {
		})
	}, function(error) {
		console.log(error);

	}
	// approved
	this.sendapprovedData = function(approvedvalues, id) {
		$http({
			method : 'POST',
			url : 'rest/addCandidate/update/' + id,
			headers : {
				'content-type' : 'application/json'
			},
			data : approvedvalues
		}).then(function(response) {
		})
	}, function(error) {
		console.log(error);

	}

	// submitted
	this.sendsubmittedData = function(submittedvalues, id) {
		$http({
			method : 'POST',
			url : 'rest/addCandidate/update/' + id,
			headers : {
				'content-type' : 'application/json'
			},
			data : submittedvalues
		}).then(function(response) {
		})
	}, function(error) {
		console.log(error);

	}

	// pincode api

	/*
	 * this.getDetails=function(pin){ var deferred1=$q.defer(); var config={
	 * headers:{ 'content type':'application/json',
	 *  } } var
	 * result=$http.get('https://www.whizapi.com/api/v2/util/ui/in/indian-city-by-postal-code?pin='+pin+'&project-app-key=8umyz1c8dhcdusdgxmnqb75x').then(function(response){
	 * deferred1.resolve(response.data); return deferred1.promise; }) return
	 * result;
	 *  }
	 */

	// list
	this.getprocesslistData = function() {
		var deferred4 = $q.defer();
		$http({
			method : 'GET',
			url : 'rest/ptype',
			headers : {
				'content-type' : 'application/json'
			},
		}).then(function(response) {
			deferred4.resolve(response);
		});
		return deferred4.promise;
	}

	// feedbackList

	this.getprocessFeddback = function() {
		var deferred12 = $q.defer();
		$http({
			method : 'GET',
			url : '/rpo/rest/interviewfeedback',
			headers : {
				'content-type' : 'application/json'
			},
		}).then(function(response) {
			deferred12.resolve(response);
		});
		return deferred12.promise;
	}

	// mounika
	this.getaddress = function(x) {
		var deferred5 = $q.defer();
		$http({
			method : 'GET',
			url : 'rest/Contact_address_map/' + x,
			headers : {
				'content-type' : 'application/json'
			},
		}).then(function(response) {
			deferred5.resolve(response);
		});
		return deferred5.promise;
	}

	this.changestatusfun = function(candidateid, status) {
		var deferred = $q.defer();
		$http.post("rest/addCandidate/" + candidateid, status).then(
				function(data) {
					deferred.resolve(data);
				});

	}

	this.sendFeedbackData = function(feedbackdata) {
		$http({
			method : 'POST',
			url : '/rpo/rest/interviewfeedback',
			headers : {
				'content-type' : 'application/json'
			},
			data : feedbackdata
		}).then(function(response) {
		})
	}, function(error) {
		console.log(error);
	}

});
