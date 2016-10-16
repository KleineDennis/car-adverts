import java.util.Date

import play.api.Application

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.specs2.mutable.Specification
import play.api.test.WithApplication


class ModelSpec extends Specification {

  import models._

  def dateIs(date: java.util.Date, str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) == str

  "Car model" should {

    def carsDao(implicit app: Application) = {
      val app2CarsDAO = Application.instanceCache[CarsDAO]
      app2CarsDAO(app)
    }

    "be retrieved by id" in new WithApplication {
      val car = Await.result(carsDao.findById(1011), Duration.Inf).get
      car.title must equalTo("Audi A4 Avant")
      car.firstRegistration must beSome.which(dateIs(_, "2012-12-12"))
    }

    "be listed" in new WithApplication {
      val cars = Await.result(carsDao.list(1), Duration.Inf)
      cars.head.id must equalTo(1011)
      cars.head.title must have length (13)
    }

    "be updated if needed" in new WithApplication {
      Await.result(carsDao.update(1011, Car(1011, "Audi A4 Avant", Fuel.Gasoline, 10001, false, mileage = None, Some(new Date(2012-12-12)))), Duration.Inf)

      val car = Await.result(carsDao.findById(1011), Duration.Inf).get
      car.price must equalTo(10001)
      car.mileage must beNone

    }

  }

}