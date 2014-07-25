
class WorkoutCtrl

    constructor: (@$log, @WorkoutService) ->
        @$log.debug "constructing WorkoutCtrl"
        @workouts = []
        @getAllWorkouts()

    getAllWorkouts: () ->
        @$log.debug "getAllWorkouts()"

        @WorkoutService.listWorkouts()
        .then(
            (data) =>
                @$log.debug "Promise returned #{data.length} Workouts"
                @workouts = data
            ,
            (error) =>
                @$log.error "Unable to get Workouts: #{error}"
            )


controllersModule.controller('WorkoutCtrl', WorkoutCtrl)