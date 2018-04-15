package controllers

import java.time.Instant

import akka.actor.{ActorSystem, Props}
import dao.ChatRepository
import javax.inject.Inject
import model.{ChatEngine, ChatMessage}
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * @author leopold
  * @since 9/04/18
  */
class ChatController @Inject()(system: ActorSystem,
                               chatRepo: ChatRepository,
                               cc: ControllerComponents) extends AbstractController(cc) {

  def selectChat = Action {
    Ok(views.html.selectChat())
  }

  def createChat(key: String, title: String) = Action {
    Ok
  }

  def findChat(key: String) = Action {
    chatRepo.findChat(key)
      .map(chat => Redirect(s"chats/${chat.id}"))
      .getOrElse(NotFound)
  }

  def displayChat(id: Long) = Action {
    chatRepo
      .getChat(id)
      .map { chat =>
        val chatActor = system.actorOf(Props(classOf[ChatEngine], chat), s"chat-engine-$id")

        Ok(views.html.displayChat(
          chat,
          Seq(
            ChatMessage(id = 1, author = None, authorName = "user1", text = "Test 1234", time = Instant.now() minusSeconds 120),
            ChatMessage(id = 2, author = None, authorName = "user2", text = "Test 2345", time = Instant.now() minusSeconds 60),
            ChatMessage(id = 3, author = None, authorName = "user1", text = "Test 4567", time = Instant.now())
          )
        ))

      } getOrElse {
      NotFound
    }

  }
}
