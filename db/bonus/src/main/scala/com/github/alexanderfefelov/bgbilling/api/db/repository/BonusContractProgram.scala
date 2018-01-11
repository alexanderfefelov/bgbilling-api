package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class BonusContractProgram(
  cid: Int,
  programid: Int,
  datefrom: LocalDate,
  dateto: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = BonusContractProgram.autoSession): BonusContractProgram = BonusContractProgram.save(this)(session)

  def destroy()(implicit session: DBSession = BonusContractProgram.autoSession): Int = BonusContractProgram.destroy(this)(session)

}


object BonusContractProgram extends SQLSyntaxSupport[BonusContractProgram] {

  override val tableName = "bonus_contract_program"

  override val columns = Seq("cid", "programId", "dateFrom", "dateTo")

  def apply(bcp: SyntaxProvider[BonusContractProgram])(rs: WrappedResultSet): BonusContractProgram = autoConstruct(rs, bcp)
  def apply(bcp: ResultName[BonusContractProgram])(rs: WrappedResultSet): BonusContractProgram = autoConstruct(rs, bcp)

  val bcp = BonusContractProgram.syntax("bcp")

  override val autoSession = AutoSession

  def find(cid: Int, programid: Int)(implicit session: DBSession = autoSession): Option[BonusContractProgram] = {
    withSQL {
      select.from(BonusContractProgram as bcp).where.eq(bcp.cid, cid).and.eq(bcp.programid, programid)
    }.map(BonusContractProgram(bcp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusContractProgram] = {
    withSQL(select.from(BonusContractProgram as bcp)).map(BonusContractProgram(bcp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusContractProgram as bcp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusContractProgram] = {
    withSQL {
      select.from(BonusContractProgram as bcp).where.append(where)
    }.map(BonusContractProgram(bcp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusContractProgram] = {
    withSQL {
      select.from(BonusContractProgram as bcp).where.append(where)
    }.map(BonusContractProgram(bcp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusContractProgram as bcp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    programid: Int,
    datefrom: LocalDate,
    dateto: Option[LocalDate] = None)(implicit session: DBSession = autoSession): BonusContractProgram = {
    withSQL {
      insert.into(BonusContractProgram).namedValues(
        column.cid -> cid,
        column.programid -> programid,
        column.datefrom -> datefrom,
        column.dateto -> dateto
      )
    }.update.apply()

    BonusContractProgram(
      cid = cid,
      programid = programid,
      datefrom = datefrom,
      dateto = dateto)
  }

  def batchInsert(entities: Seq[BonusContractProgram])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'programid -> entity.programid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto))
    SQL("""insert into bonus_contract_program(
      cid,
      programId,
      dateFrom,
      dateTo
    ) values (
      {cid},
      {programid},
      {datefrom},
      {dateto}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusContractProgram)(implicit session: DBSession = autoSession): BonusContractProgram = {
    withSQL {
      update(BonusContractProgram).set(
        column.cid -> entity.cid,
        column.programid -> entity.programid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto
      ).where.eq(column.cid, entity.cid).and.eq(column.programid, entity.programid)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusContractProgram)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusContractProgram).where.eq(column.cid, entity.cid).and.eq(column.programid, entity.programid) }.update.apply()
  }

}
