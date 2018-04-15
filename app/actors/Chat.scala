package actors

import java.time.Instant

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.collection.immutable.HashMap

/**
  * @author leopold
  * @since 15/04/18
  */
class Chat(key: String, title: String) extends Actor with ActorLogging {
  private var participants: Map[ActorRef, String] = new HashMap
  private var messages: List[ChatMessage] = Nil

  override def receive: Receive = {
    case RegisterParticipant(name) =>
      log.info(s"Registering new participant $name for chat $title")
      val newParticipant = context.actorOf(ChatParticipant(name))
      participants += (newParticipant -> name)
      sender() ! newParticipant
      messages.take(10).foreach(forwardMessageTo(_, newParticipant))

    case NewMessage(text) =>
      val newMessage = ChatMessage(sender(), text, Instant.now())
      messages ::= newMessage
      log.info(s"New message in chat $title from ${participants(sender())}: $text")

      participants.keys.foreach(forwardMessageTo(newMessage, _))
  }

  private def forwardMessageTo(chatMessage: ChatMessage, forwardTo: ActorRef): Unit =
    forwardTo ! ReceiveMessage(
      participants(chatMessage.author),
      chatMessage.author == forwardTo,
      chatMessage.text,
      chatMessage.time
    )
}

case class ChatMessage(author: ActorRef, text: String, time: Instant)

object Chat {
  def apply(key: String, title: String) = Props(classOf[Chat], key, title)
}
