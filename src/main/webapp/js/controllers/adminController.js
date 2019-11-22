app.controller('AdminCtrl', function($scope,$filter,$rootScope,$http,bdmService){
//	  $scope.reportList = ['day','month','year'];
//	  $scope.reportMonthwiseList = ["January","Febuary","March","April","May","June","July","August","September","Octomber","November","December"];
//	
//	  console.log($scope.day,"yyyy/MM/dd");
//	  $scope.reportListChangeFun = function(){
//		  console.log($scope.reptList);
//	
//		  }
//	
//	  $scope.dateChanges = function(example){
//		  if(example){
//			  debugger;
////			  $scope.$parent.day = $filter('date')($scope.$parent.day, 'yyyy/MM/dd'); 
////			  console.log($scope.day);
//				if($scope.reptList == 'day'){
//					 $scope.example = $filter('date')(example, 'yyyy-MM-dd'); 
//						$scope.month='';
//						$scope.year='';
//					 $scope.reportType();
//				}else if($scope.reptList == 'month'){
//					 $scope.example=''
//					 $scope.month = $filter('date')(example, 'MM');
//					 $scope.year=$filter('date')(example, 'yyyy');
//					 $scope.reportType();
//				}else if($scope.reptList == 'year'){
//					$scope.month='';
//					$scope.example='';
//					$scope.year = example;
//					$scope.reportType();
//				}
//			  }
//	  }
//	  $scope.reportType= function(){
//		  var accessToken=$rootScope.accessToken;
//			/* var params={
//				       headers: {'X-Access-Token':accessToken }
//					   }*/
//		  $scope.obj= {
//					 "reportType":$scope.reptList,
//					 "input":" ",
//					 "role":"  ",
//					 "roleUserId":"  ",
//					 "date": $scope.example,
//					 "month":$scope.month,
//					 "year":$scope.year
//					 }
//			  $http.post('rest/barchat/getRecruiterWiseSubmissionsVsRejectionsByReportType',$scope.obj).then(function(res){
//				  console.log(res);
//			  })
//	  }
	 
	
	  
});
app.controller('genateTrkerCtrl', function($scope,$http,$rootScope,bdmService ){
	
    var accessToken=$rootScope.accessToken;
    /* var params={
               headers: {'X-Access-Token':accessToken }
               }*/
       $http.get('rest/addCandidate/getTrackerFields').then(function(res){
            $scope.albums = res.data.result;
            console.log($scope.albums);
            
       });
       
       $scope.toggleAll = function () {
    	   debugger;
	        if ($scope.isAllSelected) {
	            $scope.isAllSelected = true;
	        } else {
	            $scope.isAllSelected = false;
	        }
	        angular.forEach($scope.albums, function (item) {
	        	item.selected = $scope.isAllSelected;
	        });

	    };
	  /*  $scope.disableClick = function (disabled) { 
           $scope.isDisabled = !isDisabled; 
       }*/ 
       $scope.alb = [];
        $scope.change = function(value){
//            if(check){
//                $scope.alb.push(value);
//                $scope.obj = {
//                           "list":$scope.alb
//                        }
//            }else{
//                 $scope.alb.splice($scope.alb.indexOf(value), 1);
//            }
//        	 $scope.obj = {
//        			 "list":$scope.alb.push(value)
//        	 
//               }
        
        	
        };
        $scope.generateTracker = function(){
  		  debugger;
  	  var promise = bdmService.getClientNames555();
  		promise
  				.then(function(data) {
  					$scope.clientnamesbdm = data.data.result;
  					console.log($scope.clientnamesbdm);

  				});
  	  }
  	  $scope.generateTracker();
 	  $scope.getRequirements = function(clientName){
 		  debugger;
 		  $scope.clientName = clientName;
      	$http.get('rest/Bdmrequire/getdataByID/'+ $scope.clientName+'/Open').then(function(response){
	      	  $scope.statusres=response.data.status;
  		    if($scope.statusres=='OK'){
  		    	/*$scope.flag=true;*/
  		    $scope.ListReqData=response.data.result;
  		    $scope.totalrecords=response.data.totalRecords;	
  		    console.log($scope.ListReqData);
  		    }
  		    else{
  		    	   /*$scope.ListReqData=[];
  		    		$scope.flag=false;*/
  		    }
 			
	     		});	
  	  }
 	
        $scope.save = function(){
             $scope.albumNameArray = [];
             angular.forEach($scope.albums, function(album){
               if (album.selected) $scope.albumNameArray.push(album.columnName);
             });
             angular.forEach($scope.clientId, function(value, key){
             	if(value.id){
             		$scope.cid = value.id;
             	}
             	
             })
             var data={
            	 "list":$scope.albumNameArray
             }
            
               debugger;
                 $http.post('rest/addCandidate/getTrackerAsExcel'+'/'+$scope.clientName+'/'+ $scope.nameOfRequirement,data).then(function(res){
                   if (res.data.status == "StatusSuccess" ){
                	   swal("Successfully generated");
                   };
                	 $scope.download = res.data.res+".xlsx";
                 })
           }
     
       $scope.getByteSteam = function(){
           /*var params={
                   headers: {'X-Access-Token':accessToken }
                   }*/
           $http.get('rest/candidateReqMapping/getByteStream').success(function(res){
                  $scope.encodedData=res.result;
              }) 
       }
    
    });