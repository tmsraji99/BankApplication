app.service('listSkillService',function($http,$q){
	//return $resource('rest/skill', {})
	
	this.getskill=function()
	{
	var deferred=$q.defer();


	var promise = $http.get("rest/skill").then(function(data){

		deferred.resolve(data);
	});
	return deferred.promise;
	
	}
	
	
	
			
});
app.service('editSkillService',function($http,$q){
	
	this.editSkill=function(id,skillName)
	
	{
	var deferred=$q.defer();
	console.log(id);
	console.log(skillName);
	
	$http.post("rest/skill/"+id,skillName).then(function(data){

		deferred.resolve(data);
	});
	
	
	}
	this.statusChange=function(id){
		var deferred=$q.defer();
		console.log(id);
	
		$http.delete("rest/skill/"+id).then(function(data){
			alert("now the status changed to delete");
			deferred.resolve(data);
		});
		return deferred.promise;
	}
});





