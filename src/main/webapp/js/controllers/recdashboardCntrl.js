app.controller("recdashboardCntrl", function($scope, $window,$location) 
{
			
	$scope.actionShow=function(){
		
		$window.localStorage.removeItem("addreqcandireqid");
		$window.localStorage.removeItem("addreqcandireqname");
		$window.localStorage.removeItem("addreqcandiclientname");
	}

});

