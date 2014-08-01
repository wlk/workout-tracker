##Objective

Create an application that tracks workouts (jogging) for logged in users

Users have to create accounts and login.
Logged in users can manage their workouts - creating, editing, deleting.
Each workout log has a date, time and distance
Each entry has an average speed
Users should be able to filter events and weekly reports by date range
Users should see a report that sums weekly distance, time and shows average speed in that week.
Prepare REST API for workout management.
All user actions are done via AJAX.

##Live demo
App is deployed on free heroku instance here:
http://warm-dawn-1738.herokuapp.com/

##Installing
1. Clone this project
2. run `./activator`
3. the "activator" console should start, to run the project execute command `run`
4. visit http://localhost:9000

##Technologies used:
- Playframework 2.3.2
- Scala
- Angularjs 1.2.21
- Coffescript
- Bootstrap 3.2.0
- nscala-time (Scala wrapper for Joda Time)


##Testing using curl
Collection of sample curl commands that interact with REST api (localhost):

```bash

curl -b cookies -c cookies -H "Content-Type: application/json" -X POST --data "{\"email\": \"abc@abc.com\", \"password\": \"abc123\"}" http://localhost:9000/api/users/new

curl -b cookies -c cookies -X POST --data "email=abc%40abc.com&password=abc123" http://localhost:9000/login

curl -b cookies -c cookies -X GET http://localhost:9000/api/workouts 

curl -b cookies -c cookies -H "Content-Type: application/json" -X POST -d "{ \"date\": \"2014-07-28\", \"distanceMeters\": 122, \"durationSeconds\": 12, \"name\": \"curltest1\" }" http://localhost:9000/api/workouts/new
curl -b cookies -c cookies -H "Content-Type: application/json" -X POST -d "{ \"date\": \"2014-07-10\", \"distanceMeters\": 122, \"durationSeconds\": 12, \"name\": \"curltest2\" }" http://localhost:9000/api/workouts/new

curl -b cookies -c cookies -X GET http://localhost:9000/api/workouts

curl -b cookies -c cookies -X GET http://localhost:9000/api/workouts/2014-07-09/2014-07-20

curl -b cookies -c cookies -X GET http://localhost:9000/api/report

curl -b cookies -c cookies -X GET http://localhost:9000/api/report/2014-07-09/2014-07-20

curl -b cookies -c cookies -X DELETE http://localhost:9000/api/workouts/6 

curl -b cookies -c cookies -X GET http://localhost:9000/api/workouts

curl -b cookies -c cookies -H "Content-Type: application/json" -X POST -d "{ \"date\": \"2014-07-28\", \"distanceMeters\": 99999, \"durationSeconds\": 9999, \"name\": \"curltest2\" }" http://localhost:9000/api/workouts/5

```

user that already has few events

```bash

curl -b cookies -c cookies -X POST --data "email=admin%40example.com&password=pass" http://localhost:9000/login

curl -b cookies -c cookies -X GET localhost:9000/api/workouts 
```

###tests that use heroku
```bash

curl -b cookies -c cookies -H "Content-Type: application/json" -X POST --data "{\"email\": \"abc@abc.com\", \"password\": \"abc123\"}" http://warm-dawn-1738.herokuapp.com/api/users/new

curl -b cookies -c cookies -X POST --data "email=abc%40abc.com&password=abc123" http://warm-dawn-1738.herokuapp.com/login

curl -b cookies -c cookies -X GET http://warm-dawn-1738.herokuapp.com/api/workouts 

curl -b cookies -c cookies -H "Content-Type: application/json" -X POST -d "{ \"date\": \"2014-07-28\", \"distanceMeters\": 122, \"durationSeconds\": 12, \"name\": \"curltest1\" }" http://warm-dawn-1738.herokuapp.com/api/workouts/new
curl -b cookies -c cookies -H "Content-Type: application/json" -X POST -d "{ \"date\": \"2014-07-10\", \"distanceMeters\": 122, \"durationSeconds\": 12, \"name\": \"curltest2\" }" http://warm-dawn-1738.herokuapp.com/api/workouts/new

curl -b cookies -c cookies -X GET http://warm-dawn-1738.herokuapp.com/api/workouts

curl -b cookies -c cookies -X GET http://warm-dawn-1738.herokuapp.com/api/workouts/2014-07-09/2014-07-20

curl -b cookies -c cookies -X GET http://warm-dawn-1738.herokuapp.com/api/report

curl -b cookies -c cookies -X GET http://warm-dawn-1738.herokuapp.com/api/report/2014-07-09/2014-07-20

curl -b cookies -c cookies -X DELETE http://warm-dawn-1738.herokuapp.com/api/workouts/6 

curl -b cookies -c cookies -X GET http://warm-dawn-1738.herokuapp.com/api/workouts

curl -b cookies -c cookies -H "Content-Type: application/json" -X POST -d "{ \"date\": \"2014-07-28\", \"distanceMeters\": 99999, \"durationSeconds\": 9999, \"name\": \"curltest2\" }" http://warm-dawn-1738.herokuapp.com/api/workouts/5

```

user that already has few events

```bash

curl -b cookies -c cookies -X POST --data "email=admin%40example.com&password=pass" http://warm-dawn-1738.herokuapp.com/login

curl -b cookies -c cookies -X GET http://warm-dawn-1738.herokuapp.com/api/workouts 
```


##Unit testing
To run unit tests, first follow steps in the Installation section, then using activator console run `test`

