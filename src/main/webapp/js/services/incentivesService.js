app.service("incentivesService",function($http,$q,$rootScope){
var deferred=$q.defer();

	this.incentivesData =function(pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory)
	{
		var role= localStorage.getItem('role');
		var loginid= localStorage.getItem('usid');			
		
	var result = $http.get('rest/incentive/getIncentivesList/'+role+'/'+loginid+'/'+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory)
		.then(function(data){		
		deferred.resolve(data);
		console.log(data);
		$rootScope.incentivesData1 =data.data.result;
		return deferred.promise;
	});
		return result
	}


})