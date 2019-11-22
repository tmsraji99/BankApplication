
app.service('clientService1',function($http,$q,$rootScope){
	this.clientDisplay=function(pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory){
		var role= localStorage.getItem('role');
		var id= localStorage.getItem('usid');
		var deferred=$q.defer();
		var config={
				headers:{
				'content type':'application/json',
				}
		}
		
		var result=$http.get('rest/client/getAllClientsByRole/'+id+'/'+role+'/'+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory).then(function(response){
			deferred.resolve(response);
			 console.log(response.data);
			 $rootScope.client1=response.data.result;
			 return deferred.promise;
		});
		return result;
	}
	
	
	this.search=function(client)
	{
		console.log("hello");
	var deffer=$q.defer();
	var config={
			headers:{
			'content type':'application/json',
			}
	}
	var result=$http.get("rest/client/"+client).then(function(data){
		defer.resolve(data);
		return defer.promise;
	})
	return result;
	console.log(result);
	}
});
