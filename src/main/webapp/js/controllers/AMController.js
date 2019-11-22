app.controller("AMController",function($scope,AMServices, $location,$timeout) { 
		var getfun1 = function(){
		  var promise=AMServices.getData();
	 
	       promise.then(function(data){
	   
		       $scope.list=data.data;
		console.log($scope.list);
	           });
	      }
		  getfun1();
		  
		  $timeout(function ()
        {
		  getfun1();
        }, 2000);
})