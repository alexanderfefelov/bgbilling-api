package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractTariffGroup(
  id: Int,
  cid: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  gid: Int,
  comment: String) {

  def save()(implicit session: DBSession = ContractTariffGroup.autoSession): ContractTariffGroup = ContractTariffGroup.save(this)(session)

  def destroy()(implicit session: DBSession = ContractTariffGroup.autoSession): Int = ContractTariffGroup.destroy(this)(session)

}


object ContractTariffGroup extends SQLSyntaxSupport[ContractTariffGroup] {

  override val tableName = "contract_tariff_group"

  override val columns = Seq("id", "cid", "date1", "date2", "gid", "comment")

  def apply(ctg: SyntaxProvider[ContractTariffGroup])(rs: WrappedResultSet): ContractTariffGroup = autoConstruct(rs, ctg)
  def apply(ctg: ResultName[ContractTariffGroup])(rs: WrappedResultSet): ContractTariffGroup = autoConstruct(rs, ctg)

  val ctg = ContractTariffGroup.syntax("ctg")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractTariffGroup] = {
    withSQL {
      select.from(ContractTariffGroup as ctg).where.eq(ctg.id, id)
    }.map(ContractTariffGroup(ctg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractTariffGroup] = {
    withSQL(select.from(ContractTariffGroup as ctg)).map(ContractTariffGroup(ctg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractTariffGroup as ctg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractTariffGroup] = {
    withSQL {
      select.from(ContractTariffGroup as ctg).where.append(where)
    }.map(ContractTariffGroup(ctg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractTariffGroup] = {
    withSQL {
      select.from(ContractTariffGroup as ctg).where.append(where)
    }.map(ContractTariffGroup(ctg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractTariffGroup as ctg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    gid: Int,
    comment: String)(implicit session: DBSession = autoSession): ContractTariffGroup = {
    val generatedKey = withSQL {
      insert.into(ContractTariffGroup).namedValues(
        column.cid -> cid,
        column.date1 -> date1,
        column.date2 -> date2,
        column.gid -> gid,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractTariffGroup(
      id = generatedKey.toInt,
      cid = cid,
      date1 = date1,
      date2 = date2,
      gid = gid,
      comment = comment)
  }

  def batchInsert(entities: Seq[ContractTariffGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'gid -> entity.gid,
        'comment -> entity.comment))
    SQL("""insert into contract_tariff_group(
      cid,
      date1,
      date2,
      gid,
      comment
    ) values (
      {cid},
      {date1},
      {date2},
      {gid},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractTariffGroup)(implicit session: DBSession = autoSession): ContractTariffGroup = {
    withSQL {
      update(ContractTariffGroup).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.gid -> entity.gid,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractTariffGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractTariffGroup).where.eq(column.id, entity.id) }.update.apply()
  }

}
