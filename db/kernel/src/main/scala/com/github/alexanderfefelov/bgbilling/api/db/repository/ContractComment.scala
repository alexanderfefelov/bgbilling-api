package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractComment(
  id: Int,
  cid: Int,
  uid: Int,
  subject: String,
  comment: String,
  dt: DateTime,
  visibled: Boolean) {

  def save()(implicit session: DBSession = ContractComment.autoSession): ContractComment = ContractComment.save(this)(session)

  def destroy()(implicit session: DBSession = ContractComment.autoSession): Int = ContractComment.destroy(this)(session)

}


object ContractComment extends SQLSyntaxSupport[ContractComment] {

  override val tableName = "contract_comment"

  override val columns = Seq("id", "cid", "uid", "subject", "comment", "dt", "visibled")

  def apply(cc: SyntaxProvider[ContractComment])(rs: WrappedResultSet): ContractComment = autoConstruct(rs, cc)
  def apply(cc: ResultName[ContractComment])(rs: WrappedResultSet): ContractComment = autoConstruct(rs, cc)

  val cc = ContractComment.syntax("cc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractComment] = {
    withSQL {
      select.from(ContractComment as cc).where.eq(cc.id, id)
    }.map(ContractComment(cc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractComment] = {
    withSQL(select.from(ContractComment as cc)).map(ContractComment(cc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractComment as cc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractComment] = {
    withSQL {
      select.from(ContractComment as cc).where.append(where)
    }.map(ContractComment(cc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractComment] = {
    withSQL {
      select.from(ContractComment as cc).where.append(where)
    }.map(ContractComment(cc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractComment as cc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    uid: Int,
    subject: String,
    comment: String,
    dt: DateTime,
    visibled: Boolean)(implicit session: DBSession = autoSession): ContractComment = {
    val generatedKey = withSQL {
      insert.into(ContractComment).namedValues(
        column.cid -> cid,
        column.uid -> uid,
        column.subject -> subject,
        column.comment -> comment,
        column.dt -> dt,
        column.visibled -> visibled
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractComment(
      id = generatedKey.toInt,
      cid = cid,
      uid = uid,
      subject = subject,
      comment = comment,
      dt = dt,
      visibled = visibled)
  }

  def batchInsert(entities: Seq[ContractComment])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'uid -> entity.uid,
        'subject -> entity.subject,
        'comment -> entity.comment,
        'dt -> entity.dt,
        'visibled -> entity.visibled))
    SQL("""insert into contract_comment(
      cid,
      uid,
      subject,
      comment,
      dt,
      visibled
    ) values (
      {cid},
      {uid},
      {subject},
      {comment},
      {dt},
      {visibled}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractComment)(implicit session: DBSession = autoSession): ContractComment = {
    withSQL {
      update(ContractComment).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.uid -> entity.uid,
        column.subject -> entity.subject,
        column.comment -> entity.comment,
        column.dt -> entity.dt,
        column.visibled -> entity.visibled
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractComment)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractComment).where.eq(column.id, entity.id) }.update.apply()
  }

}
