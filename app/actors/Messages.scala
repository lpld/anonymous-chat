package actors

import java.time.Instant

import akka.actor.ActorRef

/**
  * @author leopold
  * @since 15/04/18
  */
case class FindChat(key: String)
case class CreateChat(key: String, title: String)
case class FindOrCreateChat(key: String, title: String)
case class ChatId(id: Long)

case class RegisterParticipant(name: String)
case class NewMessage(text: String)

case class ReceiveMessage(author: String, fromMe: Boolean, text: String, time: Instant)
case class LeaveChat()

case class Connect(conn: ActorRef)
case class Disconnect(conn: ActorRef)


