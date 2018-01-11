package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class PaymentRegisterItem(
  id: Int,
  rid: Int,
  cid: Int,
  summa: Float,
  pid: Int,
  comment: String) {

  def save()(implicit session: DBSession = PaymentRegisterItem.autoSession): PaymentRegisterItem = PaymentRegisterItem.save(this)(session)

  def destroy()(implicit session: DBSession = PaymentRegisterItem.autoSession): Int = PaymentRegisterItem.destroy(this)(session)

}


object PaymentRegisterItem extends SQLSyntaxSupport[PaymentRegisterItem] {

  override val tableName = "payment_register_item"

  override val columns = Seq("id", "rid", "cid", "summa", "pid", "comment")

  def apply(pri: SyntaxProvider[PaymentRegisterItem])(rs: WrappedResultSet): PaymentRegisterItem = autoConstruct(rs, pri)
  def apply(pri: ResultName[PaymentRegisterItem])(rs: WrappedResultSet): PaymentRegisterItem = autoConstruct(rs, pri)

  val pri = PaymentRegisterItem.syntax("pri")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PaymentRegisterItem] = {
    withSQL {
      select.from(PaymentRegisterItem as pri).where.eq(pri.id, id)
    }.map(PaymentRegisterItem(pri.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PaymentRegisterItem] = {
    withSQL(select.from(PaymentRegisterItem as pri)).map(PaymentRegisterItem(pri.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PaymentRegisterItem as pri)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PaymentRegisterItem] = {
    withSQL {
      select.from(PaymentRegisterItem as pri).where.append(where)
    }.map(PaymentRegisterItem(pri.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PaymentRegisterItem] = {
    withSQL {
      select.from(PaymentRegisterItem as pri).where.append(where)
    }.map(PaymentRegisterItem(pri.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PaymentRegisterItem as pri).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    rid: Int,
    cid: Int,
    summa: Float,
    pid: Int,
    comment: String)(implicit session: DBSession = autoSession): PaymentRegisterItem = {
    val generatedKey = withSQL {
      insert.into(PaymentRegisterItem).namedValues(
        column.rid -> rid,
        column.cid -> cid,
        column.summa -> summa,
        column.pid -> pid,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    PaymentRegisterItem(
      id = generatedKey.toInt,
      rid = rid,
      cid = cid,
      summa = summa,
      pid = pid,
      comment = comment)
  }

  def batchInsert(entities: Seq[PaymentRegisterItem])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'rid -> entity.rid,
        'cid -> entity.cid,
        'summa -> entity.summa,
        'pid -> entity.pid,
        'comment -> entity.comment))
    SQL("""insert into payment_register_item(
      rid,
      cid,
      summa,
      pid,
      comment
    ) values (
      {rid},
      {cid},
      {summa},
      {pid},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PaymentRegisterItem)(implicit session: DBSession = autoSession): PaymentRegisterItem = {
    withSQL {
      update(PaymentRegisterItem).set(
        column.id -> entity.id,
        column.rid -> entity.rid,
        column.cid -> entity.cid,
        column.summa -> entity.summa,
        column.pid -> entity.pid,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PaymentRegisterItem)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PaymentRegisterItem).where.eq(column.id, entity.id) }.update.apply()
  }

}
