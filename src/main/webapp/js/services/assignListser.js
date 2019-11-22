app.service('assignListwrkSer', function($http, $q,$rootScope) {
	this.assignfunservice = function(pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory) {
       var UserId = localStorage.getItem('usid');
       var userRole = localStorage.getItem('role');
		var deferred11 = $q.defer();
	var result=	$http.get('rest/allocaterequirment/getAssinedRequirementsByBdmId/'+UserId+'/'+userRole+'/'+pageIndex+'/'+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory)
		.then(function(data) {
					deferred11.resolve(data);
					console.log("inside clientcontactmcriteria")
					console.log(data);
					$rootScope.records =data.data.result;
					return deferred11.promise;
				});	
		return result
	}
});
app.service('updateAssignService', function($http, $q) {
	this.editAssignments = function(id, list) {
		console.log("mounika");
		console.log(list);
		var deferred1 = $q.defer();

		$http.post("rest/allocaterequirment/"+id,list)
				.then(function(data) {
					deferred1.resolve(data);
				});
	}
});
app.service('deleteAssignService', function($http, $q) {
	this.deleteAssignments = function(id, list) {
		console.log("deleted");
		console.log(list);
		var deferred1 = $q.defer();
		$http.delete("rest/allocaterequirment/"+id,list)
				.then(function(data) {
					deferred1.resolve(data);
				});
	}
});
