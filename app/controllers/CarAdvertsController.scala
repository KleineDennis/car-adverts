package controllers

import javax.inject._

import models.Car
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class CarAdvertsController @Inject() extends Controller {

  implicit val carWrites: Writes[Car] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "title").write[String]
  )(unlift(Car.unapply))

  implicit val carReads: Reads[Car] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "title").read[String]
  )(Car.apply _)


  def list = Action { implicit request =>
    val json = Json.toJson(Car.list)
    Ok(json)
  }

  def find(id: Int) = Action { implicit request =>
    val json = Json.toJson(Car.find(id))
    Ok(json)
  }

  def create = Action(BodyParsers.parse.json) { implicit request =>
    val carResult = request.body.validate[Car]

    carResult.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
      },
      car => {
        Car.create(car)
        Ok(Json.obj("status" ->"OK", "message" -> ("Car '" + car.title + "' saved.") ))
      }
    )
  }

  def update(id:Int) = Action(BodyParsers.parse.json) { implicit request =>
    val carResult = request.body.validate[Car]

    carResult.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
      },
      car => {
        Car.update(id, car)
        Ok(Json.obj("status" ->"OK", "message" -> ("Car '" + car.title + "' updated.") ))
      }
    )
  }

  def delete(id: Int) = Action { implicit request =>
    Car.delete(id)
    Ok("car deleted")
  }
}
