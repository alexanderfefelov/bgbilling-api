package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractModule(
  cid: Int,
  mid: Int) {

  def save()(implicit session: DBSession = ContractModule.autoSession): ContractModule = ContractModule.save(this)(session)

  def destroy()(implicit session: DBSession = ContractModule.autoSession): Int = ContractModule.destroy(this)(session)

}


object ContractModule extends SQLSyntaxSupport[ContractModule] {

  override val tableName = "contract_module"

  override val columns = Seq("cid", "mid")

  def apply(cm: SyntaxProvider[ContractModule])(rs: WrappedResultSet): ContractModule = autoConstruct(rs, cm)
  def apply(cm: ResultName[ContractModule])(rs: WrappedResultSet): ContractModule = autoConstruct(rs, cm)

  val cm = ContractModule.syntax("cm")

  override val autoSession = AutoSession

  def find(cid: Int, mid: Int)(implicit session: DBSession = autoSession): Option[ContractModule] = {
    withSQL {
      select.from(ContractModule as cm).where.eq(cm.cid, cid).and.eq(cm.mid, mid)
    }.map(ContractModule(cm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractModule] = {
    withSQL(select.from(ContractModule as cm)).map(ContractModule(cm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractModule as cm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractModule] = {
    withSQL {
      select.from(ContractModule as cm).where.append(where)
    }.map(ContractModule(cm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractModule] = {
    withSQL {
      select.from(ContractModule as cm).where.append(where)
    }.map(ContractModule(cm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractModule as cm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    mid: Int)(implicit session: DBSession = autoSession): ContractModule = {
    withSQL {
      insert.into(ContractModule).namedValues(
        column.cid -> cid,
        column.mid -> mid
      )
    }.update.apply()

    ContractModule(
      cid = cid,
      mid = mid)
  }

  def batchInsert(entities: collection.Seq[ContractModule])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'mid -> entity.mid))
    SQL("""insert into contract_module(
      cid,
      mid
    ) values (
      {cid},
      {mid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractModule)(implicit session: DBSession = autoSession): ContractModule = {
    withSQL {
      update(ContractModule).set(
        column.cid -> entity.cid,
        column.mid -> entity.mid
      ).where.eq(column.cid, entity.cid).and.eq(column.mid, entity.mid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractModule)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractModule).where.eq(column.cid, entity.cid).and.eq(column.mid, entity.mid) }.update.apply()
  }

}
