app.controller('AddqualificationController',function($scope,qualificationService,$location) {
     $scope.addqualification=function(qualificationName){
        if(qualificationName == undefined || qualificationName == "")
        	{
        	swal("Enter Qualification");
        	return false;
        	}
        else{
        qualificationService.addQualification(qualificationName);
		$location.path('/listqualification');
	}
     }
  });