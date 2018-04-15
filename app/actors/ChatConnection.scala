package actors

import akka.actor.{Actor, ActorRef}

/**
  * @author leopold
  * @since 15/04/18
  */
class ChatConnection(participant: ActorRef, client: ActorRef) extends Actor {

  override def preStart(): Unit = {
    participant ! Connect(self)
    client ! "Welcome to chat"
  }

  override def receive: Receive = {
    case incoming: String =>
      participant ! NewMessage(incoming)

    case ReceiveMessage(author, fromMe, text, time) =>
      client ! s"${if (fromMe) ">>" else "\t\t<<"} [$author]: $text ($time)"
  }

  override def postStop(): Unit = participant ! Disconnect(self)
}
