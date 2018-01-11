package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserModuleAction(
  id: Int,
  module: String,
  `type`: Int,
  action: String,
  description: Option[String] = None) {

  def save()(implicit session: DBSession = UserModuleAction.autoSession): UserModuleAction = UserModuleAction.save(this)(session)

  def destroy()(implicit session: DBSession = UserModuleAction.autoSession): Int = UserModuleAction.destroy(this)(session)

}


object UserModuleAction extends SQLSyntaxSupport[UserModuleAction] {

  override val tableName = "user_module_action"

  override val columns = Seq("id", "module", "type", "action", "description")

  def apply(uma: SyntaxProvider[UserModuleAction])(rs: WrappedResultSet): UserModuleAction = autoConstruct(rs, uma)
  def apply(uma: ResultName[UserModuleAction])(rs: WrappedResultSet): UserModuleAction = autoConstruct(rs, uma)

  val uma = UserModuleAction.syntax("uma")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[UserModuleAction] = {
    withSQL {
      select.from(UserModuleAction as uma).where.eq(uma.id, id)
    }.map(UserModuleAction(uma.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserModuleAction] = {
    withSQL(select.from(UserModuleAction as uma)).map(UserModuleAction(uma.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserModuleAction as uma)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserModuleAction] = {
    withSQL {
      select.from(UserModuleAction as uma).where.append(where)
    }.map(UserModuleAction(uma.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserModuleAction] = {
    withSQL {
      select.from(UserModuleAction as uma).where.append(where)
    }.map(UserModuleAction(uma.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserModuleAction as uma).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    module: String,
    `type`: Int,
    action: String,
    description: Option[String] = None)(implicit session: DBSession = autoSession): UserModuleAction = {
    val generatedKey = withSQL {
      insert.into(UserModuleAction).namedValues(
        column.module -> module,
        column.`type` -> `type`,
        column.action -> action,
        column.description -> description
      )
    }.updateAndReturnGeneratedKey.apply()

    UserModuleAction(
      id = generatedKey.toInt,
      module = module,
      `type` = `type`,
      action = action,
      description = description)
  }

  def batchInsert(entities: Seq[UserModuleAction])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'module -> entity.module,
        'type -> entity.`type`,
        'action -> entity.action,
        'description -> entity.description))
    SQL("""insert into user_module_action(
      module,
      type,
      action,
      description
    ) values (
      {module},
      {type},
      {action},
      {description}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserModuleAction)(implicit session: DBSession = autoSession): UserModuleAction = {
    withSQL {
      update(UserModuleAction).set(
        column.id -> entity.id,
        column.module -> entity.module,
        column.`type` -> entity.`type`,
        column.action -> entity.action,
        column.description -> entity.description
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: UserModuleAction)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserModuleAction).where.eq(column.id, entity.id) }.update.apply()
  }

}
