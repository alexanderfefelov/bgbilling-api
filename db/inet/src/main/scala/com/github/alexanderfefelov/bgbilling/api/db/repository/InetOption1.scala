package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetOption1(
  id: Int,
  parentid: Int,
  title: String,
  groupintersection: Byte,
  config: String,
  comment: String) {

  def save()(implicit session: DBSession = InetOption1.autoSession): InetOption1 = InetOption1.save(this)(session)

  def destroy()(implicit session: DBSession = InetOption1.autoSession): Int = InetOption1.destroy(this)(session)

}


object InetOption1 extends SQLSyntaxSupport[InetOption1] with ApiDbConfig {

  override val tableName = s"inet_option_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "parentId", "title", "groupIntersection", "config", "comment")

  def apply(io: SyntaxProvider[InetOption1])(rs: WrappedResultSet): InetOption1 = autoConstruct(rs, io)
  def apply(io: ResultName[InetOption1])(rs: WrappedResultSet): InetOption1 = autoConstruct(rs, io)

  val io = InetOption1.syntax("io")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetOption1] = {
    withSQL {
      select.from(InetOption1 as io).where.eq(io.id, id)
    }.map(InetOption1(io.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetOption1] = {
    withSQL(select.from(InetOption1 as io)).map(InetOption1(io.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetOption1 as io)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetOption1] = {
    withSQL {
      select.from(InetOption1 as io).where.append(where)
    }.map(InetOption1(io.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetOption1] = {
    withSQL {
      select.from(InetOption1 as io).where.append(where)
    }.map(InetOption1(io.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetOption1 as io).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Int,
    title: String,
    groupintersection: Byte,
    config: String,
    comment: String)(implicit session: DBSession = autoSession): InetOption1 = {
    val generatedKey = withSQL {
      insert.into(InetOption1).namedValues(
        column.parentid -> parentid,
        column.title -> title,
        column.groupintersection -> groupintersection,
        column.config -> config,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    InetOption1(
      id = generatedKey.toInt,
      parentid = parentid,
      title = title,
      groupintersection = groupintersection,
      config = config,
      comment = comment)
  }

  def batchInsert(entities: Seq[InetOption1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentid -> entity.parentid,
        'title -> entity.title,
        'groupintersection -> entity.groupintersection,
        'config -> entity.config,
        'comment -> entity.comment))
    SQL("""insert into inet_option_1(
      parentId,
      title,
      groupIntersection,
      config,
      comment
    ) values (
      {parentid},
      {title},
      {groupintersection},
      {config},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetOption1)(implicit session: DBSession = autoSession): InetOption1 = {
    withSQL {
      update(InetOption1).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.title -> entity.title,
        column.groupintersection -> entity.groupintersection,
        column.config -> entity.config,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetOption1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetOption1).where.eq(column.id, entity.id) }.update.apply()
  }

}
