

object Coffees extends Table[(String, Int, Double, Double, Int, Int)]("COFFEES") {
  def name = column[String]("COF_NAME", O.PrimaryKey)
  def supID = column[Int]("SUP_ID")
  def price1 = column[Double]("PRICE1")
  def price2 = column[Double]("PRICE2")
  def sales = column[Int]("SALES")
  def total = column[Int]("TOTAL")
  def * = name ~ supID ~ price1 ~ price2 ~ sales ~ total
  def nth = Vector(price1, price2, sales) // Your index-addressable columns
}



