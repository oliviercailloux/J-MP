<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>J-MP</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.5/angular.min.js"></script>
<script type="text/javascript" >
  
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
            url : "<c:url value='/getProblem' />",
            method : "POST",
            data : {
                'ProblemID' : $scope.ProblemID
            }
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
            url : "<c:url value='/getAMPL' />?ProblemID="+$scope.ProblemID,
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
            	url : "<c:url value='/getJSON' />?ProblemID="+$scope.ProblemID,
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







</script>
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body ng-app="app">
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">J-MP</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
      
    </ul>
    <form class="navbar-form navbar-left" action="">
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search" name="search">
        <div class="input-group-btn">
          <button class="btn btn-default" type="submit">
            <i class="glyphicon glyphicon-search"></i>
          </button>
        </div>
      </div>
    </form>
  </div>
</nav>
	
	<div class="container" ng-controller="AppController">

<div class="row ">

<div class="col-lg-6" >
  <h2 style="border-bottom-style:solid;padding-bottom: 25px;">Operations</h2>
  

<br/>
<form class="form-horizontal" action="">
    <div class="form-group">
    <div class="row">
      <label class="control-label col-lg-2" >ProblemID</label>
      <div class="col-lg-2">
        <input type="text" class="form-control" id="" ng-model="ProblemID">
      </div>
      </div>
      <br/>
       <div class="row">
      <div class="col-lg-2">
        <button type="" class="btn btn-success" ng-click="getProblem()">getProblem</button>
      </div>
       <div class="col-lg-2 col-lg-offset-1">
        <button type="" class="btn btn-success" ng-click="getAMPL()">getAMPL</button>
      </div>
      <div class="col-lg-2 col-lg-offset-1">
        <button type="" class="btn btn-success" ng-click="getJSON()">getJSON</button>
      </div>
      </div>
      <br/>
       <div class="row">
      <div class="col-lg-2">
        <button type="" class="btn btn-success" ng-click="showForm('VB')">setVarBounds</button>
      </div>
       <div class="col-lg-2 col-lg-offset-1">
        <button type="" class="btn btn-success" ng-click="showForm('AC')">addConstraint</button>
      </div>
      <div class="col-lg-2 col-lg-offset-1">
        <button type="" class="btn btn-success" ng-click="showForm('RC')">removeConstraint</button>
      </div>
      <div class="col-lg-1 col-lg-offset-1">
        <button type="" class="btn btn-success" ng-click="showForm('AF')">setObjectiveFunction </button>
      </div>
      </div>

      <div class="row" ng-if="varB">
      <br/>
  <h4>Setting var bounds</h4> <div class="panel panel-default" style="background:#e1eaea;">
  <form class="form-horizontal " action=""><br/>
    <div class="form-group">
      <label class="control-label col-lg-3" for="">Var name:</label>
      <div class="col-lg-3">
        <input type="text" class="form-control" id="" >
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-lg-3" for="pwd">Lower Bound:</label>
      <div class="col-lg-3">          
        <input type="text" class="form-control" id="pwd" >
      </div>
    </div>
    
<div class="form-group">
      <label class="control-label col-lg-3" for="pwd">higher Bound:</label>
      <div class="col-lg-3">          
        <input type="text" class="form-control" id="pwd" >
      </div>
    </div>

    <div class="form-group">        
      <div class="col-sm-offset-3 col-sm-10">
        <button type="submit" class="btn btn-primary">Set</button>
      </div>
    </div>
  </form> </div>
</div>




 <div class="row " ng-if="addC">
      <br/>
  <h4>Adding new constraint</h4>
  <form class="form-horizontal panel panel-default" style="background:#e1eaea;"><br/>
    <div class="form-group">
      <label class="control-label col-lg-3" for="">New constraint:</label>
      <div class="col-lg-6">
        <input type="text" class="form-control" id="" >
      </div>
    </div>
    

    <div class="form-group">        
      <div class="col-sm-offset-3 col-sm-10 col-lg-offset-3">
        <button type="submit" class="btn btn-primary">Add</button>
      </div>
    </div>
  </form>
</div>

<div class="row" ng-if="remC">
      <br/>
  <h4>Removing old constraint</h4>
  <form class="form-horizontal panel panel-default" style="background:#e1eaea;"><br/>
    <div class="form-group">
      <label class="control-label col-lg-3" for="">Old constraint:</label>
      <div class="col-lg-6">
        <input type="text" class="form-control" id="" >
      </div>
    </div>
    

    <div class="form-group">        
      <div class="col-sm-offset-3 col-sm-10 col-lg-offset-3" >
        <button type="submit" class="btn btn-primary">Remove</button>
      </div>
    </div>
  </form>
</div>


 <div class="row" ng-if="addoC">
      <br/>
  <h4>Setting new objective function</h4>
  <form class="form-horizontal panel panel-default" style="background:#e1eaea;"><br/>
    <div class="form-group">
      <label class="control-label col-lg-4" for="">New Objective function:</label>
      <div class="col-lg-6">
        <input type="text" class="form-control" id="" >
      </div>
    </div>
    

    <div class="form-group">        
      <div class="col-sm-offset-3 col-sm-10 col-lg-offset-4">
        <button type="submit" class="btn btn-primary">Add</button>
      </div>
    </div>
  </form>
</div>

    </div>
   
    
    <div class="form-group">        
      
    </div>
  </form>






</div>

<div class="col-lg-5 panel panel-default col-lg-offset-1" style="background:#e1eaea;">
  <h1   >Output</h1>
  <textarea class="form-control" rows="20" id="comment" ng-model="message"></textarea> 
</div>







</div>

</div>
</body>
</html>