package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class BonusPrograms(
  id: Int,
  title: Option[String] = None,
  typeid: String,
  datefrom: LocalDate,
  dateto: Option[LocalDate] = None,
  paymenttypeid: Int,
  timelag: String,
  activetime: String,
  params: String) {

  def save()(implicit session: DBSession = BonusPrograms.autoSession): BonusPrograms = BonusPrograms.save(this)(session)

  def destroy()(implicit session: DBSession = BonusPrograms.autoSession): Int = BonusPrograms.destroy(this)(session)

}


object BonusPrograms extends SQLSyntaxSupport[BonusPrograms] {

  override val tableName = "bonus_programs"

  override val columns = Seq("id", "title", "typeId", "dateFrom", "dateTo", "paymentTypeId", "timeLag", "activeTime", "params")

  def apply(bp: SyntaxProvider[BonusPrograms])(rs: WrappedResultSet): BonusPrograms = autoConstruct(rs, bp)
  def apply(bp: ResultName[BonusPrograms])(rs: WrappedResultSet): BonusPrograms = autoConstruct(rs, bp)

  val bp = BonusPrograms.syntax("bp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BonusPrograms] = {
    withSQL {
      select.from(BonusPrograms as bp).where.eq(bp.id, id)
    }.map(BonusPrograms(bp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusPrograms] = {
    withSQL(select.from(BonusPrograms as bp)).map(BonusPrograms(bp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusPrograms as bp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusPrograms] = {
    withSQL {
      select.from(BonusPrograms as bp).where.append(where)
    }.map(BonusPrograms(bp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusPrograms] = {
    withSQL {
      select.from(BonusPrograms as bp).where.append(where)
    }.map(BonusPrograms(bp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusPrograms as bp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None,
    typeid: String,
    datefrom: LocalDate,
    dateto: Option[LocalDate] = None,
    paymenttypeid: Int,
    timelag: String,
    activetime: String,
    params: String)(implicit session: DBSession = autoSession): BonusPrograms = {
    val generatedKey = withSQL {
      insert.into(BonusPrograms).namedValues(
        column.title -> title,
        column.typeid -> typeid,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.paymenttypeid -> paymenttypeid,
        column.timelag -> timelag,
        column.activetime -> activetime,
        column.params -> params
      )
    }.updateAndReturnGeneratedKey.apply()

    BonusPrograms(
      id = generatedKey.toInt,
      title = title,
      typeid = typeid,
      datefrom = datefrom,
      dateto = dateto,
      paymenttypeid = paymenttypeid,
      timelag = timelag,
      activetime = activetime,
      params = params)
  }

  def batchInsert(entities: Seq[BonusPrograms])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'typeid -> entity.typeid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'paymenttypeid -> entity.paymenttypeid,
        'timelag -> entity.timelag,
        'activetime -> entity.activetime,
        'params -> entity.params))
    SQL("""insert into bonus_programs(
      title,
      typeId,
      dateFrom,
      dateTo,
      paymentTypeId,
      timeLag,
      activeTime,
      params
    ) values (
      {title},
      {typeid},
      {datefrom},
      {dateto},
      {paymenttypeid},
      {timelag},
      {activetime},
      {params}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusPrograms)(implicit session: DBSession = autoSession): BonusPrograms = {
    withSQL {
      update(BonusPrograms).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.typeid -> entity.typeid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.paymenttypeid -> entity.paymenttypeid,
        column.timelag -> entity.timelag,
        column.activetime -> entity.activetime,
        column.params -> entity.params
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusPrograms)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusPrograms).where.eq(column.id, entity.id) }.update.apply()
  }

}
