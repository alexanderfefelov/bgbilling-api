package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}

case class CardData8(
  id: Int,
  cardCode: Int,
  csId: Int,
  cardPinCode: String,
  summa: Float,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  status: Byte,
  sidPay: Int,
  sidAct: Int,
  pid: Int,
  pt: Int,
  did: Int,
  dDate: Option[LocalDate] = None,
  cid: Int,
  date: Option[DateTime] = None) {

  def save()(implicit session: DBSession = CardData8.autoSession): CardData8 = CardData8.save(this)(session)

  def destroy()(implicit session: DBSession = CardData8.autoSession): Int = CardData8.destroy(this)(session)

}


object CardData8 extends SQLSyntaxSupport[CardData8] with ApiDbConfig {

  override val tableName = s"card_data_8${bgBillingModuleId("card")}"

  override val columns = Seq("id", "card_code", "cs_id", "card_pin_code", "summa", "date1", "date2", "status", "sid_pay", "sid_act", "pid", "pt", "did", "d_date", "cid", "date")

  def apply(cd: SyntaxProvider[CardData8])(rs: WrappedResultSet): CardData8 = autoConstruct(rs, cd)
  def apply(cd: ResultName[CardData8])(rs: WrappedResultSet): CardData8 = autoConstruct(rs, cd)

  val cd = CardData8.syntax("cd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CardData8] = {
    withSQL {
      select.from(CardData8 as cd).where.eq(cd.id, id)
    }.map(CardData8(cd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CardData8] = {
    withSQL(select.from(CardData8 as cd)).map(CardData8(cd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CardData8 as cd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CardData8] = {
    withSQL {
      select.from(CardData8 as cd).where.append(where)
    }.map(CardData8(cd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CardData8] = {
    withSQL {
      select.from(CardData8 as cd).where.append(where)
    }.map(CardData8(cd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CardData8 as cd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cardCode: Int,
    csId: Int,
    cardPinCode: String,
    summa: Float,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    status: Byte,
    sidPay: Int,
    sidAct: Int,
    pid: Int,
    pt: Int,
    did: Int,
    dDate: Option[LocalDate] = None,
    cid: Int,
    date: Option[DateTime] = None)(implicit session: DBSession = autoSession): CardData8 = {
    val generatedKey = withSQL {
      insert.into(CardData8).namedValues(
        column.cardCode -> cardCode,
        column.csId -> csId,
        column.cardPinCode -> cardPinCode,
        column.summa -> summa,
        column.date1 -> date1,
        column.date2 -> date2,
        column.status -> status,
        column.sidPay -> sidPay,
        column.sidAct -> sidAct,
        column.pid -> pid,
        column.pt -> pt,
        column.did -> did,
        column.dDate -> dDate,
        column.cid -> cid,
        column.date -> date
      )
    }.updateAndReturnGeneratedKey.apply()

    CardData8(
      id = generatedKey.toInt,
      cardCode = cardCode,
      csId = csId,
      cardPinCode = cardPinCode,
      summa = summa,
      date1 = date1,
      date2 = date2,
      status = status,
      sidPay = sidPay,
      sidAct = sidAct,
      pid = pid,
      pt = pt,
      did = did,
      dDate = dDate,
      cid = cid,
      date = date)
  }

  def batchInsert(entities: Seq[CardData8])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cardCode -> entity.cardCode,
        'csId -> entity.csId,
        'cardPinCode -> entity.cardPinCode,
        'summa -> entity.summa,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'status -> entity.status,
        'sidPay -> entity.sidPay,
        'sidAct -> entity.sidAct,
        'pid -> entity.pid,
        'pt -> entity.pt,
        'did -> entity.did,
        'dDate -> entity.dDate,
        'cid -> entity.cid,
        'date -> entity.date))
    SQL("""insert into card_data_8(
      card_code,
      cs_id,
      card_pin_code,
      summa,
      date1,
      date2,
      status,
      sid_pay,
      sid_act,
      pid,
      pt,
      did,
      d_date,
      cid,
      date
    ) values (
      {cardCode},
      {csId},
      {cardPinCode},
      {summa},
      {date1},
      {date2},
      {status},
      {sidPay},
      {sidAct},
      {pid},
      {pt},
      {did},
      {dDate},
      {cid},
      {date}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CardData8)(implicit session: DBSession = autoSession): CardData8 = {
    withSQL {
      update(CardData8).set(
        column.id -> entity.id,
        column.cardCode -> entity.cardCode,
        column.csId -> entity.csId,
        column.cardPinCode -> entity.cardPinCode,
        column.summa -> entity.summa,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.status -> entity.status,
        column.sidPay -> entity.sidPay,
        column.sidAct -> entity.sidAct,
        column.pid -> entity.pid,
        column.pt -> entity.pt,
        column.did -> entity.did,
        column.dDate -> entity.dDate,
        column.cid -> entity.cid,
        column.date -> entity.date
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CardData8)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CardData8).where.eq(column.id, entity.id) }.update.apply()
  }

}
