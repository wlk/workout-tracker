class WorkoutService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = { headers: @headers }

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing WorkoutService"

  allWorkouts: () ->
    @$log.debug "listWorkouts()"
    deferred = @$q.defer()

    @$http.get("/api/workouts")
    .success((data, status, headers) =>
      @$log.info("Successfully listed workouts - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to list workouts - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getWorkoutsInRange: (startDate, endDate) ->
    @$log.debug "WorkoutService getWorkoutsInRange() " + startDate + ", endDate: " + endDate
    deferred = @$q.defer()

    @$http.get("/api/workouts/" + startDate + "/" + endDate)
    .success((data, status, headers) =>
      @$log.info("Successfully listed workouts - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to list workouts - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getSummaryInRange: (startDate, endDate) ->
    @$log.debug "WorkoutService getSummaryInRange() " + startDate + ", endDate: " + endDate
    deferred = @$q.defer()

    @$http.get("/api/summary/" + startDate + "/" + endDate)
    .success((data, status, headers) =>
      @$log.info("Successfully getSummaryInRange - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to getSummaryInRange - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getAllReports: () ->
    @$log.debug "WorkoutService allReports()"
    deferred = @$q.defer()

    @$http.get("/api/report")
    .success((data, status, headers) =>
      @$log.info("Successfully listed workouts - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to list workouts - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getReportsInRange: (startDate, endDate) ->
    @$log.debug "WorkoutService getReportsInRange() " + startDate + ", endDate: " + endDate
    deferred = @$q.defer()

    @$http.get("/api/report/" + startDate + "/" + endDate)
    .success((data, status, headers) =>
      @$log.info("Successfully getReportsInRange - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to getReportsInRange - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getWorkout: (id) ->
    @$log.debug "getWorkout()"
    deferred = @$q.defer()

    @$http.get("/api/workouts/" + id)
    .success((data, status, headers) =>
      @$log.info("Successfully got workout - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to got workout - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getSummary: () ->
    @$log.debug "getSummary()"
    deferred = @$q.defer()

    @$http.get("/api/summary")
    .success((data, status, headers) =>
      @$log.info("Successfully got summary #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to get summary status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  addWorkout: (workout) ->
    @$log.debug "addWorkout"
    deferred = @$q.defer()

    @$http.post('/api/workouts/new', workout)
    .success((data, status, headers) =>
      @$log.info("Successfully created workout - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to create workout - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  editWorkout: (workout, id) ->
    @$log.debug "editWorkout: " + id
    deferred = @$q.defer()

    @$http.post('/api/workouts/' + id, workout)
    .success((data, status, headers) =>
      @$log.info("Successfully created workout - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to create workout - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  deleteWorkout: (workout, id) ->
    @$log.debug "deleteWorkout: " + id
    deferred = @$q.defer()

    @$http.delete('/api/workouts/' + id, workout)
    .success((data, status, headers) =>
      @$log.info("Successfully deleted workout - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to delete workout - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

servicesModule.service('WorkoutService', WorkoutService)