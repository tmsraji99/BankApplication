app.controller('userprofileController', function($window,adminServices,userprofileService, $scope,$location,$rootScope,$cookieStore) {
var user=$window.localStorage.getItem("usid");
//$scope.role=$rootScope.role;
$scope.role=localStorage.getItem('role');

	  $scope.datehide=false;
  $scope.dateshow=true;
	
	   $scope.open1 = function() {
		   	$scope.datehide=true;
		   	$scope.dateshow=false;
		   	$scope.popup1.opened = true;
	   };
	 

  $scope.popup1 = {
    opened: false
  };
  
  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = $scope.formats[0];

$scope.sample = [{
		id: '1',
		name: 'What was your childhood nickname?'
		}, {
		id: '2',
		name: 'In what city or town was your first job?'
		
		}];

		$scope.redirect=function(){
		
			var change = $window.localStorage.getItem("role");
			if(change=="BDM"){
				$location.path('/bdm');
			}
			else if(change=="bdmlead"){
				$location.path('/bdmlead');
			}

			else if(change=="recruiter"){
				$location.path('/recruiter');
			}
			else if(change=="Admin"){
				$location.path('/admin');
			}
		}


var promise=userprofileService.getUser(user);
	promise.then(function(response){
	    		//console.log(response.data.result);
	    		$scope.userdata=response.data.result;
				//console.log($scope.userdata);
	    	});	

			
$scope.updateUserProfile = function(userdata){
	//$scope.udata = userdata;
	//console.log($scope.udata.question.name);
	
	var details ={
				name:$scope.userdata.firstName+"."+$scope.userdata.lastName+"@ojas-it.com",
				password:$scope.userdata.password,
				firstName:$scope.userdata.firstName,
				lastName:$scope.userdata.lastName,
				contactNumber:$scope.userdata.contactNumber,
				email:$scope.userdata.email,
				role:$scope.userdata.role,
				question:$scope.userdata.question.name,
				doj:$scope.userdata.doj,
				dob:$scope.userdata.dob,
				extension:$scope.userdata.extension,
				designation:$scope.userdata.designation,
				status:$scope.userdata.status,
				answer:$scope.userdata.answer
	}
				
				
				
	
		var promise =adminServices.updateUserList($scope.userdata.id,details);
						
					promise.then(function(response){
						
						console.log(JSON.stringify(response.data));
						
						$('#viewdis').css('display','block');
						$('#editdetails').css('display','none')
					});	
	
	
	
}

		$scope.cancelfun = function(){
			$('#viewdis').css('display','block');
			$('#editdetails').css('display','none')
			$('#changepass').css('display','none');
		}


			$scope.passwordfun = function(){
				
				
				if($scope.newPassword==$scope.newPassword1){
				var details ={
				password:$scope.password,
				newPassword:$scope.newPassword
					}
				userprofileService.ChangePassword(user,details);
				var change = $window.localStorage.getItem("role");
				if(change=="BDM"){
				$location.path('/bdm');
			}
			else if(change=="bdmlead"){
				$location.path('/bdmlead');
			}

			else if(change=="recruiter"){
				$location.path('/recruiter');
			}
			else if(change=="Admin"){
				$location.path('/admin');
			}
				}
				else{
					$scope.message="Password doesn't match your new password!!";
				}

			}			
});