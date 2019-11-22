app.service("TypeOfProcessService",function($http,$q, $rootScope) {
this.getprocessData=function(rr,process){
	var deferred=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/addstatus/find/'+rr+'/'+process,
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred.resolve(response);
		  $rootScope.processDetails=response.data.result;
      });
      return deferred.promise;
}

//for getting candidate name
this.getcandidateData=function(a){
	var deferred2=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/addCandidate/'+a,
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred2.resolve(response);
      });
      return deferred2.promise;
}
//interview type

this.getinterviewData=function(){
	var deferred3=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/modeofInterview',
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred3.resolve(response);
      });
      return deferred3.promise;
}
//Intervie

this.getInterviewDetails=function(nid){
	var deferred3=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/ptype/basedid/'+nid,
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred3.resolve(response);
      });
      return deferred3.promise;
}
//send interview schedule

this.sendinterviewData=function(interviewvalues){
	console.log("hi");
	$http({
		method:'POST',
		url:'rest/ptype',
		headers:{
			'content-type':'application/json'
		},
		data:interviewvalues
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}	
	
	//send offered letter

	this.sendOfferedData=function(offereddata){
		console.log("hi");
		$http({
			method:'POST',
			url:'rest/offeredleeter',
			headers:{
				'content-type':'application/json'
			},
			data:offereddata
		}).then(function(response){
			console.log(response);
		})},function(error){
			console.log(error);
		
		}	
//am approved
this.sendapprovedData=function(approvedvalues ,candId,reqId,loginId){
	console.log("hi");
	$http({
		method:'POST',
		url:'rest/addCandidate/update/'+candId+'/'+reqId+'/'+loginId,
		headers:{
			'content-type':'application/json'
		},
		data:approvedvalues
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}
	
	//Intervie mail
this.sendIntervieMail=function(id){
	$http({
		method:'POST',
		url:'rest/ptype/mail/'+id,
		headers:{
			'content-type':'application/json'
		},
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}

//am submitted
this.sendsubmittedData=function(submittedvalues,id){
	$http({
		method:'POST',
		url:'rest/addCandidate/update/'+id,
		headers:{
			'content-type':'application/json'
		},
		data:submittedvalues
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}	

//am rejeceted	

this.sendrejectedData=function(rejectedvalues,id){
	$http({
		method:'POST',
		url:'rest/amreject',
		headers:{
			'content-type':'application/json'
		},
		data:rejectedvalues
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}
	//am pending	

this.sendAmpendingData=function(ampendingvalues){
	$http({
		method:'POST',
		url:'rest/amquery',
		headers:{
			'content-type':'application/json'
		},
		data:ampendingvalues
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}
	
	
	this.sendAmholdData=function(amholddata,id){
	console.log(id);
	$http({
		method:'POST',
		url:'rest/amquery/'+id,
		headers:{
			'content-type':'application/json'
		},
		data:amholddata
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}
	
	
	//customer submitted	

this.sendcustsubData=function(canId,reqId,userId,status){
	$http({
		method:'POST',
		url:'rest/addCandidate/update/'+canId+'/'+reqId+'/'+userId+'/'+status,
		headers:{
			'content-type':'application/json'
		}
		
	}).then(function(response){
	})},function(error){
	
	}
	//customer shortlist	

this.sendcustshortData=function(shortvalues,id){
	$http({
		method:'POST',
		url:'rest/addCandidate/update/'+id,
		headers:{
			'content-type':'application/json'
		},
		data:shortvalues
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}
	//Customer rejeceted	

this.sendCustrejectedData=function(canId,reqId,userId,status){
	$http({
		method:'POST',
		url:'rest/addCandidate/update/'+canId+'/'+reqId+'/'+userId+'/'+status,
		headers:{
			'content-type':'application/json'
		},
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}

//pincode api

this.getDetails=function(pin){
	var deferred1=$q.defer();
	var config={
			headers:{
			'content type':'application/json',			
			}
	}
var result=$http.get('https://www.whizapi.com/api/v2/util/ui/in/indian-city-by-postal-code?pin='+pin+'&project-app-key=8umyz1c8dhcdusdgxmnqb75x').then(function(response){
		console.log("Country Data");
		deferred1.resolve(response.data);
		return deferred1.promise;
})
	return result;
	
}

//list
this.getprocesslistData=function(){
	var deferred4=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/ptype',
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred4.resolve(response);
      });
      return deferred4.promise;
}


//Hiringlist
this.getHiringList=function(){
	var deferred4=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/addstatus/findstatusforc2h',
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred4.resolve(response);
      });
      return deferred4.promise;
}

this.getquesservice=function(id){
	var deferred4=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/amquery/'+id,
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred4.resolve(response);
      });
      return deferred4.promise;
}
//feedbackList


this.getprocessFeddback=function(){
	var deferred12=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/interviewfeedback',
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred12.resolve(response);
      });
      return deferred12.promise;
}


//mounika
this.getaddress=function(x){
	var deferred5=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/Contact_address_map/'+x,
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred5.resolve(response);
      });
      return deferred5.promise;
}

this.changestatusfun=function(candidateid, status)
{
var deferred=$q.defer();
console.log(candidateid);
console.log( status);
$http.post("rest/addCandidate/"+candidateid, status).then(function(data){
	deferred.resolve(data);
});

}	



this.sendFeedbackData=function(feedbackdata){
	$http({
		method:'POST',
		url:'rest/interviewfeedback',
		headers:{
			'content-type':'application/json'
		},
		data:feedbackdata
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}	
	//feedbackdetails

this.getFeedbackDetails=function(gid){
	var deferred3=$q.defer();
      $http({
    	  method:'GET',
        url:'rest/interviewfeedback/basedid/'+gid,
        headers:{
			'content-type':'application/json'
		},
      }).then(function(response){
    	  deferred3.resolve(response);
      });
      return deferred3.promise;
}
this.sendFeedbackMail=function(id){
	console.log("hi");
	$http({
		method:'POST',
		url:'rest/interviewfeedback/mail',
		headers:{
			'content-type':'application/json'
		},
		data:id
	}).then(function(response){
		console.log(response);
	})},function(error){
		console.log(error);
	
	}




});
