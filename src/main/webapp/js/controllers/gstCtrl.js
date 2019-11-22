app.controller("gstCtrl",function($scope,gstservice,$http,$rootScope,$location){

$scope.getGst=function(){
	var promise=gstservice.getgstData();
	promise.then(function(res){
    console.log(res);
    $scope.lists=res.data.result;
    $scope.status=res.data.status;
    if($scope.status=='ExcepcetdDataNotAvilable'){
    	$scope.nodata = "No Data Found";
    }
    if($scope.status=="StatusSuccess"){
    	$scope.nodata = "";
    }
	});
}
$scope.getGst();
$scope.addGst=function(gstdetails){
	var promise=gstservice.addgstData(gstdetails);
	promise.then(function(res){
    console.log(res.data);
    $scope.status=res.data.status;
    if($scope.status=='OK'){
    	window.swal('Added Successfully');
    	$scope.gstdetails={};
    	$location.path('/addGST');
    	$scope.getGst();
    }
	});
}
$scope.deleteGst=function(id){
	var promise=gstservice.deletegstData(id);
	promise.then(function(res){
    console.log(res.data);
    $scope.status=res.data.status;
    if($scope.status=='OK'){
    	window.swal('Deleted Successfully');
    	$scope.getGst();
    }
	});
}
$scope.getbyidGst=function(id){
/*	var promise=gstservice.getbyidgstData(id);
	promise.then(function(res){
    console.log(res);
	});*/
  $http.get('rest/GST/getbyid/'+id).then(function(response){
	console.log(response);
  $scope.gstupdatedetails=response.data.result;
  console.log($scope.gstupdatedetails);
  $rootScope.editId=$scope.gstupdatedetails.id;
  
  });
}
$scope.updateGst=function(gstupdatedetails){
	var id=$rootScope.editId;
	var promise=gstservice.updateGstdata(gstupdatedetails,id);
	promise.then(function(res){
    console.log(res);
    $scope.status=res.data.status;
    if($scope.status=='OK'){
    	window.swal('Updated Successfully');
    	$location.path('/addGST');
    	$('#myModal1').modal('hide');
    	$scope.getGst();
    }
	});
}
});