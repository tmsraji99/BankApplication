app.service("bankdetailsservice",function($http,$q){
var deferred=$q.defer();
	
	this.addbankDetailsData =function(obj)
	{
	  var deferred=$q.defer();
	  var promise =$http.post('rest/bankDetails/addBank',obj).then(function(data){
			deferred.resolve(data);
		});
		return deferred.promise;
	}
   this.getbankdetailsData =function()
	{
	  var deferred=$q.defer();
	  var promise =$http.get('rest/bankDetails/getAll').then(function(data){
			deferred.resolve(data);
		});
		return deferred.promise;
	}
   this.deletebankDetailsData =function(_id)
	{
    	var deferred=$q.defer();
		var promise =$http.delete('rest/bankDetails/delete/'+_id).then(function(data){
			deferred.resolve(data);
		});
		 return deferred.promise;
	}
	this.getbyidbankDetailsData =function(_id)
	{
		var deferred=$q.defer();
		var promise =$http.get('rest/bankDetails/getbyid/'+_id).then(function(data){
			deferred.resolve(data);
		});
		 return deferred.promise;
	}
	this.updatebankDetailsData =function(_id,obj)
	{
		var deferred=$q.defer();
		var promise =$http.post('rest/bankDetails/updateBank/'+_id,obj).then(function(data){
			deferred.resolve(data);
		});
		 return deferred.promise;
	}

})