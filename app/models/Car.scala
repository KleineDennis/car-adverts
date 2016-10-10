package models

import java.util.Date
import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.collection.immutable.List

/**
  * Created by denniskleine on 07.10.16.
  * id (required): int or guid, choose whatever is more convenient for you;
  * title (required): string, e.g. "Audi A4 Avant";
  * fuel (required): gasoline or diesel, use some type which could be extended in the future by adding additional fuel types;
  * price (required): integer;
  * new (required): boolean, indicates if car is new or used;
  * mileage (only for used cars): integer;
  * first registration (only for used cars): date without time.
  */
case class Car(id: Int,
               title: String //,
//               fuel: Fuel.Value,
//               price: Int,
//               newCar: Boolean,
//               mileage: Option[Int],
//               firstRegistration: Option[Date]
               )

object Fuel extends Enumeration {
  val Gasoline, Diesel = Value
}

object Car {
  var cars: List[Car] = {
    List(
      Car(
        11,
        "Audi"
      ),
      Car(
        12,
        "BMW"
      )
    )
  }

  def list = {
    cars
  }

  def find(id: Int) = {
    cars.find(_.id == id)
  }

  def create(car: Car) = {
    cars = cars ++ List(car)
  }

  def update(id: Int, car: Car) = {
    cars = cars.filter(_.id != id)
    cars = cars ++ List(car)
  }

  def delete(id: Int) = {
    cars = cars.filter(_.id != id)
  }

}

