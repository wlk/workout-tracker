class WorkoutCtrl

  constructor: (@$log, @$location, @WorkoutService) ->
    @$log.debug "constructing WorkoutCtrl"
    @workouts = []
    @report = []
    @getReport()
    @startDate = ""
    @endDate = ""
    @getWorkouts()

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

  getWorkoutsInRange: (startDate, endDate) ->
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

  getWorkouts: () ->
    @$log.debug "getWorkouts()"
    @startDate = @$location.path().replace("/workout/", "").split("/")[1]
    @endDate = @$location.path().replace("/workout/", "").split("/")[2]
    if(@startDate == undefined || @endDate == undefined )
      @getAllWorkouts()
    else
      @getWorkoutsInRange(@startDate, @endDate)

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