import com.github.alexanderfefelov.bgbilling.api.soap.kernel._
import com.github.alexanderfefelov.bgbilling.api.soap.scalaxb._
import com.github.alexanderfefelov.bgbilling.api.soap.util.BgBillingApiSoapConfig
import scalaxb._

import scala.util.{Failure, Success}

object Main extends App {

  class ContractServiceCake extends ContractServiceBindings with Soap11ClientsWithAuthHeaderAsync with DispatchHttpClientsAsync with BgBillingApiSoapConfig {
    override def baseAddress = new java.net.URI(serviceBaseAddress("ContractService"))
  }

  val cake = new ContractServiceCake
  val service = cake.service
  service.contractGet(1).onComplete {
    case Success(response) =>
      println(response)
    case Failure(error) =>
      println(error)
  }

}