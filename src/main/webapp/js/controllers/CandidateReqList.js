app.controller('candiReqList', function($scope, CandidatelistServices,
		selectedDetailsService) {

	var promise = CandidatelistServices.getCandidate();

	promise.then(function(data) {
		$scope.candidatelist = data.data.result;
		console.log($scope.candidatelist);
	});
	$scope.reqDescription = function(id) {
		var promise = selectedDetailsService.selectedCandidate(id);
		promise.then(function(data) {
			$scope.candiDetails = data.data.result;
			console.log($scope.candiDetails);
			$scope.ski = $scope.candiDetails.skills;
			$scope.certi = $scope.candiDetails.certificates;
			console.log($scope.ski);
			document.getElementById('candiProfile').style.display = "block";
		})

	}
})