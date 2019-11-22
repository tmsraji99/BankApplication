app.controller('NoticePeriodController',function($scope, noticePeriodService,$location) { 
     $scope.addNoticePeriod=function(NoticePeriod){    	
    	 if(NoticePeriod == undefined  || NoticePeriod ==""){
    		 swal("Please Enter Notice Period in Days");
    		 return false;
    	 }
    	 else{
    	 noticePeriodService.addNoticePeriod(NoticePeriod);    	 
		$location.path('/ListOfNoticeperiod');	
}	}
});
