app.controller('AddroleController',function($scope,roleService,$location) {
     console.log("Addrole Controller");
     $scope.addroleSet=function(roleName){
        console.log(roleName);
        console.log("roles going to services"); 
    	 roleService.addRole(roleName);
		$location.path('/listrole');
	}
  });