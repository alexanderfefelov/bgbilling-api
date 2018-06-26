package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractTariff(
  id: Int,
  cid: Int,
  tpid: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  comment: String,
  lm: DateTime,
  pos: Byte,
  emid: Int,
  eid: Int,
  replacedFrom: Option[Int] = None) {

  def save()(implicit session: DBSession = ContractTariff.autoSession): ContractTariff = ContractTariff.save(this)(session)

  def destroy()(implicit session: DBSession = ContractTariff.autoSession): Int = ContractTariff.destroy(this)(session)

}


object ContractTariff extends SQLSyntaxSupport[ContractTariff] {

  override val tableName = "contract_tariff"

  override val columns = Seq("id", "cid", "tpid", "date1", "date2", "comment", "lm", "pos", "emid", "eid", "replaced_from")

  def apply(ct: SyntaxProvider[ContractTariff])(rs: WrappedResultSet): ContractTariff = autoConstruct(rs, ct)
  def apply(ct: ResultName[ContractTariff])(rs: WrappedResultSet): ContractTariff = autoConstruct(rs, ct)

  val ct = ContractTariff.syntax("ct")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractTariff] = {
    withSQL {
      select.from(ContractTariff as ct).where.eq(ct.id, id)
    }.map(ContractTariff(ct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractTariff] = {
    withSQL(select.from(ContractTariff as ct)).map(ContractTariff(ct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractTariff as ct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractTariff] = {
    withSQL {
      select.from(ContractTariff as ct).where.append(where)
    }.map(ContractTariff(ct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractTariff] = {
    withSQL {
      select.from(ContractTariff as ct).where.append(where)
    }.map(ContractTariff(ct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractTariff as ct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    tpid: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    comment: String,
    lm: DateTime,
    pos: Byte,
    emid: Int,
    eid: Int,
    replacedFrom: Option[Int] = None)(implicit session: DBSession = autoSession): ContractTariff = {
    val generatedKey = withSQL {
      insert.into(ContractTariff).namedValues(
        column.cid -> cid,
        column.tpid -> tpid,
        column.date1 -> date1,
        column.date2 -> date2,
        column.comment -> comment,
        column.lm -> lm,
        column.pos -> pos,
        column.emid -> emid,
        column.eid -> eid,
        column.replacedFrom -> replacedFrom
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractTariff(
      id = generatedKey.toInt,
      cid = cid,
      tpid = tpid,
      date1 = date1,
      date2 = date2,
      comment = comment,
      lm = lm,
      pos = pos,
      emid = emid,
      eid = eid,
      replacedFrom = replacedFrom)
  }

  def batchInsert(entities: Seq[ContractTariff])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'tpid -> entity.tpid,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'comment -> entity.comment,
        'lm -> entity.lm,
        'pos -> entity.pos,
        'emid -> entity.emid,
        'eid -> entity.eid,
        'replacedFrom -> entity.replacedFrom))
    SQL("""insert into contract_tariff(
      cid,
      tpid,
      date1,
      date2,
      comment,
      lm,
      pos,
      emid,
      eid,
      replaced_from
    ) values (
      {cid},
      {tpid},
      {date1},
      {date2},
      {comment},
      {lm},
      {pos},
      {emid},
      {eid},
      {replacedFrom}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractTariff)(implicit session: DBSession = autoSession): ContractTariff = {
    withSQL {
      update(ContractTariff).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.tpid -> entity.tpid,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.comment -> entity.comment,
        column.lm -> entity.lm,
        column.pos -> entity.pos,
        column.emid -> entity.emid,
        column.eid -> entity.eid,
        column.replacedFrom -> entity.replacedFrom
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractTariff)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractTariff).where.eq(column.id, entity.id) }.update.apply()
  }

}
