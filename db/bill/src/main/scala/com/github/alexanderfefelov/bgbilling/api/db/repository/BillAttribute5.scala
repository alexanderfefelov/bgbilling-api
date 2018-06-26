package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class BillAttribute5(
  id: Int,
  cid: Int,
  name: String,
  value: String,
  start: Option[LocalDate] = None,
  end: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = BillAttribute5.autoSession): BillAttribute5 = BillAttribute5.save(this)(session)

  def destroy()(implicit session: DBSession = BillAttribute5.autoSession): Int = BillAttribute5.destroy(this)(session)

}


object BillAttribute5 extends SQLSyntaxSupport[BillAttribute5] with ApiDbConfig {

  override val tableName = s"bill_attribute_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "cid", "name", "value", "start", "end")

  def apply(ba: SyntaxProvider[BillAttribute5])(rs: WrappedResultSet): BillAttribute5 = autoConstruct(rs, ba)
  def apply(ba: ResultName[BillAttribute5])(rs: WrappedResultSet): BillAttribute5 = autoConstruct(rs, ba)

  val ba = BillAttribute5.syntax("ba")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillAttribute5] = {
    withSQL {
      select.from(BillAttribute5 as ba).where.eq(ba.id, id)
    }.map(BillAttribute5(ba.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillAttribute5] = {
    withSQL(select.from(BillAttribute5 as ba)).map(BillAttribute5(ba.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillAttribute5 as ba)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillAttribute5] = {
    withSQL {
      select.from(BillAttribute5 as ba).where.append(where)
    }.map(BillAttribute5(ba.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillAttribute5] = {
    withSQL {
      select.from(BillAttribute5 as ba).where.append(where)
    }.map(BillAttribute5(ba.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillAttribute5 as ba).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    name: String,
    value: String,
    start: Option[LocalDate] = None,
    end: Option[LocalDate] = None)(implicit session: DBSession = autoSession): BillAttribute5 = {
    val generatedKey = withSQL {
      insert.into(BillAttribute5).namedValues(
        column.cid -> cid,
        column.name -> name,
        column.value -> value,
        column.start -> start,
        column.end -> end
      )
    }.updateAndReturnGeneratedKey.apply()

    BillAttribute5(
      id = generatedKey.toInt,
      cid = cid,
      name = name,
      value = value,
      start = start,
      end = end)
  }

  def batchInsert(entities: Seq[BillAttribute5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'name -> entity.name,
        'value -> entity.value,
        'start -> entity.start,
        'end -> entity.end))
    SQL("""insert into bill_attribute_5(
      cid,
      name,
      value,
      start,
      end
    ) values (
      {cid},
      {name},
      {value},
      {start},
      {end}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillAttribute5)(implicit session: DBSession = autoSession): BillAttribute5 = {
    withSQL {
      update(BillAttribute5).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.name -> entity.name,
        column.value -> entity.value,
        column.start -> entity.start,
        column.end -> entity.end
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillAttribute5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillAttribute5).where.eq(column.id, entity.id) }.update.apply()
  }

}
