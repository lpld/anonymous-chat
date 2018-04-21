package actors

import akka.actor.{Actor, ActorRef}

/**
  * @author leopold
  * @since 15/04/18
  */
class ChatConnection(room: ActorRef, client: ActorRef, name: String) extends Actor {

  override def preStart(): Unit = {
    room ! Connect(name)
  }

  override def receive: Receive = {
    case in: NewMessage => room ! in

    case out: ReceiveMessage => client ! out
//      out ! s"${if (fromMe) ">>" else "\t\t<<"} [$author]: $text ($time)"
  }

  override def postStop(): Unit = room ! Disconnect()
}
