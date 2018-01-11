package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class BonusProgramLog(
  programid: Int,
  datefrom: LocalDate,
  dateto: LocalDate) {

  def save()(implicit session: DBSession = BonusProgramLog.autoSession): BonusProgramLog = BonusProgramLog.save(this)(session)

  def destroy()(implicit session: DBSession = BonusProgramLog.autoSession): Int = BonusProgramLog.destroy(this)(session)

}


object BonusProgramLog extends SQLSyntaxSupport[BonusProgramLog] {

  override val tableName = "bonus_program_log"

  override val columns = Seq("programId", "dateFrom", "dateTo")

  def apply(bpl: SyntaxProvider[BonusProgramLog])(rs: WrappedResultSet): BonusProgramLog = autoConstruct(rs, bpl)
  def apply(bpl: ResultName[BonusProgramLog])(rs: WrappedResultSet): BonusProgramLog = autoConstruct(rs, bpl)

  val bpl = BonusProgramLog.syntax("bpl")

  override val autoSession = AutoSession

  def find(programid: Int)(implicit session: DBSession = autoSession): Option[BonusProgramLog] = {
    withSQL {
      select.from(BonusProgramLog as bpl).where.eq(bpl.programid, programid)
    }.map(BonusProgramLog(bpl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusProgramLog] = {
    withSQL(select.from(BonusProgramLog as bpl)).map(BonusProgramLog(bpl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusProgramLog as bpl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusProgramLog] = {
    withSQL {
      select.from(BonusProgramLog as bpl).where.append(where)
    }.map(BonusProgramLog(bpl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusProgramLog] = {
    withSQL {
      select.from(BonusProgramLog as bpl).where.append(where)
    }.map(BonusProgramLog(bpl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusProgramLog as bpl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    programid: Int,
    datefrom: LocalDate,
    dateto: LocalDate)(implicit session: DBSession = autoSession): BonusProgramLog = {
    withSQL {
      insert.into(BonusProgramLog).namedValues(
        column.programid -> programid,
        column.datefrom -> datefrom,
        column.dateto -> dateto
      )
    }.update.apply()

    BonusProgramLog(
      programid = programid,
      datefrom = datefrom,
      dateto = dateto)
  }

  def batchInsert(entities: Seq[BonusProgramLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'programid -> entity.programid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto))
    SQL("""insert into bonus_program_log(
      programId,
      dateFrom,
      dateTo
    ) values (
      {programid},
      {datefrom},
      {dateto}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusProgramLog)(implicit session: DBSession = autoSession): BonusProgramLog = {
    withSQL {
      update(BonusProgramLog).set(
        column.programid -> entity.programid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto
      ).where.eq(column.programid, entity.programid)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusProgramLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusProgramLog).where.eq(column.programid, entity.programid) }.update.apply()
  }

}
