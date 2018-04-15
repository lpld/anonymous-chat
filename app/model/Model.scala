package model

import java.time.Instant

/**
  * @author leopold
  * @since 15/04/18
  */

case class ChatInfo(id: Long, key: String, title: String, owner: Option[User])

case class ChatMessage(id: Long, author: Option[User], authorName: String, text: String, time: Instant)

case class User()
