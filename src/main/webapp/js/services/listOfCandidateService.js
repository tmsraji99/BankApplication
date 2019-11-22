app.service('listofCandidateservice',function($http,$q,$resource,$location,$window){
	this.getCandidateList=function(usid,pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory){			
			var deferred=$q.defer();
			var promise = $http.get("rest/addCandidate/getAllCandidatesByAddedPerson/"+$window.localStorage.usid+'/'+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory).then(function(data){
				deferred.resolve(data);
			});
		return deferred.promise;
		}
//	return $resource('rest/Qualification', {})
	});
//      app.service('editqualiService',function($http,$q){
//	this.editqualification=function(id,qualificationName)
//	{
//	var deferred=$q.defer();
//      console.log(id);
//        console.log(qualificationName);
//	$http.post("rest/Qualification/"+id,qualificationName).then(function(data){
//	deferred.resolve(data);
//	});
//	}
//      
//	app.service('inactiveQualServices',function($http,$q){
//		this.inactiveStatus=function(id){
//			
//			var deferred1=$q.defer();
//			$http.delete("rest/Qualification/"+id).then(function(data){
//				deferred1.resolve(data);
//			});
//		return deferred1.promise;
//		}
//	});
//});