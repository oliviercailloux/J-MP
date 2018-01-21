var app = angular.module("app", []);
app.controller("AppController", function($scope) {
  $scope.message = "Hello, AngularJS";	
  
  function setFalse(){
$scope.varB=false;
  $scope.addC=false;
$scope.remC=false;
$scope.addoC=false;
  }

  setFalse();
  $scope.showForm=function(e){

  	if(e=='VB')
  	{setFalse();
  	$scope.varB=true;	
  }
  if(e=='AC')
  {setFalse();
  	$scope.addC=true;	
  }

   if(e=='RC')
  {setFalse();
  	$scope.remC=true;	
  }
  if(e=='AF')
  {setFalse();
  	$scope.addoC=true;	
  }
  }
});