app.service('listroleservice',function($resource){
	return $resource('rest/role', {})
	
		
});
app.service('editroleService',function($http,$q){
	this.editrole=function(id,roleName)
	{
	var deferred=$q.defer();
	console.log(id);
	console.log(roleName);
	$http.post("rest/role/"+id,roleName).then(function(data){
		deferred.resolve(data);
	});
	
	}
})









