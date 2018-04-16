package actors

import akka.actor
import akka.actor.{Actor, ActorLogging, ActorRef}
import com.google.inject.AbstractModule
import javax.inject.Inject
import play.api.libs.concurrent.AkkaGuiceSupport

import scala.collection.immutable.HashMap

/**
  * @author leopold
  * @since 10/04/18
  */
class ChatEngine @Inject() extends Actor with ActorLogging {

  private var chats: Map[String, ActorRef] = new HashMap[String, ActorRef]

  override def preStart(): Unit = log.info("ChatEngine started")

  override def receive: Receive = {
    case FindOrCreateRoom(key, title) =>
      sender() ! chats.getOrElse(key, {
        log.info(s"Creating new chat $title with key $key")
        val newChat: actor.ActorRef = context.actorOf(ChatRoom(key, title))
        chats = chats + (key -> newChat)
        newChat
      })
    case GetRoom(key) => sender() ! chats(key)
  }
}

class ChatEngineModule extends AbstractModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bindActor[ChatEngine]("chatEngine")
  }
}
