import io.getquill.{PostgresJdbcContext, SnakeCase}

/**
  * @author leopold
  * @since 1/05/18
  */
package object db {
  type DbContext = PostgresJdbcContext[SnakeCase]
}
