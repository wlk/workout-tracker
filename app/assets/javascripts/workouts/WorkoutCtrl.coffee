class WorkoutCtrl

  constructor: (@$log, @$location, @WorkoutService) ->
    @$log.debug "constructing WorkoutCtrl"
    #@workouts = []
    @startDate = ""
    @endDate = ""
    @getSummary()
    @getWorkouts()
    #@summary = []

  getAllWorkouts: () ->
    @$log.debug "WorkoutCtrl getAllWorkouts()"
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
    @$log.debug "WorkoutCtrl getWorkoutsInRange()"
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
    @$log.debug "WorkoutCtrl getWorkouts()"
    @startDate = @$location.path().replace("/workout/", "").split("/")[1]
    @endDate = @$location.path().replace("/workout/", "").split("/")[2]
    if(@startDate == undefined || @endDate == undefined )
      @getAllWorkouts()
    else
      @getWorkoutsInRange(@startDate, @endDate)

  getSummaryAllDates: () ->
    @$log.debug "WorkoutCtrl getSummaryAllDates()"
    @WorkoutService.getSummary()
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} summary"
        @summary = data
    ,
    (error) =>
      @$log.error "Unable to get Workouts: #{error}"
    )

  getSummaryInRange: (startDate, endDate) ->
    @$log.debug "WorkoutCtrl getSummaryInRange()"
    @WorkoutService.getSummaryInRange(startDate, endDate)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} summary"
        @summary = data
    ,
    (error) =>
      @$log.error "Unable to get report: #{error}"
    )

  getSummary: () ->
    @$log.debug "WorkoutCtrl getWorkouts()"
    @startDate = @$location.path().replace("/workout/", "").split("/")[1]
    @endDate = @$location.path().replace("/workout/", "").split("/")[2]
    if(@startDate == "" || @endDate == "" )
      @getSummaryAllDates()
    else
      @getSummaryInRange(@startDate, @endDate)


controllersModule.controller('WorkoutCtrl', WorkoutCtrl)