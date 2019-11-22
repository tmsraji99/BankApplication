app.service('assignService', function($http, $q) {
	this.addWrk = function(assignwrk) {
		var deferred = $q.defer();
		console.log("rest/allocaterequirment", assignwrk)
				.then(function(data) {
					deferred.resolve(data);
					console.log(data);
				});
	}

});