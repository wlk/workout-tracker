
class WorkoutService

    @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
    @defaultConfig = { headers: @headers }

    constructor: (@$log, @$http, @$q) ->
        @$log.debug "constructing WorkoutService"

    listWorkouts: () ->
        @$log.debug "listWorkouts()"
        deferred = @$q.defer()

        @$http.get("/workouts")
        .success((data, status, headers) =>
                @$log.info("Successfully listed workouts - status #{status}")
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
                @$log.error("Failed to list workouts - status #{status}")
                deferred.reject(data);
            )
        deferred.promise

    addWorkout: (workout) ->
        @$log.debug "addWorkout" #{angular.toJson(workout, true)}"
        deferred = @$q.defer()

        @$http.post('/workout', workout)
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