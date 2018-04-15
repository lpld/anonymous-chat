package dao

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import model.Chat

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * @author leopold
  * @since 10/04/18
  */
@Singleton
class ChatRepository {

  private val ids: AtomicInteger = new AtomicInteger(1)
  private val chats: mutable.Map[Long, Chat] = new ConcurrentHashMap[Long, Chat]().asScala

  def createChat(key: String, title: String): Chat = {
    val chat = Chat(ids.incrementAndGet(), title, key, None)
    chats(chat.id) = chat
    chat
  }

  def findChat(key: String): Option[Chat] = chats.values.find(_.key == key)

  def getChat(id: Long): Option[Chat] = chats.get(id)
}
