package actors

import java.time.Instant

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.collection.immutable.HashMap

/**
  * @author leopold
  * @since 15/04/18
  */
class ChatRoom(key: String, title: String) extends Actor with ActorLogging {
  private var connections: Map[ActorRef, String] = new HashMap
  private var messages: List[ChatMessage] = Nil

  private def senderName = connections(sender)

  override def receive: Receive = {

    case Connect(name) =>
      log.info(s"New connection to room $title: $name")
      val time = Instant.now()
      sender ! ReceiveMessage("chatbot", fromMe = false, "Welcome", time)
      val notification = ReceiveMessage("chatbot", false, s"$name connected", time)
      connections.keys.foreach(_ ! notification)
      connections += (sender -> name)

    case Disconnect() =>
      val name = senderName
      log.info(s"$name disconnected from $title")
      connections = connections - sender
      val notification = ReceiveMessage("chatbot", fromMe = false, s"$name left", Instant.now())
      connections.keys.foreach(_ ! notification)

    case NewMessage(text) =>
      log.info(s"New message in room $title from $senderName: $text")
      val message = ChatMessage(sender, text, Instant.now())
      messages ::= message

      connections.keys.foreach(forwardMessageTo(message, _))
  }

  private def forwardMessageTo(chatMessage: ChatMessage, forwardTo: ActorRef): Unit =
    forwardTo ! ReceiveMessage(
      senderName,
      chatMessage.author == forwardTo,
      chatMessage.text,
      chatMessage.time
    )
}

case class ChatMessage(author: ActorRef, text: String, time: Instant)

object ChatRoom {
  def apply(key: String, title: String) = Props(classOf[ChatRoom], key, title)
}
