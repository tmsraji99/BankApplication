app.service('reqCandidatesListservice',function($http,$q,$resource,$location,$window){
	this.getreqCandidateList=function(pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory){
			
			var deferred=$q.defer();
			var promise = $http.get("rest/candidateRequestByRecruiters/getAllcandidateRequestByRecruiters/"+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory).then(function(data){
				deferred.resolve(data);
				console.log(data);
			});
		return deferred.promise;
		}
	});