import models.Car

import scala.collection.immutable.List

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


cars

cars = cars ++ List(Car(101,"Skoda"))

//val car_ = cars.find(_.id == 101)


//cars = cars diff List(Car(101,"Skoda"))

cars = cars.filter(_.id != 101)




