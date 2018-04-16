package controllers

import actors._
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.stream.Materializer
import akka.util.Timeout
import javax.inject.{Inject, Named}
import play.api.Logger
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationLong

/**
  * @author leopold
  * @since 9/04/18
  */
class ChatController @Inject()(@Named("chatEngine") chatEngine: ActorRef, cc: ControllerComponents)
                              (implicit ec: ExecutionContext, ac: ActorSystem, mat: Materializer)
  extends AbstractController(cc) {

  def displayChat = Action.async { implicit request =>
    val form = request.body.asFormUrlEncoded.get
    val username = form("username").head
    val title = form("title").head
    val key = form("key").head

    implicit val to: Timeout = 10.seconds

    (chatEngine ? FindOrCreateRoom(key, title))
      .map(_ =>
        Ok(views.html.displayChat(username, title))
          .withSession("chatKey" -> key, "username" -> username)
      )

  }

  def connect = WebSocket.acceptOrResult[String, String] { request =>
    val username = request.session("username")
    Logger.info(s"New websocket connection: $username")
    implicit val to: Timeout = 10.seconds
    for {
      room <- (chatEngine ? GetRoom(request.session("chatKey"))).mapTo[ActorRef]
      _ = Logger.info(s"Found chat for user $username")
    } yield Right(ActorFlow.actorRef(source => Props(classOf[ChatConnection], room, source, username)))
  }
}
