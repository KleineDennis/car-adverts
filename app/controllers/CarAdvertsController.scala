package controllers

import java.util.Date
import javax.inject._

import models.{Car, CarsDAO, Fuel}
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import scala.concurrent.{ExecutionContext, Future}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class CarAdvertsController @Inject()(carsDao: CarsDAO)(implicit ec: ExecutionContext) extends Controller {

//  implicit val carWrites: Writes[Car] = (
//    (JsPath \ "id").write[Int] and
//    (JsPath \ "title").write[String] and
//    (JsPath \ "fuel").write[Fuel.Value] and
//    (JsPath \ "price").write[Int] and
//    (JsPath \ "newCar").write[Boolean] and
//    (JsPath \ "mileage").write[Option[Int]] and
//    (JsPath \ "firstRegistration").write[Option[Date]]
//    )(unlift(Car.unapply))
//
//  implicit val carReads: Reads[Car] = (
//    (JsPath \ "id").read[Int] and
//    (JsPath \ "title").read[String] and
//    (JsPath \ "fuel").read[Fuel.Value] and
//    (JsPath \ "price").read[Int] and
//    (JsPath \ "newCar").read[Boolean] and
//    (JsPath \ "mileage").read[Option[Int]] and
//    (JsPath \ "firstRegistration").read[Option[Date]]
//  )(Car.apply _)

  implicit val carReads = Json.reads[Car]
  implicit val carWrites = Json.writes[Car]
//  implicit val carFormat = Json.format[Car]


  def list = Action.async { implicit request =>
    val car = carsDao.list
    car.map(cs => Ok(Json.toJson(cs)))
  }

  def find(id: Int) = Action.async { implicit request =>
    val car = carsDao.findById(id)
    car.map(cs => Ok(Json.toJson(cs)))
  }

  def create = Action.async(BodyParsers.parse.json) { implicit request =>
    val carResult = request.body.validate[Car]

    carResult.fold(
      errors => {
        Future(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors))))
      },
      car => {
        carsDao.create(car)
        Future(Ok(Json.obj("status" ->"OK", "message" -> ("Car '" + car.title + "' saved.") )))
      }
    )
  }

  def update(id:Int) = Action.async(BodyParsers.parse.json) { implicit request =>
    val carResult = request.body.validate[Car]

    carResult.fold(
      errors => {
        Future(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors))))
      },
      car => {
        carsDao.update(id, car)
        Future(Ok(Json.obj("status" ->"OK", "message" -> ("Car '" + car.title + "' updated.") )))
      }
    )
  }

  def delete(id: Int) = Action.async { implicit request =>
    carsDao.delete(id)
    Future(Ok(Json.obj("status" ->"OK", "message" -> ("Car with id '" + id + "' deleted.") )))
  }
}
