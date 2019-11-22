app.controller('bdmleadDashboardCtrl',function($scope,$location,$window,$rootScope,$filter, clientContactService,addressService, clientService,$http,userlistServices) {

/*	 $scope.showtable = function() {
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
	    };*/
	
	  //Dynamic Starts By @Sirisha starts
	 /*   $scope.reportList = ['day','month','year'];
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
			}*/
			
		//Dynamic Starts By @Sirisha ends
	    $scope.dateforSearch =  new Date();
	
	    $scope.open1 = function() {			
		    $scope.popup1.opened = true;
		  };
		 $scope.popup1 = {
			opened: false
			};	
		$scope.typeOfReport = 'date';
		$scope.roleChange=function(searchcategory){
			
			$scope.roleSelect;
			  $http.get("rest/Reg/getUserNamesByRole"+'?role='+roleSelect).then(function(response){
			    	$scope.roleNamesList =  response.data.result;
			    	console.log($scope.roleNamesList);
	   $scope.count1= 0;
	   $scope.count2= 0;

	    		 $scope.subVsRejmandy=function(typeOfReport,month,year){
	    			 
	    			 var id = $window.localStorage.getItem("usid");
	    				var role = $window.localStorage.getItem("role");
	    				$scope.labelData=[];
	    				$scope.typeOfReport = typeOfReport,
    					$scope.year =year,
    					$scope.month = month
    					/*$scope.dateforSearch = dateforSearch*/
	    				var json={
	    					"typeOfReport":$scope.typeOfReport,
	    					"year":$scope.year,
	    					"month":$scope.month,
	    					"dateString":$scope.dateforSearch
	    				}
	    				
	   	    		 $http.post("rest/submission/submissionsVsrejections/"+id+'/'+role,json).then(function(response){
	   		 		    	$scope.subVsrejList =  response.data.result.Data;
	   		 		    	
	   		 		    	console.log($scope.subVsrejList);
	    			      for(let i=0;i<$scope.subVsrejList.length;i++)
	    			    	  {
	    			    	  
	    			    	  $scope.monthyear =  month +" "+year;
	    			    	  $scope.labelData.push($scope.monthyear);
	    			    	  $scope.countData = $scope.subVsrejList[i].count;
	    			    	  $scope.reportsCount.push($scope.countData);

	    			    	  $scope.submisstionData.push($scope.subVsrejList[i].status);
	    			    	  console.log($scope.labelData)
	    			    	  }
	    					 var ctx = document.getElementById('myChart').getContext('2d');
			   	    		 var labllers=$scope.labelData;
			   	    		 var years= $scope.monthyear
			   	    		 var statusdata=$scope.submisstionData;
			   	    		 var chartCount=   $scope.reportsCount;
			   	    		var GraphColourArray = ['#155a21','#940f0f'];
			   	    		
			   	    		var GraphDatasetsArray = [];
			   	    		
			   	    		var GraphLabelArray =["Submissions","Rejected"];
			   	         for (i=0; i < chartCount.length; i++)
			   	         {
			   	        	GraphDatasetsArray[i] = 
	                        {
	                        "label": [statusdata[i]],
	                        "data": [chartCount[i]], 
	                        "fill": false, 
	                        "borderColor": GraphColourArray[i], 
	                        "backgroundColor": GraphColourArray[i],
	                        "pointBackgroundColor": '#ffffff', 
	                        "tension": 0,
	                        }   
			   	        	
			   	        
	    }
			   	     
			   	         
		   		 		    var chartsGraph = $scope.chartsList;
			    			 var chart = new Chart(ctx, {
			    			     // The type of chart we want to create
			    			     type: 'bar',

			    			     		    			    
			    			      
			    			         data: {
			    			                labels: [years],
			    			                datasets: GraphDatasetsArray
			    			                    } 
			    			         
			    			     
			    			     ,

			    			     // Configuration options go here
			    			     options: {}
			    			     
			    			 });
			    			 			         
	    			    })	    			 
	    		 }	
	    		 $scope.subVsRejdate=function(typeOfReport,dateforSearch){
	    			 
	    			 var id = $window.localStorage.getItem("usid");
	    				var role = $window.localStorage.getItem("role");
	    				$scope.labelData=[];
	    				$scope.typeOfReport = typeOfReport,
    					/*$scope.year =year,
    					$scope.month = month,*/
    					$scope.dateforSearch = dateforSearch
	    				var json={
	    					"typeOfReport":$scope.typeOfReport,
	    					"year":$scope.year,
	    					"month":$scope.month,
	    					"dateString":$scope.dateforSearch
	    				}
	    				
	   	    		 $http.post("rest/submission/submissionsVsrejections/"+id+'/'+role,json).then(function(response){
	   		 		    	$scope.subVsrejList =  response.data.result.Data;
	   		 		    	
	   		 		    	console.log($scope.subVsrejList);
	    			      for(let i=0;i<$scope.subVsrejList.length;i++)
	    			    	  {
	    			    	  
	    			    	  $scope.monthyear =$scope.dateforSearch;
	    			    	  $scope.labelData.push($scope.monthyear);
	    			    	  $scope.countData = $scope.subVsrejList[i].count;
	    			    	  $scope.reportsCount.push($scope.countData);

	    			    	  $scope.submisstionData.push($scope.subVsrejList[i].status);
	    			    	  console.log($scope.labelData)
	    			    	  }
	    					 var ctx = document.getElementById('myChart').getContext('2d');
			   	    		 var labllers=$scope.labelData;
			   	    		 var years= $scope.monthyear
			   	    		 var statusdata=$scope.submisstionData;
			   	    		 var chartCount=   $scope.reportsCount;
			   	    		var GraphColourArray = ['#155a21','#940f0f'];
			   	    		
			   	    		var GraphDatasetsArray = [];
			   	    		
			   	    		var GraphLabelArray =["Submissions","Rejected"];
			   	         for (i=0; i < chartCount.length; i++)
			   	         {
			   	        	GraphDatasetsArray[i] = 
	                        {
	                        "label": [statusdata[i]],
	                        "data": [chartCount[i]], 
	                        "fill": false, 
	                        "borderColor": GraphColourArray[i], 
	                        "backgroundColor": GraphColourArray[i],
	                        "pointBackgroundColor": '#ffffff', 
	                        "tension": 0,
	                        }   
			   	        	
			   	        
	    }
			   	     
			   	         
		   		 		    var chartsGraph = $scope.chartsList;
			    			 var chart = new Chart(ctx, {
			    			     // The type of chart we want to create
			    			     type: 'bar',

			    			     		    			    
			    			      
			    			         data: {
			    			                labels: [years],
			    			                datasets: GraphDatasetsArray
			    			                    } 
			    			         
			    			     
			    			     ,

			    			     // Configuration options go here
			    			     options: {}
			    			     
			    			 });
			    			     			         
	    			    })	    			 
	    		 }	
	    		   $scope.barGraph=function(){
	    			
		    	
		    			
		    			  }
	    		   const setColor = label => label = 'Submmitions' ? '#ff4433': '#3377ee';
	    	/*
			 * $scope.dailyreport = new Chart('dailyreports', { type: 'bar',
			 * data: { labels: [], datasets: [{ label: '# Order Requests', data:
			 * [], backgroundColor: [ 'rgba(192, 57, 43,1)', 'rgba(155, 89, 182,
			 * 1)', 'rgba(244, 208, 63, 1)', 'rgba(26, 188, 156, 1)', 'rgba(46,
			 * 64, 83, 1)', 'rgba(255, 159, 64, 1)', 'rgba(255, 89, 122, 1)',
			 * 'rgba(154, 162, 185, 1)', 'rgba(155, 206, 86, 1)', 'rgba(255,
			 * 139, 255, 1)', 'rgba(253, 202, 155, 1)', 'rgba(55, 255, 64, 1)',
			 * 'rgba(255, 199, 232, 1)', 'rgba(154, 42, 235, 1)', 'rgba(25, 106,
			 * 86, 1)', 'rgba(175, 92, 12, 1)', 'rgba(245, 12, 255, 1)',
			 * 'rgba(255, 219, 164, 1)', 'rgba(212, 189, 222, 1)', 'rgba(255,
			 * 62, 134, 1)', 'rgba(55, 246, 186, 1)', 'rgba(155, 39, 155, 1)',
			 * 'rgba(12, 255, 255, 1)', 'rgba(221, 123, 155, 1)', 'rgba(47, 247,
			 * 64, 1)', 'rgba(242, 89, 122, 1)', 'rgba(25, 162, 234, 1)',
			 * 'rgba(255, 24, 86, 1)', 'rgba(255, 139, 55, 1)', 'rgba(112, 255,
			 * 255, 1)' ], borderColor: [ 'rgba(255, 99, 132, 0.2)', 'rgba(54,
			 * 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)', 'rgba(75, 192, 192,
			 * 0.2)', 'rgba(153, 102, 255, 0.2)', 'rgba(255, 159, 64, 0.6)',
			 * 'rgba(255, 89, 122, 0.2)', 'rgba(154, 162, 185, 1)', 'rgba(155,
			 * 206, 86, 1)', 'rgba(255, 139, 255, 0.4)', 'rgba(253, 202, 155,
			 * 1)', 'rgba(55, 255, 64, 0.8)', 'rgba(255, 199, 232, 0.2)',
			 * 'rgba(154, 42, 235, 0.2)', 'rgba(25, 106, 86, 0.2)', 'rgba(175,
			 * 92, 12, 1)', 'rgba(245, 12, 255, 0.5)', 'rgba(255, 219, 164,
			 * 0.1)', 'rgba(212, 189, 222, 0.6)', 'rgba(255, 62, 134, 1)',
			 * 'rgba(55, 246, 186, 0.1)', 'rgba(155, 39, 155, 0.4)', 'rgba(12,
			 * 255, 255, 1)', 'rgba(221, 123, 155, 0.5)', 'rgba(47, 247, 64,
			 * 1)', 'rgba(242, 89, 122, 0.6)', 'rgba(25, 162, 234, 1)',
			 * 'rgba(255, 24, 86, 0.1)', 'rgba(255, 139, 55, 0.4)', 'rgba(112,
			 * 255, 255, 1)' ], borderWidth: 2 }] }, options: { scales: { yAxes: [{
			 * ticks: { beginAtZero: true } }] } } });
			 */
	    		  
//	    		 
	    		 
	    		 
// var id = $window.localStorage.getItem("usid");
// var role = $window.localStorage.getItem("role");
// $http.get("rest/submission/submissionsVsrejections/"+id+'/'+role+'?year='+$scope.year+'&month='+$scope.month+'&typeOfReport='+$scope.typeOfReport).then(function(response){
// $scope.subVsrejList = response.data.result;
// });
	 		
	
			
		/* } */
	 			/*
				 * $scope.chartOptions1 = { dataSource:$scope.subVsrejList,
				 * palette: ['#e8a742', '#285484', '#e49316', '#034a96',
				 * '#e8a742', '#285484', '#034a96', '#034a96','#e8a742',
				 * '#e49316', '#285484', '#034a96'], commonSeriesSettings: {
				 * argumentField: "state", type: "bar", }, series: [ {
				 * valueField: "year", name: "status" }, { valueField: "year",
				 * name: "status" } ], title: "Submissions vs Closure",
				 * 
				 * "export": { enabled: false }, tooltip: { enabled: true,
				 * location: "bottom", customizeTooltip: function (arg) { return {
				 * text: arg.seriesName + " : " + arg.valueText }; } },
				 * onPointClick: function (e) { e.target.select(); } }; }
				 */
	  
	   /*
		 * $scope.subVsClos=function(){ var id =
		 * $window.localStorage.getItem("usid"); var role =
		 * $window.localStorage.getItem("role");
		 * $http.get("rest/submission/submissionsVsClosures/"+id+'/'+role).then(function(response){
		 * $scope.subvscloslist = response.data.result;
		 * console.log($scope.subvscloslist);
		 * 
		 * }); } $scope.subVsClos();
		 */
	  /*
		 * $scope.closVsJoin=function(){ var id =
		 * $window.localStorage.getItem("usid"); var role =
		 * $window.localStorage.getItem("role");
		 * $http.get("rest/submission/ClosuresVsJoinings").then(function(response){
		 * $scope.closvsjoinlist = response.data.result;
		 * console.log($scope.closvsjoinlist); if (response.data.status=="OK"){
		 * swal('Api3 called Successfully') } }); } $scope.closVsJoin();
		 */
	  /*
		 * $scope.reqVsSubm=function(){  var id =
		 * $window.localStorage.getItem("usid"); var role =
		 * $window.localStorage.getItem("role");
		 * $http.get("rest/submission/RequirementsVsSubmissions").then(function(response){
		 * $scope.reqvssubmlist = response.data.result;
		 * console.log($scope.reqvssubmlist); if (response.data.status=="OK"){
		 * swal('Api9 called Successfully') } }); } $scope.reqVsSubm();
		 * $scope.reqVsBdmById=function(){  var id =
		 * $window.localStorage.getItem("usid"); var role =
		 * $window.localStorage.getItem("role");
		 * $http.get("rest/submission/RequirementsVsBdm/"+id+'/'+role).then(function(response){
		 * $scope.reqvsBdmid = response.data.result;
		 * console.log($scope.reqvsBdmid); if (response.data.status=="OK"){
		 * swal('Api10 called Successfully') } }); } $scope.reqVsBdmById();
		 */
// 10.Nikhil Requirements vs Submissions end //
	    
	    
	  // 2. Submissions vs Closure starts//
	/*	$scope.chartstack=function(){
			 var labllers=$scope.labelData;
			var dataSource2 =$scope.subVsrejList; 
			$scope.datavalue2=dataSource2;
			
			console.log($scope.datavalue2);
			$scope.chartOptions2 = {
				        dataSource: dataSource2,
				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
				        commonSeriesSettings: {
				            argumentField: "status",
				            type: "bar",
				            
				        },
				        series: labllers,
				        title: "Submissions vs Closure",
				        
				        "export": {
				            enabled: false
				        },
				        tooltip: {
				            enabled: true,
				            location: "bottom",
				            customizeTooltip: function (arg) {
				                return {
				                    text: arg.seriesName + " : " + arg.valueText
				                };
				            }
				        },
				        onPointClick: function (e) {
				            e.target.select();
				        }
				    };
		}
		// 2. Submissions vs Closure end //
	    
		// 3. Closing vs Joining start//
		$scope.CvsJ=function(){
			var dataSource3 = [{
			    state: "Closure",
			    year2018: 30,
			    year2019: 47,
			   
			}, {
			    state: "Joining",
			    year2018: 40,
			    year2019: 49,
			    
			}
			]; 
			$scope.datavalue3=dataSource3;
			console.log($scope.datavalue3);
			$scope.chartOptions3 = {
				        dataSource: dataSource3,
				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
				        commonSeriesSettings: {
				            argumentField: "state",
				            type: "bar",
				            
				        },
				        series: [
				            { valueField: "year2018", name: "2018" },
				            { valueField: "year2019", name: "2019" }
				        ],
				        title: "Closure vs Joining",
				        
				        "export": {
				            enabled: false
				        },
				        tooltip: {
				            enabled: true,
				            location: "bottom",
				            customizeTooltip: function (arg) {
				                return {
				                    text: arg.seriesName + " : " + arg.valueText
				                };
				            }
				        },
				        onPointClick: function (e) {
				            e.target.select();
				        }
				    };
		}
		*/
		// 3. Closing vs Joining End //
		
	  // Quality reports ends//
		
		
		// productivity charts start //
		
		// 1. Requirement vs Submissions start//
		
		// 6.Revenue By period end //
		
	    		    $scope.chartdata=function(){
	    				var dataSource = [{
	    				    state: "Submissions",
	    				    year2018: "42",
	    				    year2019: "50",
	    				   
	    				}, {
	    				    state: "Rejections",
	    				    year2018: "20",
	    				    year2019: "12",
	    				    
	    				}
	    				]; 
	    				$scope.datavalue=dataSource;
	    				console.log($scope.datavalue);
	    				$scope.chartOptions1 = {
	    					        dataSource: dataSource,
	    					        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    					        commonSeriesSettings: {
	    					            argumentField: "state",
	    					            type: "bar",
	    					            
	    					        },
	    					        series: [
	    					            { valueField: "year2018", name: "2018" },
	    					            { valueField: "year2019", name: "2019" }
	    					        ],
	    					        title: "Submissions vs Rejections",
	    					        
	    					        "export": {
	    					            enabled: false
	    					        },
	    					        tooltip: {
	    					            enabled: true,
	    					            location: "bottom",
	    					            customizeTooltip: function (arg) {
	    					                return {
	    					                    text: arg.seriesName + " : " + arg.valueText
	    					                };
	    					            }
	    					        },
	    					        onPointClick: function (e) {
	    					            e.target.select();
	    					        }
	    					    };
	    			}
	    		    
	    		  //1.Submissions vs Rejections ends //
	    		    
	    		  //2. Submissions vs Closure starts//
	    			$scope.chartstack=function(){
	    				var dataSource2 = [{
	    				    state: "Submissions",
	    				    year2018: 42,
	    				    year2019: 47,
	    				   
	    				}, {
	    				    state: "Closure",
	    				    year2018: 10,
	    				    year2019: 39,
	    				    
	    				}
	    				]; 
	    				$scope.datavalue2=dataSource2;
	    				console.log($scope.datavalue2);
	    				$scope.chartOptions2 = {
	    					        dataSource: dataSource2,
	    					        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    					        commonSeriesSettings: {
	    					            argumentField: "state",
	    					            type: "bar",
	    					            
	    					        },
	    					        series: [
	    					            { valueField: "year2018", name: "2018" },
	    					            { valueField: "year2019", name: "2019" }
	    					        ],
	    					        title: "Submissions vs Closure",
	    					        
	    					        "export": {
	    					            enabled: false
	    					        },
	    					        tooltip: {
	    					            enabled: true,
	    					            location: "bottom",
	    					            customizeTooltip: function (arg) {
	    					                return {
	    					                    text: arg.seriesName + " : " + arg.valueText
	    					                };
	    					            }
	    					        },
	    					        onPointClick: function (e) {
	    					            e.target.select();
	    					        }
	    					    };
	    			}
	    			//2. Submissions vs Closure end //
	    		    
	    			//3.  Closing vs Joining start//
	    			$scope.CvsJ=function(){
	    				var dataSource3 = [{
	    				    state: "Closure",
	    				    year2018: 30,
	    				    year2019: 47,
	    				   
	    				}, {
	    				    state: "Joining",
	    				    year2018: 40,
	    				    year2019: 49,
	    				    
	    				}
	    				]; 
	    				$scope.datavalue3=dataSource3;
	    				console.log($scope.datavalue3);
	    				$scope.chartOptions3 = {
	    					        dataSource: dataSource3,
	    					        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    					        commonSeriesSettings: {
	    					            argumentField: "state",
	    					            type: "bar",
	    					            
	    					        },
	    					        series: [
	    					            { valueField: "year2018", name: "2018" },
	    					            { valueField: "year2019", name: "2019" }
	    					        ],
	    					        title: "Closure vs Joining",
	    					        
	    					        "export": {
	    					            enabled: false
	    					        },
	    					        tooltip: {
	    					            enabled: true,
	    					            location: "bottom",
	    					            customizeTooltip: function (arg) {
	    					                return {
	    					                    text: arg.seriesName + " : " + arg.valueText
	    					                };
	    					            }
	    					        },
	    					        onPointClick: function (e) {
	    					            e.target.select();
	    					        }
	    					    };
	    			}
	    			
	    			//3. Closing vs Joining End //
	    			
	    		  //Quality reports ends//
	    			
	    			
	    			// productivity charts start //
	    			
	    			//1.  Requirement vs Submissions start//
	    			$scope.productivity1=function(){
	    				var dataSource4 = [{
	    				    state: "Requirement",
	    				    year2018: 30,
	    				    year2019: 47,
	    				   
	    				}, {
	    				    state: "Submissions",
	    				    year2018: 40,
	    				    year2019: 49,
	    				    
	    				}
	    				]; 
	    				$scope.provalue1=dataSource4;
	    				console.log($scope.provalue1);
	    				$scope.productivity1 = {
	    					        dataSource: dataSource4,
	    					        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    					        commonSeriesSettings: {
	    					            argumentField: "state",
	    					            type: "bar",
	    					            
	    					        },
	    					        series: [
	    					            { valueField: "year2018", name: "2018" },
	    					            { valueField: "year2019", name: "2019" }
	    					        ],
	    					        title: " Requirement vs Submissions",
	    					        
	    					        "export": {
	    					            enabled: false
	    					        },
	    					        tooltip: {
	    					            enabled: true,
	    					            location: "bottom",
	    					            customizeTooltip: function (arg) {
	    					                return {
	    					                    text: arg.seriesName + " : " + arg.valueText
	    					                };
	    					            }
	    					        },
	    					        onPointClick: function (e) {
	    					            e.target.select();
	    					        }
	    					    };
	    			}
	    			
	    			//1. Requirement vs Submissions End //
	    			
	    			
	    			//2.  Requirement vs BDM start//
	    			$scope.productivity2=function(){
	    				var dataSource5 = [{
	    				    state: "Requirements",
	    				    year2018: 30,
	    				    year2019: 47,
	    				   
	    				}, {
	    				    state: "BDM",
	    				    year2018: 40,
	    				    year2019: 49,
	    				    
	    				}
	    				]; 
	    				$scope.provalue2=dataSource5;
	    				console.log($scope.provalue2);
	    				$scope.productivity2 = {
	    					        dataSource: dataSource5,
	    					        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    					        commonSeriesSettings: {
	    					            argumentField: "state",
	    					            type: "bar",
	    					            
	    					        },
	    					        series: [
	    					            { valueField: "year2018", name: "2018" },
	    					            { valueField: "year2019", name: "2019" }
	    					        ],
	    					        title: " Requirements vs BDM",
	    					        
	    					        "export": {
	    					            enabled: false
	    					        },
	    					        tooltip: {
	    					            enabled: true,
	    					            location: "bottom",
	    					            customizeTooltip: function (arg) {
	    					                return {
	    					                    text: arg.seriesName + " : " + arg.valueText
	    					                };
	    					            }
	    					        },
	    					        onPointClick: function (e) {
	    					            e.target.select();
	    					        }
	    					    };
	    			}
	    			
	    			//2. Requirements vs BDM End //
	    			
	    			
	    			// productivity charts end //
	    			
	    			
	    			
	    			
	    			
	    			// Revenue Charts Starts //
	    			
	    			//1.Revenue by Category starts //
	    			
	    			$scope.Revenuedata1=function(){
	    				var RevenueSource1 = [{
	    				    region: "UI",
	    				    val: 411
	    				}, {
	    				    region: "JAVA",
	    				    val: 1012
	    				}, {
	    				    region: "DotNet",
	    				    val: 344
	    				}, {
	    				    region: "Testing",
	    				    val: 590
	    				}, {
	    				    region: "SQL",
	    				    val: 727
	    				}, {
	    				    region: "NodeJs",
	    				    val: 351
	    				}]; 
	    				$scope.revenuevalue1=RevenueSource1;
	    				console.log($scope.revenuevalue1);
	    				$scope.revenuechart1 = {
	    				        type: "doughnut",
	    				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    				        dataSource: RevenueSource1,
	    				        title: "Revenue by Category",
	    				        tooltip: {
	    				            enabled: true,
	    				            location: "bottom",
	    				            customizeTooltip: function (arg) {
	    				                return {
	    				                    text: arg.argumentText + " : " + arg.valueText
	    				                };
	    				            }
	    				        },
	    				        
	    				        "export": {
	    				            enabled: false
	    				        },
	    				        series: [{            
	    				            argumentField: "region",
	    				            label: {
	    				                visible: true,
	    				                connector: {
	    				                    visible: true
	    				                }
	    				            }
	    				        }]
	    				    };
	    			}
	    			
	    			
	    			//1.Revenue by Category end //
	    			
	    			
	    			//2.Revenue by recuiter start //
	    			
	    			$scope.Revenuedata2=function(){
	    				var RevenueSource2 = [{
	    				    region: "abc",
	    				    val: 4000
	    				}, {
	    				    region: "xyz",
	    				    val: 1012
	    				}, {
	    				    region: "mln",
	    				    val: 3440
	    				}, {
	    				    region: "pqo",
	    				    val: 5900
	    				}, {
	    				    region: "ghi",
	    				    val: 7270
	    				}, {
	    				    region: "def",
	    				    val: 3510
	    				}]; 
	    				$scope.revenuevalue2=RevenueSource2;
	    				console.log($scope.revenuevalue2);
	    				$scope.revenuechart2 = {
	    				        type: "doughnut",
	    				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    				        dataSource: RevenueSource2,
	    				        title: "Revenue by Recuiter",
	    				        tooltip: {
	    				            enabled: true,
	    				            location: "bottom",
	    				            customizeTooltip: function (arg) {
	    				                return {
	    				                    text: arg.argumentText + " : " + arg.valueText
	    				                };
	    				            }
	    				        },
	    				        
	    				        "export": {
	    				            enabled: false
	    				        },
	    				        series: [{            
	    				            argumentField: "region",
	    				            label: {
	    				                visible: true,
	    				                connector: {
	    				                    visible: true
	    				                }
	    				            }
	    				        }]
	    				    };
	    			}
	    			
	    			
	    			//2.Revenue by recuiter end //
	    			
	    			//3.Revenve by Customer start //
	    			
	    			$scope.Revenuedata3=function(){
	    				var RevenueSource3 = [{
	    				    region: "TCS",
	    				    val: 4000
	    				}, {
	    				    region: "Capgemini",
	    				    val: 1012
	    				}, {
	    				    region: "CTS",
	    				    val: 3440
	    				}, {
	    				    region: "Glogal Logic",
	    				    val: 5900
	    				}, {
	    				    region: "HCL",
	    				    val: 7270
	    				}, {
	    				    region: "Gspann",
	    				    val: 3510
	    				}]; 
	    				$scope.revenuevalue3=RevenueSource3;
	    				console.log($scope.revenuevalue3);
	    				$scope.revenuechart3 = {
	    				        type: "doughnut",
	    				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    				        dataSource: RevenueSource3,
	    				        title: "Revenve by Customer",
	    				        tooltip: {
	    				            enabled: true,
	    				            location: "bottom",
	    				            customizeTooltip: function (arg) {
	    				                return {
	    				                    text: arg.argumentText + " : " + arg.valueText
	    				                };
	    				            }
	    				        },
	    				        
	    				        "export": {
	    				            enabled: false
	    				        },
	    				        series: [{            
	    				            argumentField: "region",
	    				            label: {
	    				                visible: true,
	    				                connector: {
	    				                    visible: true
	    				                }
	    				            }
	    				        }]
	    				    };
	    			}
	    			
	    			
	    			//3.Revenve by Customer end //
	    			
	    			//4.Revenue By BDM start //
	    			
	    			$scope.Revenuedata4=function(){
	    				var RevenueSource4 = [{
	    				    region: "ABC",
	    				    val: 400
	    				}, {
	    				    region: "XYZ",
	    				    val: 102
	    				}, {
	    				    region: "DEF",
	    				    val: 344
	    				}, {
	    				    region: "GHI",
	    				    val: 590
	    				}, {
	    				    region: "MLN",
	    				    val: 720
	    				}, {
	    				    region: "PQO",
	    				    val: 351
	    				}]; 
	    				$scope.revenuevalue4=RevenueSource4;
	    				console.log($scope.revenuevalue4);
	    				$scope.revenuechart4 = {
	    				        
	    				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    				        dataSource: RevenueSource4,
	    				        title: "Revenue By BDM",
	    				        tooltip: {
	    				            enabled: true,
	    				            location: "bottom",
	    				            customizeTooltip: function (arg) {
	    				                return {
	    				                    text: arg.argumentText + " : " + arg.valueText
	    				                };
	    				            }
	    				        },
	    				        
	    				        "export": {
	    				            enabled: false
	    				        },
	    				        series: [{            
	    				            argumentField: "region",
	    				            label: {
	    				                visible: true,
	    				                connector: {
	    				                    visible: true
	    				                }
	    				            }
	    				        }]
	    				    };
	    			}
	    			
	    			
	    			//4.Revenue By BDM end //
	    			
	    			//5.Revenue By lead(sum of recuiters  start//
	    			
	    			$scope.Revenuedata5=function(){
	    				var RevenueSource5 = [{
	    				    region: "ABC",
	    				    val: 4005
	    				}, {
	    				    region: "XYZ",
	    				    val: 1025
	    				}, {
	    				    region: "DEF",
	    				    val: 3445
	    				}, {
	    				    region: "GHI",
	    				    val: 5905
	    				}, {
	    				    region: "MLN",
	    				    val: 7205
	    				}, {
	    				    region: "PQO",
	    				    val: 3515
	    				}]; 
	    				$scope.revenuevalue5=RevenueSource5;
	    				console.log($scope.revenuevalue5);
	    				$scope.revenuechart5 = {
	    				        
	    				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    				        dataSource: RevenueSource5,
	    				        title: "Revenue By Lead",
	    				        tooltip: {
	    				            enabled: true,
	    				            location: "bottom",
	    				            customizeTooltip: function (arg) {
	    				                return {
	    				                    text: arg.argumentText + " : " + arg.valueText
	    				                };
	    				            }
	    				        },
	    				        
	    				        "export": {
	    				            enabled: false
	    				        },
	    				        series: [{            
	    				            argumentField: "region",
	    				            label: {
	    				                visible: true,
	    				                connector: {
	    				                    visible: true
	    				                }
	    				            }
	    				        }]
	    				    };
	    			}
	    			
	    			
	    			//5.Revenue By lead(sum of recuiters  end//
	    			
	    			//6.Revenue By Period start //
	    			
	    			$scope.Revenuedata6=function(){
	    				var RevenueSource6 = [{
	    				    region: "2014",
	    				    val: 4005
	    				}, {
	    				    region: "2015",
	    				    val: 1025
	    				}, {
	    				    region: "2016",
	    				    val: 3445
	    				}, {
	    				    region: "2017",
	    				    val: 5905
	    				}, {
	    				    region: "2018",
	    				    val: 7205
	    				}, {
	    				    region: "2019",
	    				    val: 3515
	    				}]; 
	    				$scope.revenuevalue6=RevenueSource6;
	    				console.log($scope.revenuevalue6);
	    				$scope.revenuechart6 = {
	    				        
	    				        palette:  ['#e8a742', '#285484', '#e49316', '#034a96', '#e8a742', '#285484', '#034a96', '#034a96','#e8a742', '#e49316', '#285484', '#034a96'],
	    				        dataSource: RevenueSource6,
	    				        title: "Revenue By Period",
	    				        tooltip: {
	    				            enabled: true,
	    				            location: "bottom",
	    				            customizeTooltip: function (arg) {
	    				                return {
	    				                    text: arg.argumentText + " : " + arg.valueText
	    				                };
	    				            }
	    				        },
	    				        
	    				        "export": {
	    				            enabled: false
	    				        },
	    				        series: [{            
	    				            argumentField: "region",
	    				            label: {
	    				                visible: true,
	    				                connector: {
	    				                    visible: true
	    				                }
	    				            }
	    				        }]
	    				    };
	    			}
		
		// Revenue Charts End //
	    
	    			//Dynamic Starts By @Sirisha
/*	    			$scope.getCharts = function(){
	    				$http.get('rest/barchat/getTotalSubmissionsAndRejections').then(function(res){
	    					$scope.encodedData = res.data.encodedString;
	    					console.log(res)
	    				})
	    			}
	    			$scope.getCharts();
	    			  $scope.dateChanges = function(example){
	    				  if(example){
	    						if($scope.reptList == 'day'){
	    							 $scope.example = $filter('date')(example, 'yyyy-MM-dd'); 
	    								$scope.month='';
	    								$scope.year='';
	    								$scope.reportType();
	    								$scope.reportTypebyAllRecruiter();
	    						}else if($scope.reptList == 'month'){
	    							 $scope.example=''
	    							 $scope.month = $filter('date')(example, 'MM');
	    							 $scope.year=$filter('date')(example, 'yyyy');
	    							 $scope.reportType();
	    							 $scope.reportTypebyAllRecruiter();
	    						}else if($scope.reptList == 'year'){
	    							$scope.month='';
	    							$scope.example='';
	    							$scope.year = example;
	    							$scope.reportType();
	    							$scope.reportTypebyAllRecruiter();
	    						}
	    					  }
	    			  }
	    			  $scope.reportType= function(){
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
	    			  }
	    			  $scope.reportTypebyAllRecruiter = function(){
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
	    			  */
	    			})
		}
	
});

