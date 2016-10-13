package models

import java.util.Date
import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future


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
               title: String,
               fuel: Fuel.Value,
               price: Int,
               newCar: Boolean,
               mileage: Option[Int],
               firstRegistration: Option[Date]
               )

object Fuel extends Enumeration {
  val Gasoline = Value("Gasoline")
  val Diesel = Value("Diesel")
}


class CarsDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._
  private val cars = TableQuery[CarsTable]

  def list(col: Int): Future[List[Car]] =
    db.run(cars.to[List].sortBy(_.colIdx(col)).result)
//    db.run(cars.to[List].sortBy(_.title).result)

  def findById(id: Int): Future[Option[Car]] =
    db.run(cars.filter(_.id === id).result.headOption)

  def create(car: Car): Future[Int] =
    db.run(cars returning cars.map(_.id) += car)
//    db.run(cars.insertOrUpdate(car))

  def update(id: Int, car: Car): Future[Int] = {
    db.run(cars.filter(_.id === id).update(car))
  }

  def delete(id: Int): Future[Int] =
    db.run(cars.filter(_.id === id).delete)


  private class CarsTable(tag: Tag) extends Table[Car](tag, "CAR") {

    implicit val fuelColumnType = MappedColumnType.base[Fuel.Value, String](_.toString, string => Fuel.withName(string))
    implicit val dateColumnType = MappedColumnType.base[Date, Long](d => d.getTime, d => new Date(d))

    def id = column[Int]("ID", O.PrimaryKey)
    def title = column[String]("TITLE")
    def fuel = column[Fuel.Value]("FUEL")
    def price = column[Int]("PRICE")
    def newCar = column[Boolean]("NEWCAR")
    def mileage = column[Option[Int]]("MILEAGE")
    def firstRegistration = column[Option[Date]]("FIRST_REGISTRATION")

    def * = (id, title, fuel, price, newCar, mileage, firstRegistration) <> (Car.tupled, Car.unapply)
    def colIdx = Vector(null, id, price) // index addressable columns
  }

}
