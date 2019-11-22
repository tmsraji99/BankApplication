app.service('candidatemaplistservices', function($http,$q) {
this.getCandidateData=function(role,loginId,pageIndex,pageSizeSelected,sortingOrder,sortingField,searchtext,searchcategory){
 var deffered3=$q.defer();
 $http.get("rest/candidateReqMapping/getCandidatesList/"+role+"/"+loginId+"/"+pageIndex+"/"+pageSizeSelected+'?sortingOrder='+sortingOrder+'&sortingField='+sortingField+'&searchText='+searchtext+'&searchField='+searchcategory).then(function(data){
  deffered3.resolve(data);
 });
 return deffered3.promise;
}

// this.getCandidate=function(){
 // var deffered31=$q.defer();
 // $http.get("/rpo-0.3.0-SNAPSHOT/rest/addCandidate").then(function(data){
  // deffered31.resolve(data);
 // });
 // return deffered31.promise;
// }

});
app.service('selectedDetailsService',function($http,$q){
this.selectedCandidate=function(id){
 var deffered=$q.defer();
 
 $http.get("rest/addCandidate/"+id).then(function(data){
  deffered.resolve(data);
 });
 return deffered.promise;
}
})
app.service('updateCandidatesrvService',function($http,$q){
this.updateCandidateList=function(id,list)
{
 /*console.log("jyothiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");*/
 console.log(id);
 console.log(list);
var deferred1=$q.defer();

$http.post("rest/addCandidate/updating/"+id,list).then(function(data){
 deferred1.resolve(data);
});
}
});