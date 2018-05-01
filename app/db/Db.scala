package db

import java.io.Closeable

import helpers.Contexts
import io.getquill._
import javax.inject.Inject
import javax.sql.DataSource
import play.api.db.Database

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author leopold
  * @since 1/05/18
  */
class Db @Inject()(playDb: Database, contexts: Contexts) {

  val ctx = new DbContext(SnakeCase, playDb.dataSource.asInstanceOf[DataSource with Closeable])

  implicit val exCtx: ExecutionContext = contexts.database

  def exec[T](uow: => T) = Future(uow)
}
