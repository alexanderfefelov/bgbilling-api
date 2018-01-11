package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BonusContractInclude(
  cid: Int,
  include: Int) {

  def save()(implicit session: DBSession = BonusContractInclude.autoSession): BonusContractInclude = BonusContractInclude.save(this)(session)

  def destroy()(implicit session: DBSession = BonusContractInclude.autoSession): Int = BonusContractInclude.destroy(this)(session)

}


object BonusContractInclude extends SQLSyntaxSupport[BonusContractInclude] {

  override val tableName = "bonus_contract_include"

  override val columns = Seq("cid", "include")

  def apply(bci: SyntaxProvider[BonusContractInclude])(rs: WrappedResultSet): BonusContractInclude = autoConstruct(rs, bci)
  def apply(bci: ResultName[BonusContractInclude])(rs: WrappedResultSet): BonusContractInclude = autoConstruct(rs, bci)

  val bci = BonusContractInclude.syntax("bci")

  override val autoSession = AutoSession

  def find(cid: Int)(implicit session: DBSession = autoSession): Option[BonusContractInclude] = {
    withSQL {
      select.from(BonusContractInclude as bci).where.eq(bci.cid, cid)
    }.map(BonusContractInclude(bci.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusContractInclude] = {
    withSQL(select.from(BonusContractInclude as bci)).map(BonusContractInclude(bci.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusContractInclude as bci)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusContractInclude] = {
    withSQL {
      select.from(BonusContractInclude as bci).where.append(where)
    }.map(BonusContractInclude(bci.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusContractInclude] = {
    withSQL {
      select.from(BonusContractInclude as bci).where.append(where)
    }.map(BonusContractInclude(bci.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusContractInclude as bci).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    include: Int)(implicit session: DBSession = autoSession): BonusContractInclude = {
    withSQL {
      insert.into(BonusContractInclude).namedValues(
        column.cid -> cid,
        column.include -> include
      )
    }.update.apply()

    BonusContractInclude(
      cid = cid,
      include = include)
  }

  def batchInsert(entities: Seq[BonusContractInclude])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'include -> entity.include))
    SQL("""insert into bonus_contract_include(
      cid,
      include
    ) values (
      {cid},
      {include}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusContractInclude)(implicit session: DBSession = autoSession): BonusContractInclude = {
    withSQL {
      update(BonusContractInclude).set(
        column.cid -> entity.cid,
        column.include -> entity.include
      ).where.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusContractInclude)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusContractInclude).where.eq(column.cid, entity.cid) }.update.apply()
  }

}
