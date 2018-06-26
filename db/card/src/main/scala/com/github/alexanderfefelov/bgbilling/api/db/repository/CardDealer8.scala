package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class CardDealer8(
  id: Int,
  `type`: Int,
  title: String,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  login: String,
  pswd: Option[String] = None,
  pt: Option[String] = None,
  comment: String,
  error: Int,
  ip: String,
  params: Option[Int] = None,
  findmodes: Long,
  allowcontracts: Long,
  canselTime: Option[Int] = None,
  stRequest: Int,
  stFinded: Int,
  stPayed: Int,
  cid: Int,
  cardPercent: Float,
  payPercent: Float,
  cardContract: String,
  payContract: String,
  payComissionType: Byte,
  contractId: Int) {

  def save()(implicit session: DBSession = CardDealer8.autoSession): CardDealer8 = CardDealer8.save(this)(session)

  def destroy()(implicit session: DBSession = CardDealer8.autoSession): Int = CardDealer8.destroy(this)(session)

}


object CardDealer8 extends SQLSyntaxSupport[CardDealer8] with ApiDbConfig {

  override val tableName = s"card_dealer_${bgBillingModuleId("card")}"

  override val columns = Seq("id", "type", "title", "date1", "date2", "login", "pswd", "pt", "comment", "error", "ip", "params", "findmodes", "allowcontracts", "cansel_time", "st_request", "st_finded", "st_payed", "cid", "card_percent", "pay_percent", "card_contract", "pay_contract", "pay_comission_type", "contract_id")

  def apply(cd: SyntaxProvider[CardDealer8])(rs: WrappedResultSet): CardDealer8 = autoConstruct(rs, cd)
  def apply(cd: ResultName[CardDealer8])(rs: WrappedResultSet): CardDealer8 = autoConstruct(rs, cd)

  val cd = CardDealer8.syntax("cd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CardDealer8] = {
    withSQL {
      select.from(CardDealer8 as cd).where.eq(cd.id, id)
    }.map(CardDealer8(cd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CardDealer8] = {
    withSQL(select.from(CardDealer8 as cd)).map(CardDealer8(cd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CardDealer8 as cd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CardDealer8] = {
    withSQL {
      select.from(CardDealer8 as cd).where.append(where)
    }.map(CardDealer8(cd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CardDealer8] = {
    withSQL {
      select.from(CardDealer8 as cd).where.append(where)
    }.map(CardDealer8(cd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CardDealer8 as cd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    `type`: Int,
    title: String,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    login: String,
    pswd: Option[String] = None,
    pt: Option[String] = None,
    comment: String,
    error: Int,
    ip: String,
    params: Option[Int] = None,
    findmodes: Long,
    allowcontracts: Long,
    canselTime: Option[Int] = None,
    stRequest: Int,
    stFinded: Int,
    stPayed: Int,
    cid: Int,
    cardPercent: Float,
    payPercent: Float,
    cardContract: String,
    payContract: String,
    payComissionType: Byte,
    contractId: Int)(implicit session: DBSession = autoSession): CardDealer8 = {
    val generatedKey = withSQL {
      insert.into(CardDealer8).namedValues(
        column.`type` -> `type`,
        column.title -> title,
        column.date1 -> date1,
        column.date2 -> date2,
        column.login -> login,
        column.pswd -> pswd,
        column.pt -> pt,
        column.comment -> comment,
        column.error -> error,
        column.ip -> ip,
        column.params -> params,
        column.findmodes -> findmodes,
        column.allowcontracts -> allowcontracts,
        column.canselTime -> canselTime,
        column.stRequest -> stRequest,
        column.stFinded -> stFinded,
        column.stPayed -> stPayed,
        column.cid -> cid,
        column.cardPercent -> cardPercent,
        column.payPercent -> payPercent,
        column.cardContract -> cardContract,
        column.payContract -> payContract,
        column.payComissionType -> payComissionType,
        column.contractId -> contractId
      )
    }.updateAndReturnGeneratedKey.apply()

    new CardDealer8(
      id = generatedKey.toInt,
      `type` = `type`,
      title = title,
      date1 = date1,
      date2 = date2,
      login = login,
      pswd = pswd,
      pt = pt,
      comment = comment,
      error = error,
      ip = ip,
      params = params,
      findmodes = findmodes,
      allowcontracts = allowcontracts,
      canselTime = canselTime,
      stRequest = stRequest,
      stFinded = stFinded,
      stPayed = stPayed,
      cid = cid,
      cardPercent = cardPercent,
      payPercent = payPercent,
      cardContract = cardContract,
      payContract = payContract,
      payComissionType = payComissionType,
      contractId = contractId)
  }

  def batchInsert(entities: Seq[CardDealer8])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'type -> entity.`type`,
        'title -> entity.title,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'login -> entity.login,
        'pswd -> entity.pswd,
        'pt -> entity.pt,
        'comment -> entity.comment,
        'error -> entity.error,
        'ip -> entity.ip,
        'params -> entity.params,
        'findmodes -> entity.findmodes,
        'allowcontracts -> entity.allowcontracts,
        'canselTime -> entity.canselTime,
        'stRequest -> entity.stRequest,
        'stFinded -> entity.stFinded,
        'stPayed -> entity.stPayed,
        'cid -> entity.cid,
        'cardPercent -> entity.cardPercent,
        'payPercent -> entity.payPercent,
        'cardContract -> entity.cardContract,
        'payContract -> entity.payContract,
        'payComissionType -> entity.payComissionType,
        'contractId -> entity.contractId))
    SQL("""insert into card_dealer_8(
      type,
      title,
      date1,
      date2,
      login,
      pswd,
      pt,
      comment,
      error,
      ip,
      params,
      findmodes,
      allowcontracts,
      cansel_time,
      st_request,
      st_finded,
      st_payed,
      cid,
      card_percent,
      pay_percent,
      card_contract,
      pay_contract,
      pay_comission_type,
      contract_id
    ) values (
      {type},
      {title},
      {date1},
      {date2},
      {login},
      {pswd},
      {pt},
      {comment},
      {error},
      {ip},
      {params},
      {findmodes},
      {allowcontracts},
      {canselTime},
      {stRequest},
      {stFinded},
      {stPayed},
      {cid},
      {cardPercent},
      {payPercent},
      {cardContract},
      {payContract},
      {payComissionType},
      {contractId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CardDealer8)(implicit session: DBSession = autoSession): CardDealer8 = {
    withSQL {
      update(CardDealer8).set(
        column.id -> entity.id,
        column.`type` -> entity.`type`,
        column.title -> entity.title,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.login -> entity.login,
        column.pswd -> entity.pswd,
        column.pt -> entity.pt,
        column.comment -> entity.comment,
        column.error -> entity.error,
        column.ip -> entity.ip,
        column.params -> entity.params,
        column.findmodes -> entity.findmodes,
        column.allowcontracts -> entity.allowcontracts,
        column.canselTime -> entity.canselTime,
        column.stRequest -> entity.stRequest,
        column.stFinded -> entity.stFinded,
        column.stPayed -> entity.stPayed,
        column.cid -> entity.cid,
        column.cardPercent -> entity.cardPercent,
        column.payPercent -> entity.payPercent,
        column.cardContract -> entity.cardContract,
        column.payContract -> entity.payContract,
        column.payComissionType -> entity.payComissionType,
        column.contractId -> entity.contractId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CardDealer8)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CardDealer8).where.eq(column.id, entity.id) }.update.apply()
  }

}
