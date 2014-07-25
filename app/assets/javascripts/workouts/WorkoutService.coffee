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

  getWorkout: (id) ->
    @$log.debug "getWorkout()"
    deferred = @$q.defer()

    @$http.get("/api/workouts/" + id)
    .success((data, status, headers) =>
      @$log.info("Successfully got workout - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed togot workout - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getReport: () ->
    @$log.debug "getReport()"
    deferred = @$q.defer()

    @$http.get("/api/report")
    .success((data, status, headers) =>
      @$log.info("Successfully got report #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to get report status #{status}")
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
    @$log.debug "editWorkout"
    deferred = @$q.defer()

    @$http.get('/api/workouts/' + id, workout)
    .success((data, status, headers) =>
      @$log.info("Successfully created workout - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to create workout - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

servicesModule.service('WorkoutService', WorkoutService)