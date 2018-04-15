package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

/**
  * @author leopold
  * @since 15/04/18
  */
class ChatParticipant(name: String) extends Actor with ActorLogging {
  private var connections: List[ActorRef] = Nil

  override def receive: Receive = {

    case Connect(connection: ActorRef) =>
      connections ::= connection
      log.info("New client connection")

    case Disconnect(connection: ActorRef) =>
      connections = connections.filterNot(_ == connection)

    case msg: NewMessage => context.parent ! msg

    case msg@ReceiveMessage(author, fromMe, text, time) =>
      log.info(s"${if (fromMe) ">>" else "\t\t<<"} [$author]: $text ($time)")
      connections.foreach(_ ! msg)
  }
}

object ChatParticipant {
  def apply(name: String) = Props(classOf[ChatParticipant], name)
}
