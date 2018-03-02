var app = angular.module("app", []);
app.controller("AppController", function($scope,$http) {
  
  
  function setFalse(){
$scope.varB=false;
  $scope.addC=false;
$scope.remC=false;
$scope.addoC=false;
$scope.ProblemFormat="AMPL";
$scope.ProblemID=0;
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

  $scope.initProblems = function() {
        
         $http({
            url : window.location.href+"initProblems",
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

   $scope.getProblem=function(){
    
    if($scope.ProblemFormat=="AMPL")
      getAMPL();

    if($scope.ProblemFormat=="JSON")
      getJSON();
   }     
        
var getAMPL = function() {
        
         $http({
            url :  window.location.href+"problems/"+$scope.ProblemID+"/AMPL",
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
        
var getJSON = function() {
            
            $http({
            	url : window.location.href+"problems/"+$scope.ProblemID+"/Json",
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





