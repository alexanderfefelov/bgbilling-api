package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class TariffOption(
  id: Int,
  title: String,
  tariffIds: String,
  comment: String,
  description: String,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  depends: String,
  incompatible: String,
  deactivationMode: Int,
  contractGroups: Long,
  hideforweb: Short,
  hideforwebcontractgroups: Long,
  hideforwebcontractgroupsmode: Short) {

  def save()(implicit session: DBSession = TariffOption.autoSession): TariffOption = TariffOption.save(this)(session)

  def destroy()(implicit session: DBSession = TariffOption.autoSession): Int = TariffOption.destroy(this)(session)

}


object TariffOption extends SQLSyntaxSupport[TariffOption] {

  override val tableName = "tariff_option"

  override val columns = Seq("id", "title", "tariff_ids", "comment", "description", "date1", "date2", "depends", "incompatible", "deactivation_mode", "contract_groups", "hideForWeb", "hideForWebContractGroups", "hideForWebContractGroupsMode")

  def apply(to: SyntaxProvider[TariffOption])(rs: WrappedResultSet): TariffOption = autoConstruct(rs, to)
  def apply(to: ResultName[TariffOption])(rs: WrappedResultSet): TariffOption = autoConstruct(rs, to)

  val to = TariffOption.syntax("to")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TariffOption] = {
    withSQL {
      select.from(TariffOption as to).where.eq(to.id, id)
    }.map(TariffOption(to.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffOption] = {
    withSQL(select.from(TariffOption as to)).map(TariffOption(to.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffOption as to)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffOption] = {
    withSQL {
      select.from(TariffOption as to).where.append(where)
    }.map(TariffOption(to.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffOption] = {
    withSQL {
      select.from(TariffOption as to).where.append(where)
    }.map(TariffOption(to.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffOption as to).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    tariffIds: String,
    comment: String,
    description: String,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    depends: String,
    incompatible: String,
    deactivationMode: Int,
    contractGroups: Long,
    hideforweb: Short,
    hideforwebcontractgroups: Long,
    hideforwebcontractgroupsmode: Short)(implicit session: DBSession = autoSession): TariffOption = {
    val generatedKey = withSQL {
      insert.into(TariffOption).namedValues(
        column.title -> title,
        column.tariffIds -> tariffIds,
        column.comment -> comment,
        column.description -> description,
        column.date1 -> date1,
        column.date2 -> date2,
        column.depends -> depends,
        column.incompatible -> incompatible,
        column.deactivationMode -> deactivationMode,
        column.contractGroups -> contractGroups,
        column.hideforweb -> hideforweb,
        column.hideforwebcontractgroups -> hideforwebcontractgroups,
        column.hideforwebcontractgroupsmode -> hideforwebcontractgroupsmode
      )
    }.updateAndReturnGeneratedKey.apply()

    TariffOption(
      id = generatedKey.toInt,
      title = title,
      tariffIds = tariffIds,
      comment = comment,
      description = description,
      date1 = date1,
      date2 = date2,
      depends = depends,
      incompatible = incompatible,
      deactivationMode = deactivationMode,
      contractGroups = contractGroups,
      hideforweb = hideforweb,
      hideforwebcontractgroups = hideforwebcontractgroups,
      hideforwebcontractgroupsmode = hideforwebcontractgroupsmode)
  }

  def batchInsert(entities: collection.Seq[TariffOption])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'tariffIds -> entity.tariffIds,
        'comment -> entity.comment,
        'description -> entity.description,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'depends -> entity.depends,
        'incompatible -> entity.incompatible,
        'deactivationMode -> entity.deactivationMode,
        'contractGroups -> entity.contractGroups,
        'hideforweb -> entity.hideforweb,
        'hideforwebcontractgroups -> entity.hideforwebcontractgroups,
        'hideforwebcontractgroupsmode -> entity.hideforwebcontractgroupsmode))
    SQL("""insert into tariff_option(
      title,
      tariff_ids,
      comment,
      description,
      date1,
      date2,
      depends,
      incompatible,
      deactivation_mode,
      contract_groups,
      hideForWeb,
      hideForWebContractGroups,
      hideForWebContractGroupsMode
    ) values (
      {title},
      {tariffIds},
      {comment},
      {description},
      {date1},
      {date2},
      {depends},
      {incompatible},
      {deactivationMode},
      {contractGroups},
      {hideforweb},
      {hideforwebcontractgroups},
      {hideforwebcontractgroupsmode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffOption)(implicit session: DBSession = autoSession): TariffOption = {
    withSQL {
      update(TariffOption).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.tariffIds -> entity.tariffIds,
        column.comment -> entity.comment,
        column.description -> entity.description,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.depends -> entity.depends,
        column.incompatible -> entity.incompatible,
        column.deactivationMode -> entity.deactivationMode,
        column.contractGroups -> entity.contractGroups,
        column.hideforweb -> entity.hideforweb,
        column.hideforwebcontractgroups -> entity.hideforwebcontractgroups,
        column.hideforwebcontractgroupsmode -> entity.hideforwebcontractgroupsmode
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffOption)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffOption).where.eq(column.id, entity.id) }.update.apply()
  }

}
