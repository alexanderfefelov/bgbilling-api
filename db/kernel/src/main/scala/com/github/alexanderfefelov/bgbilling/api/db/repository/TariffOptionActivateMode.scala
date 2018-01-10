package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class TariffOptionActivateMode(
  id: Int,
  optionId: Int,
  title: String,
  chargeTypeId: Int,
  chargeSumma: BigDecimal,
  periodMode: Int,
  periodCol: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  deactivationMode: Int,
  reactivationMode: Int,
  deleteMode: Int,
  deleteChargeMode: Option[Int] = None) {

  def save()(implicit session: DBSession = TariffOptionActivateMode.autoSession): TariffOptionActivateMode = TariffOptionActivateMode.save(this)(session)

  def destroy()(implicit session: DBSession = TariffOptionActivateMode.autoSession): Int = TariffOptionActivateMode.destroy(this)(session)

}


object TariffOptionActivateMode extends SQLSyntaxSupport[TariffOptionActivateMode] {

  override val tableName = "tariff_option_activate_mode"

  override val columns = Seq("id", "option_id", "title", "charge_type_id", "charge_summa", "period_mode", "period_col", "date1", "date2", "deactivation_mode", "reactivation_mode", "delete_mode", "delete_charge_mode")

  def apply(toam: SyntaxProvider[TariffOptionActivateMode])(rs: WrappedResultSet): TariffOptionActivateMode = autoConstruct(rs, toam)
  def apply(toam: ResultName[TariffOptionActivateMode])(rs: WrappedResultSet): TariffOptionActivateMode = autoConstruct(rs, toam)

  val toam = TariffOptionActivateMode.syntax("toam")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TariffOptionActivateMode] = {
    withSQL {
      select.from(TariffOptionActivateMode as toam).where.eq(toam.id, id)
    }.map(TariffOptionActivateMode(toam.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffOptionActivateMode] = {
    withSQL(select.from(TariffOptionActivateMode as toam)).map(TariffOptionActivateMode(toam.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffOptionActivateMode as toam)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffOptionActivateMode] = {
    withSQL {
      select.from(TariffOptionActivateMode as toam).where.append(where)
    }.map(TariffOptionActivateMode(toam.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffOptionActivateMode] = {
    withSQL {
      select.from(TariffOptionActivateMode as toam).where.append(where)
    }.map(TariffOptionActivateMode(toam.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffOptionActivateMode as toam).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    optionId: Int,
    title: String,
    chargeTypeId: Int,
    chargeSumma: BigDecimal,
    periodMode: Int,
    periodCol: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    deactivationMode: Int,
    reactivationMode: Int,
    deleteMode: Int,
    deleteChargeMode: Option[Int] = None)(implicit session: DBSession = autoSession): TariffOptionActivateMode = {
    val generatedKey = withSQL {
      insert.into(TariffOptionActivateMode).namedValues(
        column.optionId -> optionId,
        column.title -> title,
        column.chargeTypeId -> chargeTypeId,
        column.chargeSumma -> chargeSumma,
        column.periodMode -> periodMode,
        column.periodCol -> periodCol,
        column.date1 -> date1,
        column.date2 -> date2,
        column.deactivationMode -> deactivationMode,
        column.reactivationMode -> reactivationMode,
        column.deleteMode -> deleteMode,
        column.deleteChargeMode -> deleteChargeMode
      )
    }.updateAndReturnGeneratedKey.apply()

    TariffOptionActivateMode(
      id = generatedKey.toInt,
      optionId = optionId,
      title = title,
      chargeTypeId = chargeTypeId,
      chargeSumma = chargeSumma,
      periodMode = periodMode,
      periodCol = periodCol,
      date1 = date1,
      date2 = date2,
      deactivationMode = deactivationMode,
      reactivationMode = reactivationMode,
      deleteMode = deleteMode,
      deleteChargeMode = deleteChargeMode)
  }

  def batchInsert(entities: Seq[TariffOptionActivateMode])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'optionId -> entity.optionId,
        'title -> entity.title,
        'chargeTypeId -> entity.chargeTypeId,
        'chargeSumma -> entity.chargeSumma,
        'periodMode -> entity.periodMode,
        'periodCol -> entity.periodCol,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'deactivationMode -> entity.deactivationMode,
        'reactivationMode -> entity.reactivationMode,
        'deleteMode -> entity.deleteMode,
        'deleteChargeMode -> entity.deleteChargeMode))
    SQL("""insert into tariff_option_activate_mode(
      option_id,
      title,
      charge_type_id,
      charge_summa,
      period_mode,
      period_col,
      date1,
      date2,
      deactivation_mode,
      reactivation_mode,
      delete_mode,
      delete_charge_mode
    ) values (
      {optionId},
      {title},
      {chargeTypeId},
      {chargeSumma},
      {periodMode},
      {periodCol},
      {date1},
      {date2},
      {deactivationMode},
      {reactivationMode},
      {deleteMode},
      {deleteChargeMode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffOptionActivateMode)(implicit session: DBSession = autoSession): TariffOptionActivateMode = {
    withSQL {
      update(TariffOptionActivateMode).set(
        column.id -> entity.id,
        column.optionId -> entity.optionId,
        column.title -> entity.title,
        column.chargeTypeId -> entity.chargeTypeId,
        column.chargeSumma -> entity.chargeSumma,
        column.periodMode -> entity.periodMode,
        column.periodCol -> entity.periodCol,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.deactivationMode -> entity.deactivationMode,
        column.reactivationMode -> entity.reactivationMode,
        column.deleteMode -> entity.deleteMode,
        column.deleteChargeMode -> entity.deleteChargeMode
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffOptionActivateMode)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffOptionActivateMode).where.eq(column.id, entity.id) }.update.apply()
  }

}
