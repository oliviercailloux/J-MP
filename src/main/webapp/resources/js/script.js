var app = angular.module("app", []);
app
		.controller(
				"AppController",
				function($scope, $http) {
					$scope.message = "Please press InitProblems to create the list of problems !!";

					$scope.ProblemID = 0;
					$scope.ProblemFormat = "AMPL";
					$scope.initProblems = function() {

						$http({
							url : window.location.href + "initProblems",
							method : "GET",

						}).then(function(response) {
							// console.log(response.data);
							$scope.message = response.data;
						}, function(response) {
							// fail case

							$scope.message = response;
						});

					};

					$scope.getProblem = function() {

						if ($scope.ProblemFormat == "AMPL")
							getAMPL();

						if ($scope.ProblemFormat == "JSON")
							getJSON();
					}

					var getAMPL = function() {

						$http(
								{
									url : window.location.href + "problems/"
											+ $scope.ProblemID + "/AMPL",
									method : "GET"

								}).then(function(response) {
							// console.log(response.data);
							$scope.message = response.data;
							// alert(response.data);
						}, function(response) {
							// fail case
							console.log(response);
							$scope.message = response;
						});

					};

					var getJSON = function() {

						$http(
								{
									url : window.location.href + "problems/"
											+ $scope.ProblemID + "/Json",
									method : "GET"
								}).then(function(response) {
							console.log(response.data);
							$scope.message = response.data;
						}, function(response) {
							// fail case
							console.log(response);
							$scope.message = response;
						});

					};

				});
