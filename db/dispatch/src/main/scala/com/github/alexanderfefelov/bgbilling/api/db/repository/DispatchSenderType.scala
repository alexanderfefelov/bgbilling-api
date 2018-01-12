package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class DispatchSenderType(
  id: Int,
  title: String,
  className: String) {

  def save()(implicit session: DBSession = DispatchSenderType.autoSession): DispatchSenderType = DispatchSenderType.save(this)(session)

  def destroy()(implicit session: DBSession = DispatchSenderType.autoSession): Int = DispatchSenderType.destroy(this)(session)

}


object DispatchSenderType extends SQLSyntaxSupport[DispatchSenderType] {

  override val tableName = "dispatch_sender_type"

  override val columns = Seq("id", "title", "class_name")

  def apply(dst: SyntaxProvider[DispatchSenderType])(rs: WrappedResultSet): DispatchSenderType = autoConstruct(rs, dst)
  def apply(dst: ResultName[DispatchSenderType])(rs: WrappedResultSet): DispatchSenderType = autoConstruct(rs, dst)

  val dst = DispatchSenderType.syntax("dst")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DispatchSenderType] = {
    withSQL {
      select.from(DispatchSenderType as dst).where.eq(dst.id, id)
    }.map(DispatchSenderType(dst.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatchSenderType] = {
    withSQL(select.from(DispatchSenderType as dst)).map(DispatchSenderType(dst.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatchSenderType as dst)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatchSenderType] = {
    withSQL {
      select.from(DispatchSenderType as dst).where.append(where)
    }.map(DispatchSenderType(dst.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatchSenderType] = {
    withSQL {
      select.from(DispatchSenderType as dst).where.append(where)
    }.map(DispatchSenderType(dst.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatchSenderType as dst).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    className: String)(implicit session: DBSession = autoSession): DispatchSenderType = {
    val generatedKey = withSQL {
      insert.into(DispatchSenderType).namedValues(
        column.title -> title,
        column.className -> className
      )
    }.updateAndReturnGeneratedKey.apply()

    DispatchSenderType(
      id = generatedKey.toInt,
      title = title,
      className = className)
  }

  def batchInsert(entities: Seq[DispatchSenderType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'className -> entity.className))
    SQL("""insert into dispatch_sender_type(
      title,
      class_name
    ) values (
      {title},
      {className}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: DispatchSenderType)(implicit session: DBSession = autoSession): DispatchSenderType = {
    withSQL {
      update(DispatchSenderType).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.className -> entity.className
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatchSenderType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatchSenderType).where.eq(column.id, entity.id) }.update.apply()
  }

}
