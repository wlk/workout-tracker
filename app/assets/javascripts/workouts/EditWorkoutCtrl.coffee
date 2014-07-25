class EditWorkoutCtrl

  constructor: (@$log, @$location, @WorkoutService) ->
    @$log.debug "constructing EditWorkoutCtrl"
    id = @$location.path().replace("/workout/", "") #possibly not ideal, but don't know how to do this in AngularJS right now
    @fillWorkout(id)

  fillWorkout: (id) ->
    @$log.debug "fillWorkout()"
    @WorkoutService.getWorkout(id)
    .then(
      (data) =>
        @$log.debug "fillWorkout returned #{data} Workout"
        @workout = data
    ,
    (error) =>
      @$log.error "fillWorkout returned error: #{error}"
    )


  editWorkout: () ->
    @$log.debug "editWorkout()"
    @WorkoutService.editWorkout(@workout)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data} Workout"
        @workout = data
        @$location.path("/")
    ,
    (error) =>
      @$log.error "Unable to add Workout: #{error}"
    )

controllersModule.controller('EditWorkoutCtrl', EditWorkoutCtrl)