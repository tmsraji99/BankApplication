app.controller('listroleController',function($scope,listroleservice,editroleService,$location) {
    console.log("list role Controller");
	$scope.clickedUser={};
    $scope.rolelists = listroleservice.query();
   
    console.log($scope.rolelists);
    $scope.roleName= $scope.rolelists.roleName;
    
    $scope.id;
    $scope.pushrole=function(roles){
    	$scope.clickedUser=roles;
    }
	
	
    $scope.editSkill12=function(){
    	console.log($scope.clickedUser);
    	editrole={
    			roleName:$scope.clickedUser.roleName
    	}
    
    console.log($scope.clickedUser.id);
    	editroleService.editrole($scope.clickedUser.id,editrole);
   
    }
});
	








