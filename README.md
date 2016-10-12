### Requirements

Create a git repository (either local or public one on GitHub) that contains a RESTful web-service written in Scala. The service should allow users to place new car adverts and view, modify and delete existing car adverts.

Car adverts should have the following fields:
* **id** (_required_): **int** or **guid**, choose whatever is more convenient for you;
* **title** (_required_): **string**, e.g. _"Audi A4 Avant"_;
* **fuel** (_required_): gasoline or diesel, use some type which could be extended in the future by adding additional fuel types;
* **price** (_required_): **integer**;
* **new** (_required_): **boolean**, indicates if car is new or used;
* **mileage** (_only for used cars_): **integer**;
* **first registration** (_only for used cars_): **date** without time.

Service should:
* have functionality to return list of all car adverts;
  * optional sorting by any field specified by query parameter, default sorting - by **id**;
* have functionality to return data for single car advert by id;
* have functionality to add car advert;
* have functionality to modify car advert by id;
* have functionality to delete car advert by id;
* have validation (see required fields and fields only for used cars);
* accept and return data in JSON format, use standard JSON date format for the **first registration** field.

### Additional requirements

* Service should be able to handle CORS requests from any domain.
* Think about test pyramid and write unit-, integration- and acceptance-tests if needed.
* It's not necessary to document your code, but having a readme.md for developers who will potentially use your service would be great.

### Tips, hints & insights

* Feel free to use any Scala or sbt versions.
* Feel free to make any assumptions as long as you can explain them.
* Think how to use HTTP verbs and construct your HTTP paths to represent different actions (key word - RESTful).
* Commit frequently, small commits will help us to understand how you tackle the problem.


* We're using Play! at AutoScout24, but feel free to use a different Scala framework of your choice.
  * If you decide to use Play!, try to use version 2.4 and do not use **GlobalSettings** or any globals like **Play.configuration** or **Play.application**, use dependency injection instead.


* We're using Amazon Dynamo DB, but feel free to use different storage as long as we will be able to run it without doing excessive configuration work.
  * If you decide to use local Amazon Dynamo DB, which could be downloaded from Amazon as a [tar.gz](http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_latest.tar.gz) or [zip](http://dynamodb-local.s3-website-us-west-2.amazonaws.com/dynamodb_local_latest.zip) archive or installed with **homebrew** if you're using MacOS: ```brew install dynamodb-local```, try to not store data in Dynamo DB as a string blob, try to either use a Dynamo DB document model or store every field as a separate attribute.


* Feel free to ask questions! :)

### Sending us your work

If you used GitHub repository, just send us a link to your repository.

If you used local repository, zip it (folder called ".git" in your working directory, it is hidden usually!) and send it us by email or put it on Dropbox and send us a link. 


TODOS:
-  create Scala-Play Template with the activator - done
-  create local git repository, init, add and push to github (first commit) - done
-  adapt files build.sbt, plugins.sbt, application.conf, etc. - done
-  define routes for CRUD RESTful web-service - done
-  create model Car with attributes - done
-  write car adverts controller to mockup RESTful web-service - done
-  write unit-tests to test CRUD RESTful web-service (it is near TDD Test Drive Development) - done
-  write DAOs and tables to access the database via slick - done
-  configure application.conf to allow CORS requests 
-  write integration- and acceptance-tests
-  test and refactoring (e.g. /v1/ http://developer.lightbend.com/guides/play-rest-api/part-1/index.html)


* Create
curl --include --request POST --header "Content-type: application/json" --data '{"id":1011,"title":"Audi", "fuel":"Diesel", "price":160000, "newCar":false, "mileage":100000}' http://localhost:9000/cars
curl --include --request POST --header "Content-type: application/json" --data '{"id":1012,"title":"BMW",  "fuel":"Diesel", "price":160001, "newCar":true,  "mileage":0, "firstRegistration":"2012-12-12"}' http://localhost:9000/cars
curl --include --request POST --header "Content-type: application/json" --data '{"id":1013,"title":"BMW",  "fuel":"Benzin", "price":160001, "newCar":true,  "mileage":0}' http://localhost:9000/cars

* Read
curl --include --request GET --header "Content-type: application/json"  http://localhost:9000/cars
curl --include --request GET --header "Content-type: application/json"  http://localhost:9000/cars/1013

* Update
curl --include --request PUT --header "Content-type: application/json" --data '{"id":1012,"title":"BMW",  "fuel":"Diesel", "price":160001, "newCar":true,  "mileage":100000, "firstRegistration":"2012-12-12"}' http://localhost:9000/cars/1012

* Delete
curl --include --request DELETE --header "Content-type: application/json"  http://localhost:9000/cars/1013