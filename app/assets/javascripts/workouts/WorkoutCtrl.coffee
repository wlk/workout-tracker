class WorkoutCtrl

  constructor: (@$log, @$location, @WorkoutService) ->
    @$log.debug "constructing WorkoutCtrl"
    @workouts = []
    @getAllWorkouts()
    @report = []
    @getReport()
    @startDate = ""
    @endDate = ""

  getAllWorkouts: () ->
    @$log.debug "getAllWorkouts()"

    @WorkoutService.allWorkouts()
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Workouts"
        @workouts = data
    ,
    (error) =>
      @$log.error "Unable to get Workouts: #{error}"
    )

  getWorkoutsInRange: () ->
    startDate = @$location.path().replace("/workout/", "").split("/")[1]
    endDate = @$location.path().replace("/workout/", "").split("/")[2]

    @$log.debug "getWorkoutsInRange()"

    @WorkoutService.getWorkoutsInRange(startDate, endDate)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Workouts"
        @workouts = data
    ,
    (error) =>
      @$log.error "Unable to get Workouts: #{error}"
    )

  getReport: () ->
    @$log.debug "getReport()"

    @WorkoutService.getReport()
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} reports"
        @report = data
    ,
    (error) =>
      @$log.error "Unable to get report: #{error}"
    )


controllersModule.controller('WorkoutCtrl', WorkoutCtrl)