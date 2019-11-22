app.service('skillService', function($http,$q) {
	
	
	this.addSkill=function(skillName)
	{
	var deferred=$q.defer();
	console.log(skillName);

	$http.post("rest/skill",skillName).then(function(data){

		deferred.resolve(data);
		console.log(data);
	});
	
	}
});