package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}

case class Contract(
  id: Int,
  gr: Long,
  title: String,
  titlePatternId: Int,
  pswd: String,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  mode: Byte,
  closesumma: BigDecimal,
  pgid: Int,
  pfid: Int,
  fc: Boolean,
  comment: String,
  del: Boolean,
  scid: Int,
  subList: String,
  subMode: Byte,
  status: Byte,
  statusDate: Option[LocalDate] = None,
  lastTariffChange: Option[DateTime] = None,
  crmCustomerId: Int) {

  def save()(implicit session: DBSession = Contract.autoSession): Contract = Contract.save(this)(session)

  def destroy()(implicit session: DBSession = Contract.autoSession): Int = Contract.destroy(this)(session)

}


object Contract extends SQLSyntaxSupport[Contract] {

  override val tableName = "contract"

  override val columns = Seq("id", "gr", "title", "title_pattern_id", "pswd", "date1", "date2", "mode", "closesumma", "pgid", "pfid", "fc", "comment", "del", "scid", "sub_list", "sub_mode", "status", "status_date", "last_tariff_change", "crm_customer_id")

  def apply(c: SyntaxProvider[Contract])(rs: WrappedResultSet): Contract = autoConstruct(rs, c)
  def apply(c: ResultName[Contract])(rs: WrappedResultSet): Contract = autoConstruct(rs, c)

  val c = Contract.syntax("c")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Contract] = {
    withSQL {
      select.from(Contract as c).where.eq(c.id, id)
    }.map(Contract(c.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Contract] = {
    withSQL(select.from(Contract as c)).map(Contract(c.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Contract as c)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Contract] = {
    withSQL {
      select.from(Contract as c).where.append(where)
    }.map(Contract(c.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Contract] = {
    withSQL {
      select.from(Contract as c).where.append(where)
    }.map(Contract(c.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Contract as c).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gr: Long,
    title: String,
    titlePatternId: Int,
    pswd: String,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    mode: Byte,
    closesumma: BigDecimal,
    pgid: Int,
    pfid: Int,
    fc: Boolean,
    comment: String,
    del: Boolean,
    scid: Int,
    subList: String,
    subMode: Byte,
    status: Byte,
    statusDate: Option[LocalDate] = None,
    lastTariffChange: Option[DateTime] = None,
    crmCustomerId: Int)(implicit session: DBSession = autoSession): Contract = {
    val generatedKey = withSQL {
      insert.into(Contract).namedValues(
        column.gr -> gr,
        column.title -> title,
        column.titlePatternId -> titlePatternId,
        column.pswd -> pswd,
        column.date1 -> date1,
        column.date2 -> date2,
        column.mode -> mode,
        column.closesumma -> closesumma,
        column.pgid -> pgid,
        column.pfid -> pfid,
        column.fc -> fc,
        column.comment -> comment,
        column.del -> del,
        column.scid -> scid,
        column.subList -> subList,
        column.subMode -> subMode,
        column.status -> status,
        column.statusDate -> statusDate,
        column.lastTariffChange -> lastTariffChange,
        column.crmCustomerId -> crmCustomerId
      )
    }.updateAndReturnGeneratedKey.apply()

    Contract(
      id = generatedKey.toInt,
      gr = gr,
      title = title,
      titlePatternId = titlePatternId,
      pswd = pswd,
      date1 = date1,
      date2 = date2,
      mode = mode,
      closesumma = closesumma,
      pgid = pgid,
      pfid = pfid,
      fc = fc,
      comment = comment,
      del = del,
      scid = scid,
      subList = subList,
      subMode = subMode,
      status = status,
      statusDate = statusDate,
      lastTariffChange = lastTariffChange,
      crmCustomerId = crmCustomerId)
  }

  def batchInsert(entities: Seq[Contract])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gr -> entity.gr,
        'title -> entity.title,
        'titlePatternId -> entity.titlePatternId,
        'pswd -> entity.pswd,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'mode -> entity.mode,
        'closesumma -> entity.closesumma,
        'pgid -> entity.pgid,
        'pfid -> entity.pfid,
        'fc -> entity.fc,
        'comment -> entity.comment,
        'del -> entity.del,
        'scid -> entity.scid,
        'subList -> entity.subList,
        'subMode -> entity.subMode,
        'status -> entity.status,
        'statusDate -> entity.statusDate,
        'lastTariffChange -> entity.lastTariffChange,
        'crmCustomerId -> entity.crmCustomerId))
    SQL("""insert into contract(
      gr,
      title,
      title_pattern_id,
      pswd,
      date1,
      date2,
      mode,
      closesumma,
      pgid,
      pfid,
      fc,
      comment,
      del,
      scid,
      sub_list,
      sub_mode,
      status,
      status_date,
      last_tariff_change,
      crm_customer_id
    ) values (
      {gr},
      {title},
      {titlePatternId},
      {pswd},
      {date1},
      {date2},
      {mode},
      {closesumma},
      {pgid},
      {pfid},
      {fc},
      {comment},
      {del},
      {scid},
      {subList},
      {subMode},
      {status},
      {statusDate},
      {lastTariffChange},
      {crmCustomerId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Contract)(implicit session: DBSession = autoSession): Contract = {
    withSQL {
      update(Contract).set(
        column.id -> entity.id,
        column.gr -> entity.gr,
        column.title -> entity.title,
        column.titlePatternId -> entity.titlePatternId,
        column.pswd -> entity.pswd,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.mode -> entity.mode,
        column.closesumma -> entity.closesumma,
        column.pgid -> entity.pgid,
        column.pfid -> entity.pfid,
        column.fc -> entity.fc,
        column.comment -> entity.comment,
        column.del -> entity.del,
        column.scid -> entity.scid,
        column.subList -> entity.subList,
        column.subMode -> entity.subMode,
        column.status -> entity.status,
        column.statusDate -> entity.statusDate,
        column.lastTariffChange -> entity.lastTariffChange,
        column.crmCustomerId -> entity.crmCustomerId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Contract)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Contract).where.eq(column.id, entity.id) }.update.apply()
  }

}
