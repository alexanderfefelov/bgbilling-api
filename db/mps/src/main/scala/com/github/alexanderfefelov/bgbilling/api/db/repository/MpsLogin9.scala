package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class MpsLogin9(
  cid: Int,
  id: Int) {

  def save()(implicit session: DBSession = MpsLogin9.autoSession): MpsLogin9 = MpsLogin9.save(this)(session)

  def destroy()(implicit session: DBSession = MpsLogin9.autoSession): Int = MpsLogin9.destroy(this)(session)

}


object MpsLogin9 extends SQLSyntaxSupport[MpsLogin9] with ApiDbConfig {

  override val tableName = s"mps_login_${bgBillingModuleId("mps")}"

  override val columns = Seq("cid", "id")

  def apply(ml: SyntaxProvider[MpsLogin9])(rs: WrappedResultSet): MpsLogin9 = autoConstruct(rs, ml)
  def apply(ml: ResultName[MpsLogin9])(rs: WrappedResultSet): MpsLogin9 = autoConstruct(rs, ml)

  val ml = MpsLogin9.syntax("ml")

  override val autoSession = AutoSession

  def find(cid: Int)(implicit session: DBSession = autoSession): Option[MpsLogin9] = {
    withSQL {
      select.from(MpsLogin9 as ml).where.eq(ml.cid, cid)
    }.map(MpsLogin9(ml.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MpsLogin9] = {
    withSQL(select.from(MpsLogin9 as ml)).map(MpsLogin9(ml.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MpsLogin9 as ml)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MpsLogin9] = {
    withSQL {
      select.from(MpsLogin9 as ml).where.append(where)
    }.map(MpsLogin9(ml.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MpsLogin9] = {
    withSQL {
      select.from(MpsLogin9 as ml).where.append(where)
    }.map(MpsLogin9(ml.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MpsLogin9 as ml).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    id: Int)(implicit session: DBSession = autoSession): MpsLogin9 = {
    withSQL {
      insert.into(MpsLogin9).namedValues(
        column.cid -> cid,
        column.id -> id
      )
    }.update.apply()

    MpsLogin9(
      cid = cid,
      id = id)
  }

  def batchInsert(entities: Seq[MpsLogin9])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'id -> entity.id))
    SQL("""insert into mps_login_9(
      cid,
      id
    ) values (
      {cid},
      {id}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MpsLogin9)(implicit session: DBSession = autoSession): MpsLogin9 = {
    withSQL {
      update(MpsLogin9).set(
        column.cid -> entity.cid,
        column.id -> entity.id
      ).where.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: MpsLogin9)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MpsLogin9).where.eq(column.cid, entity.cid) }.update.apply()
  }

}
