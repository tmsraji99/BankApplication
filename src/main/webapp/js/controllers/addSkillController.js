app.controller('SkillController',function($scope,skillService,$location) {
     console.log("skills Controller");   
     $scope.addSkillSet=function(skillName){
    	 console.log(skillName);
    	 console.log("skills going to services"); 
	     skillService.addSkill(skillName);
		$location.path('/listSkill');
	
}	
});
