import java.util.Date

import controllers.CarAdvertsController
import models.{Car, CarsDAO, Fuel}
import org.specs2.mock.Mockito
import play.api.libs.json.{Json, Reads}
import play.api.mvc._
import play.api.test._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class CarAdvertsControllerSpec extends PlaySpecification with Mockito with Results {

  implicit val mockedRepo: CarsDAO = mock[CarsDAO]
  implicit val fuelReads = Reads.enumNameReads(Fuel)  //Json deserializer for type models.Fuel.Value

  implicit val carReads = Json.reads[Car]
  implicit val carWrites = Json.writes[Car]


  "CarAdvertsController " should {

    "create a car" in new WithCarApplication() {
      val car = Car(1012, "Audi A4 Avant", Fuel.Diesel, 10000, false, Some(70000), Some(new Date(2012-12-12)))
      mockedRepo.create(car) returns Future.successful(1)
      val result = carController.create().apply(FakeRequest().withBody(Json.toJson(car)))
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"OK","message":"Car 'Audi A4 Avant' created."}"""
    }

    "update a car" in new WithCarApplication() {
      val updatedCar = Car(1012, "Audi A4 Avant", Fuel.Gasoline, 10000, false, Some(50000), Some(new Date(2012-12-12)))
      mockedRepo.update(1012, updatedCar) returns Future.successful(1)

      val result = carController.update(1012).apply(FakeRequest().withBody(Json.toJson(updatedCar)))
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"OK","message":"Car 'Audi A4 Avant' updated."}"""
    }

    "delete a car" in new WithCarApplication() {
      mockedRepo.delete(1011) returns Future.successful(1)
      val result = carController.delete(1011).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"OK","message":"1 Car with id '1011' deleted."}"""
    }

    "list all cars" in new WithCarApplication() {
      val car = Car(1012, "Audi A4 Avant", Fuel.Diesel, 10000, false, Some(70000), Some(new Date(2012-12-12)))
      mockedRepo.list(1) returns Future.successful(List(car))
      val result = carController.list(1).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString === """{"status":"OK","data":[{"id":1012,"title":"Audi A4 Avant","fuel":"Diesel","price":10000,"newCar":false,"mileage":70000,"firstRegistration":1988}],"message":"All Car Adverts listed."}"""
    }

  }

}

class WithCarApplication(implicit mockedRepo: CarsDAO, implicit val ec: ExecutionContext) extends WithApplication {
  val carController = new CarAdvertsController(mockedRepo)
}