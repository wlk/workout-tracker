
class AddWorkoutCtrl

    constructor: (@$log, @$location,  @WorkoutService) ->
        @$log.debug "constructing AddWorkoutController"
        @workout = {}

    addWorkout: () ->
        @$log.debug "addWorkout()"
        @WorkoutService.addWorkout(@workout)
        .then(
            (data) =>
                @$log.debug "Promise returned #{data} Workout"
                @workout = data
                @$location.path("/")
            ,
            (error) =>
                @$log.error "Unable to add Workout: #{error}"
            )

controllersModule.controller('AddWorkoutCtrl', AddWorkoutCtrl)