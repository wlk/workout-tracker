class ReportCtrl

  constructor: (@$log, @$location, @WorkoutService) ->
    @$log.debug "constructing ReportCtrl"
    @startDate = ""
    @endDate = ""
    @reports = {}
    @getReports()

  getReportsInRange: (startDate, endDate) ->
    @$log.debug "ReportCtrl getReportsInRange()"
    @WorkoutService.getReportsInRange(startDate, endDate)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Reports"
        @reports = data
    ,
    (error) =>
      @$log.error "Unable to get Workouts: #{error}"
    )

  getAllReports: () ->
    @$log.debug "ReportCtrl getAllReports()"
    @WorkoutService.getAllReports()
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Reports"
        @reports = data
    ,
    (error) =>
      @$log.error "Unable to get Reports: #{error}"
    )

  getReports: () ->
    @$log.debug "ReportCtrl getReports()"
    @startDate = @$location.path().replace("/report/", "").split("/")[0]
    @endDate = @$location.path().replace("/report/", "").split("/")[1]
    if(@startDate == "" || @endDate == "" )
      @getAllReports()
    else
      @getReportsInRange(@startDate, @endDate)

controllersModule.controller('ReportCtrl', ReportCtrl)