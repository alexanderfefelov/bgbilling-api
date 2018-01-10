package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class ContractScript(
  id: Int,
  cid: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  scriptId: Int,
  comment: String) {

  def save()(implicit session: DBSession = ContractScript.autoSession): ContractScript = ContractScript.save(this)(session)

  def destroy()(implicit session: DBSession = ContractScript.autoSession): Int = ContractScript.destroy(this)(session)

}


object ContractScript extends SQLSyntaxSupport[ContractScript] {

  override val tableName = "contract_script"

  override val columns = Seq("id", "cid", "date1", "date2", "script_id", "comment")

  def apply(cs: SyntaxProvider[ContractScript])(rs: WrappedResultSet): ContractScript = autoConstruct(rs, cs)
  def apply(cs: ResultName[ContractScript])(rs: WrappedResultSet): ContractScript = autoConstruct(rs, cs)

  val cs = ContractScript.syntax("cs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractScript] = {
    withSQL {
      select.from(ContractScript as cs).where.eq(cs.id, id)
    }.map(ContractScript(cs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractScript] = {
    withSQL(select.from(ContractScript as cs)).map(ContractScript(cs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractScript as cs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractScript] = {
    withSQL {
      select.from(ContractScript as cs).where.append(where)
    }.map(ContractScript(cs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractScript] = {
    withSQL {
      select.from(ContractScript as cs).where.append(where)
    }.map(ContractScript(cs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractScript as cs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    scriptId: Int,
    comment: String)(implicit session: DBSession = autoSession): ContractScript = {
    val generatedKey = withSQL {
      insert.into(ContractScript).namedValues(
        column.cid -> cid,
        column.date1 -> date1,
        column.date2 -> date2,
        column.scriptId -> scriptId,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractScript(
      id = generatedKey.toInt,
      cid = cid,
      date1 = date1,
      date2 = date2,
      scriptId = scriptId,
      comment = comment)
  }

  def batchInsert(entities: Seq[ContractScript])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'scriptId -> entity.scriptId,
        'comment -> entity.comment))
    SQL("""insert into contract_script(
      cid,
      date1,
      date2,
      script_id,
      comment
    ) values (
      {cid},
      {date1},
      {date2},
      {scriptId},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractScript)(implicit session: DBSession = autoSession): ContractScript = {
    withSQL {
      update(ContractScript).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.scriptId -> entity.scriptId,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractScript)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractScript).where.eq(column.id, entity.id) }.update.apply()
  }

}
