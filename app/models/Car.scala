package models

import java.util.Date

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
  val Gasoline, Diesel = Value
}


