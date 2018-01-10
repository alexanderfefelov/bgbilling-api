import com.github.alexanderfefelov.bgbilling.api.db.util._
import com.github.alexanderfefelov.bgbilling.api.db.repository._
import scalikejdbc._

object Main extends App {

  Db.init()
  println(AddressArea.findAll)
  println(AddressCity.findAll)
  println(AddressCountry.findAll)
  println(AddressHouse.findAll)
  println(AddressQuarter.findAll)
  println(AddressStreet.findAll)

}
