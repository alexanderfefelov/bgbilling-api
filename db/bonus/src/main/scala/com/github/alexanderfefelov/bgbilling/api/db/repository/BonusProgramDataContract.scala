package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BonusProgramDataContract(
  programid: Int,
  contractid: Int,
  data: Option[String] = None) {

  def save()(implicit session: DBSession = BonusProgramDataContract.autoSession): BonusProgramDataContract = BonusProgramDataContract.save(this)(session)

  def destroy()(implicit session: DBSession = BonusProgramDataContract.autoSession): Int = BonusProgramDataContract.destroy(this)(session)

}


object BonusProgramDataContract extends SQLSyntaxSupport[BonusProgramDataContract] {

  override val tableName = "bonus_program_data_contract"

  override val columns = Seq("programId", "contractId", "data")

  def apply(bpdc: SyntaxProvider[BonusProgramDataContract])(rs: WrappedResultSet): BonusProgramDataContract = autoConstruct(rs, bpdc)
  def apply(bpdc: ResultName[BonusProgramDataContract])(rs: WrappedResultSet): BonusProgramDataContract = autoConstruct(rs, bpdc)

  val bpdc = BonusProgramDataContract.syntax("bpdc")

  override val autoSession = AutoSession

  def find(contractid: Int, programid: Int)(implicit session: DBSession = autoSession): Option[BonusProgramDataContract] = {
    withSQL {
      select.from(BonusProgramDataContract as bpdc).where.eq(bpdc.contractid, contractid).and.eq(bpdc.programid, programid)
    }.map(BonusProgramDataContract(bpdc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusProgramDataContract] = {
    withSQL(select.from(BonusProgramDataContract as bpdc)).map(BonusProgramDataContract(bpdc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusProgramDataContract as bpdc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusProgramDataContract] = {
    withSQL {
      select.from(BonusProgramDataContract as bpdc).where.append(where)
    }.map(BonusProgramDataContract(bpdc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusProgramDataContract] = {
    withSQL {
      select.from(BonusProgramDataContract as bpdc).where.append(where)
    }.map(BonusProgramDataContract(bpdc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusProgramDataContract as bpdc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    programid: Int,
    contractid: Int,
    data: Option[String] = None)(implicit session: DBSession = autoSession): BonusProgramDataContract = {
    withSQL {
      insert.into(BonusProgramDataContract).namedValues(
        column.programid -> programid,
        column.contractid -> contractid,
        column.data -> data
      )
    }.update.apply()

    BonusProgramDataContract(
      programid = programid,
      contractid = contractid,
      data = data)
  }

  def batchInsert(entities: Seq[BonusProgramDataContract])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'programid -> entity.programid,
        'contractid -> entity.contractid,
        'data -> entity.data))
    SQL("""insert into bonus_program_data_contract(
      programId,
      contractId,
      data
    ) values (
      {programid},
      {contractid},
      {data}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusProgramDataContract)(implicit session: DBSession = autoSession): BonusProgramDataContract = {
    withSQL {
      update(BonusProgramDataContract).set(
        column.programid -> entity.programid,
        column.contractid -> entity.contractid,
        column.data -> entity.data
      ).where.eq(column.contractid, entity.contractid).and.eq(column.programid, entity.programid)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusProgramDataContract)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusProgramDataContract).where.eq(column.contractid, entity.contractid).and.eq(column.programid, entity.programid) }.update.apply()
  }

}
