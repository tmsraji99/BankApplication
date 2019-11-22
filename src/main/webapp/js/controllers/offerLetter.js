app.controller("offerLetter", function($scope, $location, $rootScope, $window,
		TypeOfProcessService, $http, $q, $timeout, $window) {
	 $scope.date = new Date();
	$scope.download = function() {
		html2canvas(document.getElementById('offerLetter'), {
			onrendered : function(canvas) {
				var data = canvas.toDataURL();
				var docDefinition = {
					content : [ {
						image : data,
						width : 500
					} ]
				};
				pdfMake.createPdf(docDefinition).download("OfferLetter.pdf");
			}
		});
	}
	$scope.backNavigation = function() {
		$location.path("/TypeOfProcess");
	}
	
	$scope.getOfferDetails = function(){
		var Cid = $window.localStorage.getItem("candidateid");
		$http.get("rest/addCandidate/getOfferDetails/"+Cid).success(function(response){
			$scope.candidateDetails = response.result;
			
			console.log($scope.candidateDetails);
		});

		}

});