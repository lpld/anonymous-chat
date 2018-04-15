package dao

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import model.ChatInfo

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * @author leopold
  * @since 10/04/18
  */
@Singleton
class ChatRepository {

  private val ids: AtomicInteger = new AtomicInteger(1)
  private val chats: mutable.Map[Long, ChatInfo] = new ConcurrentHashMap[Long, ChatInfo]().asScala

  def createChat(key: String, title: String): ChatInfo = {
    val chat = ChatInfo(ids.incrementAndGet(), title, key, None)
    chats(chat.id) = chat
    chat
  }

  def findChat(key: String): Option[ChatInfo] = chats.values.find(_.key == key)

  def getChat(id: Long): Option[ChatInfo] = chats.get(id)
}
