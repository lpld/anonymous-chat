package model

import akka.actor.Actor
import javax.inject.Singleton

/**
  * @author leopold
  * @since 10/04/18
  */
@Singleton
class ChatEngine(chat: Chat) extends Actor {

  override def receive: Receive = {
    null
  }
}
