package actors

import java.time.Instant

/**
  * @author leopold
  * @since 15/04/18
  */
case class GetRoom(key: String)
case class FindOrCreateRoom(key: String, title: String)

case class Connect(name: String)
case class Disconnect()
case class NewMessage(text: String)
case class ReceiveMessage(author: String, fromMe: Boolean, text: String, time: Instant)

