app.controller("ListCertificateController",function($scope,addCertificateService,$timeout,$window) { 
	var promise=null;
	var fun1 = function() {
		CertiList={}
		var promise = addCertificateService.getCertificateData();
		promise.then(function(response) {
			console.log(response);
			$scope.CertiList = response.data.result;
			if (response.data.totalRecords == null){
				$scope.noData = "No Certificates Found";
			}
		});
	};	
	fun1();
	$timeout(fun1);
	
	$scope.popupdata=function(id){
		console.log(id);
		var promise1=addCertificateService.getEditData(id);
		promise1.then(function(response){
			console.log(response.data);
	        $scope.editDetails=response.data.result;
	        console.log($scope.editDetails);	        
	        $scope.editCertificate=$scope.editDetails.certificationName;
		});
	}
	

	 $scope.UpdateCerti=function(id) {	
		update = {
				certificationName : $scope.editCertificate				
		};
		console.log(update);
		addCertificateService.addUpdatedData(id,update);
		//$window.location.reload();
		//$scope.getList();
		fun1();
		$timeout(fun1);
	 }
	
	
	
	//$scope.getList();

});
