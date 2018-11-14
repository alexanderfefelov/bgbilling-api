package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectParam(
  id: Int,
  title: String,
  `type`: Boolean,
  comment: String,
  flags: Option[Byte] = None) {

  def save()(implicit session: DBSession = ObjectParam.autoSession): ObjectParam = ObjectParam.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParam.autoSession): Int = ObjectParam.destroy(this)(session)

}


object ObjectParam extends SQLSyntaxSupport[ObjectParam] {

  override val tableName = "object_param"

  override val columns = Seq("id", "title", "type", "comment", "flags")

  def apply(op: SyntaxProvider[ObjectParam])(rs: WrappedResultSet): ObjectParam = autoConstruct(rs, op)
  def apply(op: ResultName[ObjectParam])(rs: WrappedResultSet): ObjectParam = autoConstruct(rs, op)

  val op = ObjectParam.syntax("op")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ObjectParam] = {
    withSQL {
      select.from(ObjectParam as op).where.eq(op.id, id)
    }.map(ObjectParam(op.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParam] = {
    withSQL(select.from(ObjectParam as op)).map(ObjectParam(op.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParam as op)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParam] = {
    withSQL {
      select.from(ObjectParam as op).where.append(where)
    }.map(ObjectParam(op.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParam] = {
    withSQL {
      select.from(ObjectParam as op).where.append(where)
    }.map(ObjectParam(op.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParam as op).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    `type`: Boolean,
    comment: String,
    flags: Option[Byte] = None)(implicit session: DBSession = autoSession): ObjectParam = {
    val generatedKey = withSQL {
      insert.into(ObjectParam).namedValues(
        column.title -> title,
        column.`type` -> `type`,
        column.comment -> comment,
        column.flags -> flags
      )
    }.updateAndReturnGeneratedKey.apply()

    ObjectParam(
      id = generatedKey.toInt,
      title = title,
      `type` = `type`,
      comment = comment,
      flags = flags)
  }

  def batchInsert(entities: collection.Seq[ObjectParam])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'type -> entity.`type`,
        'comment -> entity.comment,
        'flags -> entity.flags))
    SQL("""insert into object_param(
      title,
      type,
      comment,
      flags
    ) values (
      {title},
      {type},
      {comment},
      {flags}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectParam)(implicit session: DBSession = autoSession): ObjectParam = {
    withSQL {
      update(ObjectParam).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.`type` -> entity.`type`,
        column.comment -> entity.comment,
        column.flags -> entity.flags
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParam)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParam).where.eq(column.id, entity.id) }.update.apply()
  }

}
