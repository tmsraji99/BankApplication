app.service('noticePeriodService', function($http,$q) {
	
	
	this.addNoticePeriod=function(NoticePeriod)
	{
	var deferred=$q.defer();
	console.log(NoticePeriod);
	$http.post("rest/noticePeriod",NoticePeriod).then(function(data){
		deferred.resolve(data);
		console.log(data);
	});
	
	}
});