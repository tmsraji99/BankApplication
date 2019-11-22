app.controller("addCertificateController",function($scope,addCertificateService, $location) { 
		 $scope.addCerti=function() {	
			list = {
					certificationName : $scope.certificationName				
			};
			console.log(list);
			addCertificateService.addCertificateData(list);
			$location.path("/certificateList");
		 }
})