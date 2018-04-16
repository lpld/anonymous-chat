package actors

import akka.actor.{Actor, ActorRef}

/**
  * @author leopold
  * @since 15/04/18
  */
class ChatConnection(room: ActorRef, out: ActorRef, name: String) extends Actor {

  override def preStart(): Unit = {
    room ! Connect(name)
  }

  override def receive: Receive = {
    case incoming: String =>
      room ! NewMessage(incoming)

    case ReceiveMessage(author, fromMe, text, time) =>
      out ! s"${if (fromMe) ">>" else "\t\t<<"} [$author]: $text ($time)"
  }

  override def postStop(): Unit = room ! Disconnect()
}
