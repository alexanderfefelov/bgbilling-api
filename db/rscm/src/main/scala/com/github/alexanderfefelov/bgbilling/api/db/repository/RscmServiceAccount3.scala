package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class RscmServiceAccount3(
  id: Int,
  cid: Int,
  sid: Int,
  date: Option[LocalDate] = None,
  amount: Option[Long] = None,
  comment: String) {

  def save()(implicit session: DBSession = RscmServiceAccount3.autoSession): RscmServiceAccount3 = RscmServiceAccount3.save(this)(session)

  def destroy()(implicit session: DBSession = RscmServiceAccount3.autoSession): Int = RscmServiceAccount3.destroy(this)(session)

}


object RscmServiceAccount3 extends SQLSyntaxSupport[RscmServiceAccount3] with ApiDbConfig {

  override val tableName = s"rscm_service_account_${bgBillingModuleId("rscm")}"

  override val columns = Seq("id", "cid", "sid", "date", "amount", "comment")

  def apply(rsa: SyntaxProvider[RscmServiceAccount3])(rs: WrappedResultSet): RscmServiceAccount3 = autoConstruct(rs, rsa)
  def apply(rsa: ResultName[RscmServiceAccount3])(rs: WrappedResultSet): RscmServiceAccount3 = autoConstruct(rs, rsa)

  val rsa = RscmServiceAccount3.syntax("rsa")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[RscmServiceAccount3] = {
    withSQL {
      select.from(RscmServiceAccount3 as rsa).where.eq(rsa.id, id)
    }.map(RscmServiceAccount3(rsa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[RscmServiceAccount3] = {
    withSQL(select.from(RscmServiceAccount3 as rsa)).map(RscmServiceAccount3(rsa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(RscmServiceAccount3 as rsa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[RscmServiceAccount3] = {
    withSQL {
      select.from(RscmServiceAccount3 as rsa).where.append(where)
    }.map(RscmServiceAccount3(rsa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[RscmServiceAccount3] = {
    withSQL {
      select.from(RscmServiceAccount3 as rsa).where.append(where)
    }.map(RscmServiceAccount3(rsa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(RscmServiceAccount3 as rsa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    sid: Int,
    date: Option[LocalDate] = None,
    amount: Option[Long] = None,
    comment: String)(implicit session: DBSession = autoSession): RscmServiceAccount3 = {
    val generatedKey = withSQL {
      insert.into(RscmServiceAccount3).namedValues(
        column.cid -> cid,
        column.sid -> sid,
        column.date -> date,
        column.amount -> amount,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    RscmServiceAccount3(
      id = generatedKey.toInt,
      cid = cid,
      sid = sid,
      date = date,
      amount = amount,
      comment = comment)
  }

  def batchInsert(entities: Seq[RscmServiceAccount3])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'sid -> entity.sid,
        'date -> entity.date,
        'amount -> entity.amount,
        'comment -> entity.comment))
    SQL("""insert into rscm_service_account_3(
      cid,
      sid,
      date,
      amount,
      comment
    ) values (
      {cid},
      {sid},
      {date},
      {amount},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: RscmServiceAccount3)(implicit session: DBSession = autoSession): RscmServiceAccount3 = {
    withSQL {
      update(RscmServiceAccount3).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.sid -> entity.sid,
        column.date -> entity.date,
        column.amount -> entity.amount,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: RscmServiceAccount3)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(RscmServiceAccount3).where.eq(column.id, entity.id) }.update.apply()
  }

}
