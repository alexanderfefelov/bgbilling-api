import com.github.alexanderfefelov.bgbilling.api.db.util._
import com.github.alexanderfefelov.bgbilling.api.db.oss.repository._
import scalikejdbc._

object Main extends App {

  Db.init()
  println(AddressAreaRepository.findAll)
  println(AddressCityRepository.findAll)
  println(AddressCountryRepository.findAll)
  println(AddressHouseRepository.findAll)
  println(AddressQuarterRepository.findAll)
  println(AddressStreetRepository.findAll)

}
