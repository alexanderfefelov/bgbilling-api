import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._
import com.github.alexanderfefelov.bgbilling.api.soap.util.BgBillingApiSoapConfig
import scalaxb._

import scala.util.{Failure, Success}

object Main extends App {

  class AddressServiceCake extends AddressServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with BgBillingApiSoapConfig {
    override def baseAddress = new java.net.URI(serviceBaseAddress("AddressService"))
  }

  val cake = new AddressServiceCake
  val service = cake.service
  service.countryList(None).onComplete {
    case Success(response) =>
      println(response)
    case Failure(error) =>
      println(error)
  }

}
