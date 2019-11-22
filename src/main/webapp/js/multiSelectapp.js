app.controller('myCtrl',function($scope){
	$scope.skillSet=[
	{"id":1,"name":"java"},
	{"id":2,"name":"core java"},
	{"id":3,"name":"jquery"},
	{"id":4,"name":"Angular"}
	]
		
	console.log($scope.skillSet);
$scope.temp=[];
$scope.submit=function(){
angular.forEach($scope.skill,function(key){
$scope.temp.push({"id":key});
})
console.log($scope.temp);
}
});