package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class BillAccount5(
  id: Int,
  title: String,
  bankTitle: String,
  account: String,
  bik: String,
  corrAccount: String,
  pid: Int,
  gr: Long,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = BillAccount5.autoSession): BillAccount5 = BillAccount5.save(this)(session)

  def destroy()(implicit session: DBSession = BillAccount5.autoSession): Int = BillAccount5.destroy(this)(session)

}


object BillAccount5 extends SQLSyntaxSupport[BillAccount5] with ApiDbConfig {

  override val tableName = s"bill_account_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "title", "bank_title", "account", "bik", "corr_account", "pid", "gr", "comment")

  def apply(ba: SyntaxProvider[BillAccount5])(rs: WrappedResultSet): BillAccount5 = autoConstruct(rs, ba)
  def apply(ba: ResultName[BillAccount5])(rs: WrappedResultSet): BillAccount5 = autoConstruct(rs, ba)

  val ba = BillAccount5.syntax("ba")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillAccount5] = {
    withSQL {
      select.from(BillAccount5 as ba).where.eq(ba.id, id)
    }.map(BillAccount5(ba.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillAccount5] = {
    withSQL(select.from(BillAccount5 as ba)).map(BillAccount5(ba.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillAccount5 as ba)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillAccount5] = {
    withSQL {
      select.from(BillAccount5 as ba).where.append(where)
    }.map(BillAccount5(ba.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillAccount5] = {
    withSQL {
      select.from(BillAccount5 as ba).where.append(where)
    }.map(BillAccount5(ba.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillAccount5 as ba).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    bankTitle: String,
    account: String,
    bik: String,
    corrAccount: String,
    pid: Int,
    gr: Long,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): BillAccount5 = {
    val generatedKey = withSQL {
      insert.into(BillAccount5).namedValues(
        column.title -> title,
        column.bankTitle -> bankTitle,
        column.account -> account,
        column.bik -> bik,
        column.corrAccount -> corrAccount,
        column.pid -> pid,
        column.gr -> gr,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    BillAccount5(
      id = generatedKey.toInt,
      title = title,
      bankTitle = bankTitle,
      account = account,
      bik = bik,
      corrAccount = corrAccount,
      pid = pid,
      gr = gr,
      comment = comment)
  }

  def batchInsert(entities: Seq[BillAccount5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'bankTitle -> entity.bankTitle,
        'account -> entity.account,
        'bik -> entity.bik,
        'corrAccount -> entity.corrAccount,
        'pid -> entity.pid,
        'gr -> entity.gr,
        'comment -> entity.comment))
    SQL("""insert into bill_account_5(
      title,
      bank_title,
      account,
      bik,
      corr_account,
      pid,
      gr,
      comment
    ) values (
      {title},
      {bankTitle},
      {account},
      {bik},
      {corrAccount},
      {pid},
      {gr},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillAccount5)(implicit session: DBSession = autoSession): BillAccount5 = {
    withSQL {
      update(BillAccount5).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.bankTitle -> entity.bankTitle,
        column.account -> entity.account,
        column.bik -> entity.bik,
        column.corrAccount -> entity.corrAccount,
        column.pid -> entity.pid,
        column.gr -> entity.gr,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillAccount5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillAccount5).where.eq(column.id, entity.id) }.update.apply()
  }

}
