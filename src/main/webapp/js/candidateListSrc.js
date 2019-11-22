
app.service('selectedDetailsService',function($http,$q){
	this.selectedCandidate=function(id){
		var deffered=$q.defer();
		$http.get("rest/addCandidate/"+id).then(function(data){
			deffered.resolve(data);
		});
		return deffered.promise;
	}
})
app.service('updateCandidatesrvService',function($http,$q){
	this.updateCandidateList=function(id,list)
	{
		console.log("jyothi");
	var deferred1=$q.defer();

	$http.post("rest/addCandidate/"+id,list).then(function(data){
		deferred1.resolve(data);
	});
	}
});