app.controller("poCtrl",function($scope){
	
	$scope.open1 = function() {
	    $scope.popup1.opened = true;
	  };
	  
	  $scope.popup1 = {
	    opened: false
	  };

});