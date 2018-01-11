package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}

case class InetServOption1(
  servid: Int,
  optionid: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = InetServOption1.autoSession): InetServOption1 = InetServOption1.save(this)(session)

  def destroy()(implicit session: DBSession = InetServOption1.autoSession): Int = InetServOption1.destroy(this)(session)

}


object InetServOption1 extends SQLSyntaxSupport[InetServOption1] with ApiDbConfig {

  override val tableName = s"inet_serv_option_${bgBillingModuleId("inet")}"

  override val columns = Seq("servId", "optionId", "dateFrom", "dateTo")

  def apply(iso: SyntaxProvider[InetServOption1])(rs: WrappedResultSet): InetServOption1 = autoConstruct(rs, iso)
  def apply(iso: ResultName[InetServOption1])(rs: WrappedResultSet): InetServOption1 = autoConstruct(rs, iso)

  val iso = InetServOption1.syntax("iso")

  override val autoSession = AutoSession

  def find(servid: Int, optionid: Int, datefrom: Option[LocalDate], dateto: Option[LocalDate])(implicit session: DBSession = autoSession): Option[InetServOption1] = {
    withSQL {
      select.from(InetServOption1 as iso).where.eq(iso.servid, servid).and.eq(iso.optionid, optionid).and.eq(iso.datefrom, datefrom).and.eq(iso.dateto, dateto)
    }.map(InetServOption1(iso.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetServOption1] = {
    withSQL(select.from(InetServOption1 as iso)).map(InetServOption1(iso.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetServOption1 as iso)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetServOption1] = {
    withSQL {
      select.from(InetServOption1 as iso).where.append(where)
    }.map(InetServOption1(iso.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetServOption1] = {
    withSQL {
      select.from(InetServOption1 as iso).where.append(where)
    }.map(InetServOption1(iso.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetServOption1 as iso).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    servid: Int,
    optionid: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None)(implicit session: DBSession = autoSession): InetServOption1 = {
    withSQL {
      insert.into(InetServOption1).namedValues(
        column.servid -> servid,
        column.optionid -> optionid,
        column.datefrom -> datefrom,
        column.dateto -> dateto
      )
    }.update.apply()

    InetServOption1(
      servid = servid,
      optionid = optionid,
      datefrom = datefrom,
      dateto = dateto)
  }

  def batchInsert(entities: Seq[InetServOption1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'servid -> entity.servid,
        'optionid -> entity.optionid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto))
    SQL("""insert into inet_serv_option_1(
      servId,
      optionId,
      dateFrom,
      dateTo
    ) values (
      {servid},
      {optionid},
      {datefrom},
      {dateto}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetServOption1)(implicit session: DBSession = autoSession): InetServOption1 = {
    withSQL {
      update(InetServOption1).set(
        column.servid -> entity.servid,
        column.optionid -> entity.optionid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto
      ).where.eq(column.servid, entity.servid).and.eq(column.optionid, entity.optionid).and.eq(column.datefrom, entity.datefrom).and.eq(column.dateto, entity.dateto)
    }.update.apply()
    entity
  }

  def destroy(entity: InetServOption1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetServOption1).where.eq(column.servid, entity.servid).and.eq(column.optionid, entity.optionid).and.eq(column.datefrom, entity.datefrom).and.eq(column.dateto, entity.dateto) }.update.apply()
  }

}
