package db

/**
  * @author leopold
  * @since 1/05/18
  */
case class ChatUser(id: Long, login: String, passwordHash: String, name: String)
