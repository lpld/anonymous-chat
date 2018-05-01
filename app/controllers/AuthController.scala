package controllers

import db.{ChatUser, Db}
import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author leopold
  * @since 9/04/18
  */
class AuthController @Inject()(cc: ControllerComponents, db: Db)(implicit ectx: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {


  val userForm = Form(
    mapping(
      "login" -> nonEmptyText,
      "name" -> text,
      "password" -> nonEmptyText,
      "passwordRepeat" -> nonEmptyText,
    )(UserData.apply)(UserData.unapply) verifying(
      "Passwords should match", ud => ud.password == ud.passwordRepeat
    )
  )

  def register = Action { implicit request =>
    Ok(views.html.registerUser(userForm))
  }

  def registerPost: Action[AnyContent] = Action.async { implicit request =>
    import db.ctx._

    userForm.bindFromRequest.fold(
      failedForm => Future(Ok(views.html.registerUser(failedForm))),

      form => db.exec {
        run {
          query[ChatUser].insert(
            _.login -> lift(form.login),
            _.passwordHash -> lift(form.password),
            _.name -> lift(form.name)
          )
        }
      } map (_ => Ok)
    )
  }

  def abc(): Unit = {

    import db.ctx._

    quote {
      query[ChatUser].insert(lift(ChatUser(id = 0, login = "", passwordHash = "", name = "")))
    }
  }
}

case class UserData(login: String, name: String, password: String, passwordRepeat: String)
