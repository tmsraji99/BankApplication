app.controller('reqEditCtrl',function($scope,$routeParams,reqEditSer,listSkillService,designationService,listqualificationservice,addModeService,getHiringModeService,$rootScope, $window) {
     console.log("bdm Controller");
     
	 
	      console.log("bdm Controller");
	 reqEditSer.getEditableData($routeParams.id);
	 // var ss=(controllerSharingData.get('toto'));
	 
	 $scope.id12=$routeParams.id;
      var ss;
	  var editId=$routeParams.id;
	  var promise=reqEditSer.getEditableData($scope.id12);
	 
	 promise.then(function(data){
	     console.log("varunaaaaaa");
		 $scope.ListReqData=data;
		 ss=$scope.ListReqData.data;
		 console.log(ss.id);

		 
		 
		 
         console.log(ss);
	 console.log($routeParams.id);
	 
	 
	 var temparr1=ss.skills;
	 console.log(temparr1);
	 
	 var temparr2=ss.designations;
	 
	 
	 var temparr3=ss.qualifications;
	
	 
	 var temparr4=ss.certificates;
	 
	 var temparr5=ss.interviewMode;
	
	 
    // $scope.clientName=ss.client.clientName;
	 $scope.clientName=ss.client.id;
	 console.log(ss.client.id);
//$scope.contact_Name1=ss.addContact.contact_Name;
    // $scope.contact_Name=ss.addContact.id;
	 $scope.email=ss.addContact.email;
    $scope.mobile=ss.addContact.mobile;
    $scope.nameOfRequirement=ss.nameOfRequirement;
    $scope.billRate=ss.billRate;
    $scope.conversionFee=ss.conversionFee;
    $scope.minimumContract=ss.minimumContract;
    $scope.permanent=ss.permanent;
    $scope.tax=ss.tax;
    $scope.slab=ss.slab;
    $scope.samount=ss.samount;
    $scope.famount=ss.samount;
    			// frombdm:$scope.frombdm,
    			// tobdm:$scope.tobdm,
    			 
    $scope.requirementType=ss.requirementType;
    	$scope.skill=temparr1;		
    		/*	 skills:temparr,
    			 designations:temparrdesig,
    			 qualifications:temparrqualify,
    			 interviewMode:temparrinterview, */
				 
	$scope.designation=temparr2;
    $scope.qualifications=	temparr3;
	$scope.interviewMode=temparr5;
	$scope.certificationName=temparr4;
	
				 
    $scope.jobLocation=ss.jobLocation;
    $scope.noticePeriod=ss.noticePeriod;
    $scope.typeOfHiring=ss.typeOfHiring;
    $scope.salaryBand=ss.salaryBand;
   
    $scope.requirementDescription=ss.requirementDescription;
    $scope.totalExperience=ss.totalExperience;
    $scope.relavantExperience=ss.relavantExperience;  			
    $scope.numberOfPositions=ss.numberOfPositions;
    $scope.numberOfRounds=ss.numberOfRounds;

	$scope.netPeriod=ss.netPeriod;
    $scope.gross_Amount=ss.gross_Amount;	
		 
		 
	 });
	  
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
     
    /* 
 	$scope.getMode = function() {
		
		var promise1 = bdmService.getClientNames();
		promise1.then(function(response) {
			console.log(response);
			$scope.client = response.data.data;
			console.log($scope.client);

		});
	};	*/
     
     
 	$scope.getRequirement = function(id) {
		
 		console.log(id);
 		console.log("inside getrequirement function");
 		
 		
 		 var promise=reqEditSer.clientcontactm(id);
 	     
 	     promise.then(function(response){
 	 		console.log("hiringmode dataget");
 	 		$scope.chiru=response.data;
 	 		console.log($scope.chiru);
			$scope.contact_Name1=$scope.chiru.contact_Name;
			$scope.contact_Name=$scope.chiru.id;
			swal($scope.contact_Name);
 	 	});
 	
 	}
 	
 	$scope.getRequirementContact = function(addContact1) {
 		console.log(addContact1);		

 	 		$scope.val=$scope.addContact1;
 	 		console.log($scope.val);
 	 		var jhansi = JSON.parse($scope.val);
 	 		console.log(jhansi);
 	 		$scope.contactPerson=jhansi.contactPerson;
 	 		$scope.email=jhansi.email;
 	 		$scope.mobile=jhansi.mobile;
 	 		$scope.contact=jhansi.id;
 	
 	}
     
   $scope.billRate="IncludeTax";
   $scope.newtype="Retrospective";
   $scope.salaryBand="pm";
     /*$scope.show1=true;*/
     $scope.permanent="fixed";
     $scope.showSlab= $scope.permanent;
      $scope.slab = [
	        {
	        	
	           
	        }];
      $scope.show1=true;
	  $scope.show2=false;
      $scope.showFun1=function(){
    	  console.log("fun1");
    	  $scope.show1=true;
    	  $scope.show2=false;
    	  console.log("show1"+ $scope.show1);
    	  console.log("show2"+ $scope.show2);
      }
      $scope.showFun2=function(){
    	  console.log("fun2");
    	  $scope.show1=false;
    	  $scope.show2=true;
    	  console.log( "show1"+$scope.show1);
    	  console.log("show2"+ $scope.show1);
      }
     var promise=getHiringModeService.getModeDetails();
     
     promise.then(function(response){
 		console.log("hiringmode dataget");
 		$scope.hiremode=response.data;
 		console.log($scope.hiremode);
 	});
     
     
     
     

	$scope.skillbdm=listSkillService.query();
	var promise1=addModeService.getModeData();
	promise1.then(function(data){  
		$scope.interviewbdm=data.data;
		console.log($scope.interviewbdm);
		
	});
	
	
	
	
	
	
	 $scope.addNew = function(slab){
		 console.log("hhhhhhhhhhhhhh");
            $scope.slab.push({ 
               
            });
        };
    
        $scope.remove = function(){
            var newDataList=[];
            $scope.selectedAll = false;
            angular.forEach($scope.slab, function(selected){
                if(!selected.selected){
                    newDataList.push(selected);
                }
            }); 
            $scope.s = newDataList;
        };
	
	
	
	var promise=designationService.getDesignationList();
	
	promise.then(function(data){  
		$scope.desigbdm=data.data;
		console.log($scope.desigbdm);
		
	});
	/*var ClientData;
	//$scope.CCmap=bdmService.getCCmapdetails();
	var promise=bdmService.getCCmapdetails();
	promise.then(function(data){  
		$scope.CCmap=data.data;
		ClientData=$scope.CCmap;
		console.log($scope.CCmap);
		
	});*/
	
	/*var promise=bdmService.clientcontactm(id);
	promise.then(function(data){  
		$scope.CCmapf=data.data;
		ClientData=$scope.CCmapf;
		console.log($scope.CCmapf);
		
	});*/
	
	
	
	$scope.qualificbdm=listqualificationservice.query();
	console.log("hiqualifications");
	
	console.log($scope.qualificbdm);
	
	var promise=reqEditSer.getCertificateNames();
	promise.then(function(data){  
		$scope.certificatebdm=data.data;
		console.log($scope.certificatebdm);
		
	});
	
	
	var promise=reqEditSer.getClientNames555();
	promise.then(function(data){  
		console.log("inside the geeting client names from service");
		$scope.clientnamesbdm=data.data;
		console.log($scope.clientnamesbdm);
		
	});
	
     $scope.bdmreqdtls=function(reqdtls){
    	 $scope.bdmSkill=$scope.skill;
    	 
    	 var temparrinterview=[];
     	angular.forEach($scope.interviewMode,function(id,value){
 			 
     		temparrinterview.push({"id":id});
 		 });
     	
     	var temparrccmapping=[];
     	angular.forEach($scope.contact,function(id,value){
     		console.log($scope.contact)
     		temparrccmapping.push({"id":id});
 		 });
     	
     	
     	
    	var temparrqualify=[];
    	angular.forEach($scope.qualifications,function(id,value){
			 
    		temparrqualify.push({"id":id});
		 });
    	
    	var  temparrslab=[];
    	angular.forEach($scope.slab,function(id,value){
    		temparrslab.push({"id":id});
		 });
    	
    	
    	
    	
    	
    	
    	
    	var temparrdesig=[];
    	angular.forEach($scope.designation,function(id,value){
			 console.log($scope.designation)
    		temparrdesig.push({"id":id});
		 });
    	
    	var temparrcertificates=[];
    	angular.forEach($scope.certificationName,function(id,value){
			 console.log($scope.certificationName)
    		temparrcertificates.push({"id":id});
		 });
    	
    
    	var temparr=[];
    	angular.forEach($scope.bdmSkill,function(id,value){
			 
			temparr.push({"id":id});
		 });
    	
    	
    	
    	$scope.requirementType="Niche";
    	
    	$scope.id=$window.localStorage.getItem("usid");
    	console.log("user_id"+$scope.id);
$scope.reqdtls= {
		
     			
			client:{
    				 id:$scope.clientName
    			 },
    			 addContact:{
    				 id:$scope.contact_Name
    			 },
    			 user:{
    				 id:$scope.id
    			 },
    			 requirementStartdate:$scope.requirementStartdate,
    			 newtype:$scope.newtype,
    			 nameOfRequirement:$scope.nameOfRequirement,
    			// designation:$scope.designation,
    			// qualifications:$scope.qualifications,
    			 billRate:$scope.billRate,
    			 conversionFee:$scope.conversionFee,
    			 minimumContract:$scope.minimumContract,
    			 permanent:$scope.permanent,
    			 tax:$scope.tax,
    			 slab:$scope.slab,
    			 samount:$scope.samount,
    			 famount:$scope.famount,
    			// frombdm:$scope.frombdm,
    			// tobdm:$scope.tobdm,
    			 
    			 requirementType:$scope.requirementType,
    			// contact:$scope.temparrccmapping,
    			 skills:temparr,
    			 certificates:temparrcertificates,
    			 designations:temparrdesig,
    			 qualifications:temparrqualify,
    			 interviewMode:temparrinterview,
    			 jobLocation:$scope.jobLocation,
    			 noticePeriod:$scope.noticePeriod,
    			 typeOfHiring:$scope.typeOfHiring,
    			 salaryBand:$scope.salaryBand,
    			 conversionFee:$scope.conversionFee,
    			 requirementDescription:$scope.requirementDescription,
    			 totalExperience:$scope.totalExperience,
    			 relavantExperience:$scope.relavantExperience,
    			
    			 numberOfPositions:$scope.numberOfPositions,
    			 numberOfRounds:$scope.numberOfRounds,
    			 gross_Amount:$scope.gross_Amount,
    			 netPeriod:$scope.netPeriod
    			 
    		
}
/*$scope.getselectval = function () {
	 $scope.selectedvalues= 'Name: ' + $scope.qualificbdm
	 console.log($scope.selectedvalues);
	 }	 */

     console.log($scope.reqdtls);
    	 console.log("bdm going to services"); 
		 console.log();
    	 reqEditSer.addBdmdtls($scope.reqdtls,editId);
    
	
}
    
	
});
app.filter('unique', function() {
	   return function(collection, keyname) {
	      var output = [], 
	          keys = [];
	      angular.forEach(collection, function(item) {
	          var key = item[keyname];
	          if(keys.indexOf(key) === -1) {
	              keys.push(key); 
	              output.push(item);
	          }
	      });
	      return output;
	   };
	});


