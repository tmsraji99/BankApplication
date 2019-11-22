app.controller('listSkillControllers',function($scope, listSkillService,editSkillService,$location,$timeout,$window) {
    console.log("list skills Controller");
	$scope.skillNew={};
	$scope.clickedUser={};
	var promise = null;
   /* var promise = listSkillService.getskill();
		promise.then(function(response){
	    		$scope.skillNames=response.data.result;
	    			    	});	*/
	$scope.skillfun=function(){
		var promise = listSkillService.getskill();
		promise.then(function(response){
	    		$scope.skillNames=response.data.result;	 
	    		if(response.data.status == "DataIsEmpty"){
					$scope.noData = "No Skills Found";
				}
		});
			}
	$scope.skillfun();
	$scope.id;
    $scope.pushSkill=function(skills){
    	$scope.clickedUser=skills;  	
    }

    $scope.editSkill12=function(){
    	promise;   
    	editSkill={
    			skillName:$scope.clickedUser.skillName,
				flag:$scope.clickedUser.flag			
    	}
    editSkillService.editSkill($scope.clickedUser.id,editSkill);    			
	$scope.skillfun();
    }
    $scope.changeStatus=function(id,skill){
    	  editSkillService.statusChange(id,skill);
    }
    $scope.skillfun();
});
	
