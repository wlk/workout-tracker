class ReportCtrl

  constructor: (@$log, @$location, @WorkoutService) ->
    @$log.debug "constructing ReportCtrl"
    @startDate = ""
    @endDate = ""
    @reports = {}
    @getReports()

  getAllReports: () ->
    @$log.debug "ReportCtrl getAllReports()"
#    @WorkoutService.allWorkouts()
#    .then(
#      (data) =>
#        @$log.debug "Promise returned #{data.length} Reports"
#        @workouts = data
#    ,
#    (error) =>
#      @$log.error "Unable to get Reports: #{error}"
#    )

  getReportsInRange: (startDate, endDate) ->
    @$log.debug "ReportCtrl getReportsInRange()"
#    @WorkoutService.getWorkoutsInRange(startDate, endDate)
#    .then(
#      (data) =>
#        @$log.debug "Promise returned #{data.length} Reports"
#        @workouts = data
#    ,
#    (error) =>
#      @$log.error "Unable to get Workouts: #{error}"
#    )

  getReports: () ->
    @$log.debug "ReportCtrl getReports()"
    @startDate = @$location.path().replace("/report/", "").split("/")[0]
    @endDate = @$location.path().replace("/report/", "").split("/")[1]
    if(@startDate == undefined || @endDate == undefined )
      @getAllReports()
    else
      @getReportsInRange(@startDate, @endDate)

controllersModule.controller('ReportCtrl', ReportCtrl)