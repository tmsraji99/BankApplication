app.directive("navbar",function(){
	alert("navbar directive");
	
	return{
		templateUrl : 'partials/navbar.html', 
		restrict:'E'
	}
})
