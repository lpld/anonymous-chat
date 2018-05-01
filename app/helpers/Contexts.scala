package helpers

import akka.actor.ActorSystem
import javax.inject.Inject

import scala.concurrent.ExecutionContext

/**
  * @author leopold
  * @since 1/05/18
  */
class Contexts @Inject()(akka: ActorSystem) {

  val database: ExecutionContext = akka.dispatchers.lookup("contexts.database")
}
