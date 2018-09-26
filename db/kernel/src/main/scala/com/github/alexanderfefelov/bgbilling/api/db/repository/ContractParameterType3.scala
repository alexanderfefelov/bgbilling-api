package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType3(
  id: Int,
  cid: Int,
  pid: Int,
  email: Option[String] = None,
  name: Option[String] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = ContractParameterType3.autoSession): ContractParameterType3 = ContractParameterType3.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType3.autoSession): Int = ContractParameterType3.destroy(this)(session)

}


object ContractParameterType3 extends SQLSyntaxSupport[ContractParameterType3] {

  override val tableName = "contract_parameter_type_3"

  override val columns = Seq("id", "cid", "pid", "email", "name", "comment")

  def apply(cpt: SyntaxProvider[ContractParameterType3])(rs: WrappedResultSet): ContractParameterType3 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType3])(rs: WrappedResultSet): ContractParameterType3 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType3.syntax("cpt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType3] = {
    withSQL {
      select.from(ContractParameterType3 as cpt).where.eq(cpt.id, id)
    }.map(ContractParameterType3(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType3] = {
    withSQL(select.from(ContractParameterType3 as cpt)).map(ContractParameterType3(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType3 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType3] = {
    withSQL {
      select.from(ContractParameterType3 as cpt).where.append(where)
    }.map(ContractParameterType3(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType3] = {
    withSQL {
      select.from(ContractParameterType3 as cpt).where.append(where)
    }.map(ContractParameterType3(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType3 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    email: Option[String] = None,
    name: Option[String] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): ContractParameterType3 = {
    val generatedKey = withSQL {
      insert.into(ContractParameterType3).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.email -> email,
        column.name -> name,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractParameterType3(
      id = generatedKey.toInt,
      cid = cid,
      pid = pid,
      email = email,
      name = name,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[ContractParameterType3])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'email -> entity.email,
        'name -> entity.name,
        'comment -> entity.comment))
    SQL("""insert into contract_parameter_type_3(
      cid,
      pid,
      email,
      name,
      comment
    ) values (
      {cid},
      {pid},
      {email},
      {name},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType3)(implicit session: DBSession = autoSession): ContractParameterType3 = {
    withSQL {
      update(ContractParameterType3).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.email -> entity.email,
        column.name -> entity.name,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType3)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType3).where.eq(column.id, entity.id) }.update.apply()
  }

}
