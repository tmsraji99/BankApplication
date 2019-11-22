app.controller('listNoticePeriodController', function($scope,$window,
		listNoticePeriodService, editNoticePeriodService) {
	$scope.noticePeriodNew = {};
	$scope.clickedUser = {};
	//$scope.noticePeriods = listNoticePeriodService.query();	
	$scope.notice=function(){
		debugger;
	var promise = listNoticePeriodService.getnoticeperiod();
	promise.then(function(response) {
				$scope.noticePeriods = response.data.result;
			});
	}
	$scope.notice();			
	//$scope.noticePeriod = $scope.noticePeriods.noticePeriod;

	$scope.id;
	$scope.pushnoticePeriod = function(items) {
		$scope.clickedUser = items;
	}

	$scope.editNoticePeriod = function() {
		debugger;
		var editNP = {
			noticePeriod : $scope.clickedUser.noticePeriod
		}
		editNoticePeriodService.editNP($scope.clickedUser.id, editNP);		
		 $window.location.reload();
		$scope.notice();
	}
});
