app.service("ListReqSer",function($http,$q,$rootScope){

var deferred=$q.defer();

	this.getListReqData=function(pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory)
	{
		var role=localStorage.getItem('role');
		var id = localStorage.getItem('usid');
			
		debugger;
	var result = $http.get('rest/Bdmrequire/getBdmReqByRole/'+id+'/'+role+'/'+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory)
		.then(function(data){		
		deferred.resolve(data);
		console.log(data);
	/*	$rootScope.ListReqData1 =data.data.result;*/
		return deferred.promise;
	});
		return result
	}
	/*this.getSearchData=function(role,loginId,searchtext,searchcategory,pageIndex,pageSizeSelected){
		var role=sessionStorage.getItem('userRole');
		var loginId=sessionStorage.getItem('UserId');
		var deffered3=$q.defer();
		$http.get("rest/Bdmrequire/searchRequirements/"+role+"/"+loginId+"/"+searchtext+"/"+searchcategory+"/"+pageIndex+"/"+pageSizeSelected).then(function(data){
			deffered3.resolve(data);			
		});
		return deffered3.promise;
	}*/
})