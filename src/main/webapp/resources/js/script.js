var app = angular.module("app", []);
app.controller("AppController", function($scope,$http) {
  
  
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

  $scope.getProblem = function() {
        
         $http({
            url : window.location.href+"/getProblem?ProblemID="+$scope.ProblemID,
            method : "GET",
            
        }).then(function(response) {
            //console.log(response.data);
            $scope.message = response.data;
        }, function(response) {
            //fail case
            console.log(response);
            $scope.message = response;
        });
        
        };
        
$scope.getAMPL = function() {
        
         $http({
            url :  window.location.href+"/getAMPL?ProblemID="+$scope.ProblemID,
            method : "GET"
            
        }).then(function(response) {
            //console.log(response.data);
            $scope.message = response.data;
            //alert(response.data);
        }, function(response) {
            //fail case
            console.log(response);
            $scope.message = response;
        });
        
        };
        
$scope.getJSON = function() {
            
            $http({
            	url : window.location.href+"/getJSON?ProblemID="+$scope.ProblemID,
                method : "GET"
           }).then(function(response) {
               console.log(response.data);
               $scope.message = response.data;
           }, function(response) {
               //fail case
               console.log(response);
               $scope.message = response;
           });
           
           };        


});





