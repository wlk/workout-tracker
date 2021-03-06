
dependencies = [
  'ngRoute',
  'ui.bootstrap',
  'myApp.filters',
  'myApp.services',
  'myApp.controllers',
  'myApp.directives',
  'myApp.common',
  'myApp.routeConfig',
]

app = angular.module('myApp', dependencies)

angular.module('myApp.routeConfig', ['ngRoute'])
.config ($routeProvider) ->
  $routeProvider
  .when('/', {
      templateUrl: '/assets/partials/view.html'
    })
  .when('/workout/add', {
      templateUrl: '/assets/partials/create.html'
    })
  .when('/workout/:workoutId', {
      templateUrl: '/assets/partials/edit.html'
    })
  .when('/report/:startDate/:endDate', {
      templateUrl: '/assets/partials/report.html'
    })
  .when('/report', {
      templateUrl: '/assets/partials/report.html'
    })
  .when('/:startDate/:endDate', {
      templateUrl: '/assets/partials/view.html'
    })
  .otherwise({redirectTo: '/'})

@commonModule = angular.module('myApp.common', [])
@controllersModule = angular.module('myApp.controllers', [])
@servicesModule = angular.module('myApp.services', [])
@modelsModule = angular.module('myApp.models', [])
@directivesModule = angular.module('myApp.directives', [])
@filtersModule = angular.module('myApp.filters', [])