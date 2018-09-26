package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractTariffOption(
  id: Int,
  uid: Int,
  cid: Int,
  optionId: Int,
  timeFrom: DateTime,
  timeTo: Option[DateTime] = None,
  chargeId: Int,
  summa: BigDecimal,
  activatedMode: Int,
  activatedTime: DateTime,
  deactivatedTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = ContractTariffOption.autoSession): ContractTariffOption = ContractTariffOption.save(this)(session)

  def destroy()(implicit session: DBSession = ContractTariffOption.autoSession): Int = ContractTariffOption.destroy(this)(session)

}


object ContractTariffOption extends SQLSyntaxSupport[ContractTariffOption] {

  override val tableName = "contract_tariff_option"

  override val columns = Seq("id", "uid", "cid", "option_id", "time_from", "time_to", "charge_id", "summa", "activated_mode", "activated_time", "deactivated_time")

  def apply(cto: SyntaxProvider[ContractTariffOption])(rs: WrappedResultSet): ContractTariffOption = autoConstruct(rs, cto)
  def apply(cto: ResultName[ContractTariffOption])(rs: WrappedResultSet): ContractTariffOption = autoConstruct(rs, cto)

  val cto = ContractTariffOption.syntax("cto")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractTariffOption] = {
    withSQL {
      select.from(ContractTariffOption as cto).where.eq(cto.id, id)
    }.map(ContractTariffOption(cto.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractTariffOption] = {
    withSQL(select.from(ContractTariffOption as cto)).map(ContractTariffOption(cto.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractTariffOption as cto)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractTariffOption] = {
    withSQL {
      select.from(ContractTariffOption as cto).where.append(where)
    }.map(ContractTariffOption(cto.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractTariffOption] = {
    withSQL {
      select.from(ContractTariffOption as cto).where.append(where)
    }.map(ContractTariffOption(cto.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractTariffOption as cto).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uid: Int,
    cid: Int,
    optionId: Int,
    timeFrom: DateTime,
    timeTo: Option[DateTime] = None,
    chargeId: Int,
    summa: BigDecimal,
    activatedMode: Int,
    activatedTime: DateTime,
    deactivatedTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): ContractTariffOption = {
    val generatedKey = withSQL {
      insert.into(ContractTariffOption).namedValues(
        column.uid -> uid,
        column.cid -> cid,
        column.optionId -> optionId,
        column.timeFrom -> timeFrom,
        column.timeTo -> timeTo,
        column.chargeId -> chargeId,
        column.summa -> summa,
        column.activatedMode -> activatedMode,
        column.activatedTime -> activatedTime,
        column.deactivatedTime -> deactivatedTime
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractTariffOption(
      id = generatedKey.toInt,
      uid = uid,
      cid = cid,
      optionId = optionId,
      timeFrom = timeFrom,
      timeTo = timeTo,
      chargeId = chargeId,
      summa = summa,
      activatedMode = activatedMode,
      activatedTime = activatedTime,
      deactivatedTime = deactivatedTime)
  }

  def batchInsert(entities: collection.Seq[ContractTariffOption])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uid -> entity.uid,
        'cid -> entity.cid,
        'optionId -> entity.optionId,
        'timeFrom -> entity.timeFrom,
        'timeTo -> entity.timeTo,
        'chargeId -> entity.chargeId,
        'summa -> entity.summa,
        'activatedMode -> entity.activatedMode,
        'activatedTime -> entity.activatedTime,
        'deactivatedTime -> entity.deactivatedTime))
    SQL("""insert into contract_tariff_option(
      uid,
      cid,
      option_id,
      time_from,
      time_to,
      charge_id,
      summa,
      activated_mode,
      activated_time,
      deactivated_time
    ) values (
      {uid},
      {cid},
      {optionId},
      {timeFrom},
      {timeTo},
      {chargeId},
      {summa},
      {activatedMode},
      {activatedTime},
      {deactivatedTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractTariffOption)(implicit session: DBSession = autoSession): ContractTariffOption = {
    withSQL {
      update(ContractTariffOption).set(
        column.id -> entity.id,
        column.uid -> entity.uid,
        column.cid -> entity.cid,
        column.optionId -> entity.optionId,
        column.timeFrom -> entity.timeFrom,
        column.timeTo -> entity.timeTo,
        column.chargeId -> entity.chargeId,
        column.summa -> entity.summa,
        column.activatedMode -> entity.activatedMode,
        column.activatedTime -> entity.activatedTime,
        column.deactivatedTime -> entity.deactivatedTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractTariffOption)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractTariffOption).where.eq(column.id, entity.id) }.update.apply()
  }

}
