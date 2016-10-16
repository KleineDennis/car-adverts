import play.api.Application
import play.api.test.{WithApplication, PlaySpecification}
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import java.util.Date
import scala.concurrent.Future

class CarsDAOSpec extends PlaySpecification{

  import models._

  "Car repository" should {

    def carRepo(implicit app: Application) = Application.instanceCache[CarsDAO].apply(app)

    "get all rows" in new WithApplication()  {
      val result = await(carRepo.list(1))
      result.length === 3
      result.head.title === "Audi A4 Avant"
    }

    "get single rows" in new  WithApplication()  {
      val result = await(carRepo.findById(1011))
      result.isDefined === true
      result.get.title === "Audi A4 Avant"
    }

    "insert a row" in new  WithApplication()  {
      val carId = await(carRepo.create(Car(1012, "Audi A4", Fuel.Diesel, 10000, false, Some(70000), Some(new Date(2012-12-12)))))
      carId === 1012
    }

    "update a row" in new  WithApplication()  {
      val result = await(carRepo.update(1012, Car(1012, "Audi A4", Fuel.Gasoline, 10000, false, Some(50000), Some(new Date(2012-12-12)))))
      result === 1
    }

    "delete a row" in new  WithApplication()  {
      val result = await(carRepo.delete(1012))
      result === 1
    }
  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

}