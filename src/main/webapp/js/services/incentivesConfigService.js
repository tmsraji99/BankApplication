app.service("incentivesConfigService",function($http,$q,$rootScope){
var deferred=$q.defer();
	
	this.addincentivesData =function(obj)
	{
		var deferred=$q.defer();
		var promise = $http.post('rest/incentiveConfig/save',obj).then(function(data){
		  deferred.resolve(data);
		   });
		 return deferred.promise;	
	}
	this.getincentivesData =function(pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory)
	{
		var deferred=$q.defer();
		var promise = $http.get('rest/incentiveConfig/getAll').then(function(data){
		  deferred.resolve(data);
		   });
		 return deferred.promise;		
	}
	this.updateIncentivesData =function(id,Obj)
	{
		var deferred=$q.defer();
		var promise = $http.post('rest/incentiveConfig/update/'+id,Obj).then(function(data){
		  deferred.resolve(data);
		   });
		 return deferred.promise;
	}
	this.getByIdIncentivesData =function(id)
	{
	    var deferred=$q.defer();
		var promise = $http.get('rest/incentiveConfig/get/'+id).then(function(data){
		  deferred.resolve(data);
		   });
		 return deferred.promise;
	}
	this.deleteincentivesData =function(id)
	{
	 var deferred=$q.defer();
		var promise = $http.delete('rest/incentiveConfig/delete/'+id).then(function(data){
		  deferred.resolve(data);
		   });
		 return deferred.promise;
	}

})