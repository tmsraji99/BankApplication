app.controller("RecriterDashBoardCtrl",function($scope,$location,$window,$rootScope,$filter, RecuriterDashBoard,clientContactService,addressService, clientService,$http,userlistServices) { 
	 $scope.showtable = function() {
	        $scope.mytable = !$scope.mytable;
	    };
	    $scope.showtable2 = function() {
	        $scope.mytable2 = !$scope.mytable2;
	    };
	    $scope.showtable3 = function() {
	        $scope.mytable3 = !$scope.mytable3;
	    };
	    $scope.productivitytable1 = function() {
	        $scope.protable1 = !$scope.protable1;
	    };
	    $scope.productivitytable2 = function() {
	        $scope.protable2 = !$scope.protable2;
	    };
	    $scope.revenuetable1 = function() {
	        $scope.revenueview1 = !$scope.revenueview1;
	    };
	    $scope.revenuetable2 = function() {
	        $scope.revenueview2 = !$scope.revenueview2;
	    };
	    $scope.revenuetable3 = function() {
	        $scope.revenueview3 = !$scope.revenueview3;
	    };
	    $scope.revenuetable4 = function() {
	        $scope.revenueview4 = !$scope.revenueview4;
	    };
	    $scope.revenuetable5 = function() {
	        $scope.revenueview5 = !$scope.revenueview5;
	    };
	    $scope.revenuetable6 = function() {
	        $scope.revenueview6 = !$scope.revenueview6;
	    };
	    $scope.open1 = function() {			
		    $scope.popup1.opened = true;
		  };
		 $scope.popup1 = {
			opened: false
			};	
		 $scope.year='2019';

	/*	$scope.roleChange=function(roleSelect){
			
			$scope.roleSelect;
			  $http.get("rest/Reg/getUserNamesByRole"+'?role='+roleSelect).then(function(response){
			    	$scope.roleNamesList =  response.data.result;
			    	console.log($scope.roleNamesList);
			    	if (response.data.status=="OK"){
			    	swal('Api112 called Successfully')
			    	}
		       	});	
			  };*/
	    
	    //Quality reports starts//
//	    11.Nikhil Submissions  vs Rejections start //
	 /*   $scope.submByRecr=function(){
			
			var id = $window.localStorage.getItem("usid");
			var role = $window.localStorage.getItem("role");
		    $http.get("rest/submission/SubmissionBbyRecruiter/"+id).then(function(response){
		    	$scope.submisssionListRecr =  response.data.result;
		    	console.log($scope.submisssionListRecr);
		    	if (response.data.status=="OK"){
		    	swal('Api11 called Successfully')
		    	}
	       	});
		}
	    $scope.submByRecr();*/
//      11.Nikhil Submissions  vs Rejections end //
//	    12.Nikhil Submissions  vs Rejections start //
	  /*  $scope.closByRecr=function(){
			
			var id = $window.localStorage.getItem("usid");
			var role = $window.localStorage.getItem("role");
		    $http.get("rest/submission/ClosureByRecruiter/"+id).then(function(response){
		    	$scope.closuresbyRecrList =  response.data.result;
		    	console.log($scope.closuresbyRecrList);
		    	if (response.data.status=="OK"){
		    	swal('Api12 called Successfully')
		    	}
	       	});
		}
	    $scope.closByRecr();*/
//      12.Nikhil Submissions  vs Rejections end //
//	    13.Nikhil Submissions  vs Rejections start //
	   /* $scope.joinByRecr=function(){
			
			var id = $window.localStorage.getItem("usid");
			var role = $window.localStorage.getItem("role");
		    $http.get("rest/submission/JoiningsByRecruiter/"+id).then(function(response){
		    	$scope.joinListByRecr =  response.data.result;
		    	console.log($scope.joinListByRecr);
		    	if (response.data.status=="OK"){
		    	swal('Api13 called Successfully')
		    	}
	       	});
		}
	    $scope.joinByRecr();*/
//      13.Nikhil Submissions  vs Rejections end //
	    //1.Submissions vs Rejections starts //
	    
	    
	   
	    
	  //1.Submissions vs Rejections ends //
	    
	  //2. Submissions vs Closure starts//
		
		//2. Submissions vs Closure end //
	    
		//3.  Closing vs Joining start//
		
		//3. Closing vs Joining End //
		
	  //Quality reports ends//
		
		
		// productivity charts start //
		
		//1.  Requirement vs Submissions start//
	
		//1. Requirement vs Submissions End //
		
		
		//2.  Requirement vs BDM start//
		
		//2. Requirements vs BDM End //
		
		
		// productivity charts end //
		
		
		
		
		
		// Revenue Charts Starts //
		
		//1.Revenue by Category starts //
		
	
		
		//1.Revenue by Category end //
		
		
		//2.Revenue by recuiter start //
		
	
		
		
		//4.Revenue By BDM end //
		
		
		
		// Revenue Charts End //
		//Dynamic Starts By @Sirisha starts
	    $scope.reportList = ['day','month','year'];
		  $scope.reportMonthwiseList = ["January","Febuary","March","April","May","June","July","August","September","Octomber","November","December"];
		  $scope.reportListChangeFun = function(reptList){
			  $scope.reptList=reptList;
			  console.log($scope.reptList);
		
			  }
		  $scope.recuiterChangeFun = function(recuiterList){
			  $scope.recuiterList=recuiterList;
			  console.log($scope.recuiterList);
			  $scope.indrecruiters();
		  }
		
			$scope.indrecruiters = function() {
				$scope.id = $window.localStorage.getItem("usid");
				$scope.role = $window.localStorage.getItem("role");
				$http.get("rest/Reg/getRecruitersByReportingId/" + $scope.id)
						.success(function(response) {
							debugger;
							$scope.indrecruiters = response.result;
						});
			}
			
		//Dynamic Starts By @Sirisha ends
			//Dynamic Starts By @Sirisha
			$scope.getCharts = function(){
				debugger;
				var id = $window.localStorage.getItem("usid");
				var role = $window.localStorage.getItem("role");
				$http.get('rest/barchat/getTotalSubmissionsAndRejections/'+ id +'/'+ role).then(function(res){
					$scope.encodedData = res.data.encodedString;
					console.log(res)
				})
			}
			$scope.getCharts();
			$scope.subVsClosures = function(){
				debugger;
				var id = $window.localStorage.getItem("usid");
				var role = $window.localStorage.getItem("role");
				$http.get('rest/barchat/getSubmissionVsClosure/'+ id +'/'+ role).then(function(res){
					$scope.subvsclosures = res.data.encodedString;
					console.log(res)
				})
			}
			$scope.subVsClosures();
			$scope.closuresVsJoinings = function(){
				debugger;
				var id = $window.localStorage.getItem("usid");
				var role = $window.localStorage.getItem("role");
				$http.get('rest/barchat/getClosuresVsJoinings/'+ id +'/'+ role).then(function(res){
					$scope.closuresvsJoinings = res.data.encodedString;
					console.log(res)
				})
			}
			$scope.closuresVsJoinings();
			$scope.getSubmissionsVsRejectionsByReportType = function(reptList,day,month,year){
				debugger;
				var reportType =  $scope.reptList;
				var month =  $scope.month;
				var year =  $scope.year;
				var date=null;
				var id = $window.localStorage.getItem("usid");
				var role = $window.localStorage.getItem("role");
				if($scope.reptList == 'month'){
					month=document.getElementById('months').value;
					var month1 = month.slice(5,7);
					var year= month.slice(0,4);
					console.log(month1);
					console.log(year);
					if(month!=null){
					 month=document.getElementById('months').value;
					}
				}
				if($scope.reptList == 'day'){
				date=document.getElementById('datesss').value;
				console.log(date);
				if(day!=null){
				 date=document.getElementById('datesss').value;
				}
				}
				if($scope.recuiterList == 'recuriter' || $scope.recuiterList == 'lead'){
					selectedId=document.getElementById('recruitesList').value;
					console.log(selectedId);
					/*if(day!=null){
					 date=document.getElementById('datesss').value;
					}*/
					}
				if(selectedId  == undefined){
					var selectedId  = null;
				}
				debugger;
				var postedData ={				
						"reportType":reportType,
						 "date": date,
						 "month":month1,
						 "year":year,
						 "roleUserId": id,						
						"role" :  role,
						"selectedId": selectedId,
						/*"input": $scope.recuiterList*/
				}
				if($scope.recuiterList == undefined){
				$http.post('rest/barchat/getSubmissionsVsRejectionsByReportType/', postedData).then(function(res){
					if(res.data.status == "Exception"){
						swal("Internal Server Error");
					}
					$scope.encodedData = res.data.result.base64String;
					console.log(res)
				})
				$http.post('rest/barchat/getSubmissionsVsClosersByReportType/', postedData).then(function(res){
					if(res.data.status == "Exception"){
						swal("Internal Server Error");
					}
					$scope.subvsclosures = res.data.result.base64String;
					console.log(res)
				})
				$http.post('rest/barchat/getClosersVsJoiningByReportType/', postedData).then(function(res){
					if(res.data.status == "Exception"){
						swal("Internal Server Error");
					}
					$scope.closuresvsJoinings = res.data.result.base64String;
					console.log(res)
				})
				}
				if($scope.recuiterList == 'recuriter'){
					$http.post('rest/barchat/getRecruiterWiseSubmissionsVsRejectionsByReportType/', postedData).then(function(res){
						if(res.data.status == "Exception"){
							swal('Internal Server Error');
						}
					$scope.encodedData = res.data.result.base64String;
					
				})
					$http.post('rest/barchat/getRecruiterWiseSubmissionsVsClousersByReportType/', postedData).then(function(res){
						if(res.data.status == "Exception"){
							swal('Internal Server Error');
						}
					$scope.subvsclosures = res.data.result.base64String;
					
				})
					$http.post('rest/barchat/getRecruiterWiseClosersVsJoinersByReportType/', postedData).then(function(res){
						if(res.data.status == "Exception"){
							swal('Internal Server Error');
						}
					$scope.closuresvsJoinings = res.data.result.base64String;
					
				})
					}
			}
			
			  $scope.dateChanges = function(example){
				  if(example){
						if($scope.reptList == 'day'){
							 $scope.example = $filter('date')(example, 'yyyy-MM-dd'); 
								$scope.month='';
								$scope.year='';
								/*$scope.reportType();*/
								/*$scope.reportTypebyAllRecruiter();*/
						}else if($scope.reptList == 'month'){
							 $scope.example=''
							 $scope.month = $filter('date')(example, 'MM');
							 $scope.year=$filter('date')(example, 'yyyy');
							/* $scope.reportType();
							 $scope.reportTypebyAllRecruiter();*/
						}else if($scope.reptList == 'year'){
							$scope.month='';
							$scope.example='';
							$scope.year = example;
							/*$scope.reportType();
							$scope.reportTypebyAllRecruiter();*/
						}
					  }
			  }
			/*  $scope.reportType= function(){
				  var accessToken=$rootScope.accessToken;
				  $scope.obj= {
							 "reportType":$scope.reptList,
							 "input":" ",
							 "role":"  ",
							 "roleUserId":"  ",
							 "date": $scope.example,
							 "month":$scope.month,
							 "year":$scope.year
							 }
					  $http.post('rest/barchat/getRecruiterWiseSubmissionsVsRejectionsByReportType',$scope.obj).then(function(res){
						  console.log(res);
						  $scope.basString = res.data.result;
					  })
			  }*/
			 /* $scope.reportTypebyAllRecruiter = function(){
				  $scope.obj={
							  "reportType":$scope.reptList,
							  "year":$scope.year,
							  "month":$scope.month,
							  "date": $scope.example
							}
				  $http.post('rest/barchat/getRecruiterWiseSubmissionsVsRejectionsByReportType',$scope.obj).then(function(res){
					  console.log(res);
					  $scope.b64String = res.data.result;
				  })
				  $http.post('rest/barchat/getLeadWiseSubmissionsVsRejectionsByReportType',$scope.obj).then(function(res){
					  console.log(res);
					  $scope.getLeadWise = res.data.result;
				  })
			  }*/
			  })