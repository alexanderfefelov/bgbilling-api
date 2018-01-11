package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class RscmService3(
  id: Int,
  sid: Option[String] = None,
  unit: Option[String] = None) {

  def save()(implicit session: DBSession = RscmService3.autoSession): RscmService3 = RscmService3.save(this)(session)

  def destroy()(implicit session: DBSession = RscmService3.autoSession): Int = RscmService3.destroy(this)(session)

}


object RscmService3 extends SQLSyntaxSupport[RscmService3] with ApiDbConfig {

  override val tableName = s"rscm_service_$bgBillingModuleId("rscm")"

  override val columns = Seq("id", "sid", "unit")

  def apply(r: SyntaxProvider[RscmService3])(rs: WrappedResultSet): RscmService3 = autoConstruct(rs, r)
  def apply(r: ResultName[RscmService3])(rs: WrappedResultSet): RscmService3 = autoConstruct(rs, r)

  val r = RscmService3.syntax("r")

  override val autoSession = AutoSession

  def find(id: Int, sid: Option[String], unit: Option[String])(implicit session: DBSession = autoSession): Option[RscmService3] = {
    withSQL {
      select.from(RscmService3 as r).where.eq(r.id, id).and.eq(r.sid, sid).and.eq(r.unit, unit)
    }.map(RscmService3(r.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[RscmService3] = {
    withSQL(select.from(RscmService3 as r)).map(RscmService3(r.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(RscmService3 as r)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[RscmService3] = {
    withSQL {
      select.from(RscmService3 as r).where.append(where)
    }.map(RscmService3(r.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[RscmService3] = {
    withSQL {
      select.from(RscmService3 as r).where.append(where)
    }.map(RscmService3(r.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(RscmService3 as r).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    sid: Option[String] = None,
    unit: Option[String] = None)(implicit session: DBSession = autoSession): RscmService3 = {
    val generatedKey = withSQL {
      insert.into(RscmService3).namedValues(
        column.sid -> sid,
        column.unit -> unit
      )
    }.updateAndReturnGeneratedKey.apply()

    RscmService3(
      id = generatedKey.toInt,
      sid = sid,
      unit = unit)
  }

  def batchInsert(entities: Seq[RscmService3])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'sid -> entity.sid,
        'unit -> entity.unit))
    SQL("""insert into rscm_service_3(
      sid,
      unit
    ) values (
      {sid},
      {unit}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: RscmService3)(implicit session: DBSession = autoSession): RscmService3 = {
    withSQL {
      update(RscmService3).set(
        column.id -> entity.id,
        column.sid -> entity.sid,
        column.unit -> entity.unit
      ).where.eq(column.id, entity.id).and.eq(column.sid, entity.sid).and.eq(column.unit, entity.unit)
    }.update.apply()
    entity
  }

  def destroy(entity: RscmService3)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(RscmService3).where.eq(column.id, entity.id).and.eq(column.sid, entity.sid).and.eq(column.unit, entity.unit) }.update.apply()
  }

}
