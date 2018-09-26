package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractPattern(
  id: Int,
  title: String,
  closesumma: Float,
  tpid: String,
  groups: Long,
  mode: Int,
  pgid: Int,
  pfid: Int,
  fc: Byte,
  dtl: Int,
  tgid: String,
  scrid: String,
  namePattern: String,
  data: Option[Array[Byte]] = None,
  patid: Int,
  status: Int,
  domainid: Int) {

  def save()(implicit session: DBSession = ContractPattern.autoSession): ContractPattern = ContractPattern.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPattern.autoSession): Int = ContractPattern.destroy(this)(session)

}


object ContractPattern extends SQLSyntaxSupport[ContractPattern] {

  override val tableName = "contract_pattern"

  override val columns = Seq("id", "title", "closesumma", "tpid", "groups", "mode", "pgid", "pfid", "fc", "dtl", "tgid", "scrid", "name_pattern", "data", "patid", "status", "domainId")

  def apply(cp: SyntaxProvider[ContractPattern])(rs: WrappedResultSet): ContractPattern = autoConstruct(rs, cp)
  def apply(cp: ResultName[ContractPattern])(rs: WrappedResultSet): ContractPattern = autoConstruct(rs, cp)

  val cp = ContractPattern.syntax("cp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractPattern] = {
    withSQL {
      select.from(ContractPattern as cp).where.eq(cp.id, id)
    }.map(ContractPattern(cp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPattern] = {
    withSQL(select.from(ContractPattern as cp)).map(ContractPattern(cp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPattern as cp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPattern] = {
    withSQL {
      select.from(ContractPattern as cp).where.append(where)
    }.map(ContractPattern(cp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPattern] = {
    withSQL {
      select.from(ContractPattern as cp).where.append(where)
    }.map(ContractPattern(cp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPattern as cp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    closesumma: Float,
    tpid: String,
    groups: Long,
    mode: Int,
    pgid: Int,
    pfid: Int,
    fc: Byte,
    dtl: Int,
    tgid: String,
    scrid: String,
    namePattern: String,
    data: Option[Array[Byte]] = None,
    patid: Int,
    status: Int,
    domainid: Int)(implicit session: DBSession = autoSession): ContractPattern = {
    val generatedKey = withSQL {
      insert.into(ContractPattern).namedValues(
        column.title -> title,
        column.closesumma -> closesumma,
        column.tpid -> tpid,
        column.groups -> groups,
        column.mode -> mode,
        column.pgid -> pgid,
        column.pfid -> pfid,
        column.fc -> fc,
        column.dtl -> dtl,
        column.tgid -> tgid,
        column.scrid -> scrid,
        column.namePattern -> namePattern,
        column.data -> data,
        column.patid -> patid,
        column.status -> status,
        column.domainid -> domainid
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractPattern(
      id = generatedKey.toInt,
      title = title,
      closesumma = closesumma,
      tpid = tpid,
      groups = groups,
      mode = mode,
      pgid = pgid,
      pfid = pfid,
      fc = fc,
      dtl = dtl,
      tgid = tgid,
      scrid = scrid,
      namePattern = namePattern,
      data = data,
      patid = patid,
      status = status,
      domainid = domainid)
  }

  def batchInsert(entities: collection.Seq[ContractPattern])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'closesumma -> entity.closesumma,
        'tpid -> entity.tpid,
        'groups -> entity.groups,
        'mode -> entity.mode,
        'pgid -> entity.pgid,
        'pfid -> entity.pfid,
        'fc -> entity.fc,
        'dtl -> entity.dtl,
        'tgid -> entity.tgid,
        'scrid -> entity.scrid,
        'namePattern -> entity.namePattern,
        'data -> entity.data,
        'patid -> entity.patid,
        'status -> entity.status,
        'domainid -> entity.domainid))
    SQL("""insert into contract_pattern(
      title,
      closesumma,
      tpid,
      groups,
      mode,
      pgid,
      pfid,
      fc,
      dtl,
      tgid,
      scrid,
      name_pattern,
      data,
      patid,
      status,
      domainId
    ) values (
      {title},
      {closesumma},
      {tpid},
      {groups},
      {mode},
      {pgid},
      {pfid},
      {fc},
      {dtl},
      {tgid},
      {scrid},
      {namePattern},
      {data},
      {patid},
      {status},
      {domainid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPattern)(implicit session: DBSession = autoSession): ContractPattern = {
    withSQL {
      update(ContractPattern).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.closesumma -> entity.closesumma,
        column.tpid -> entity.tpid,
        column.groups -> entity.groups,
        column.mode -> entity.mode,
        column.pgid -> entity.pgid,
        column.pfid -> entity.pfid,
        column.fc -> entity.fc,
        column.dtl -> entity.dtl,
        column.tgid -> entity.tgid,
        column.scrid -> entity.scrid,
        column.namePattern -> entity.namePattern,
        column.data -> entity.data,
        column.patid -> entity.patid,
        column.status -> entity.status,
        column.domainid -> entity.domainid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPattern)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPattern).where.eq(column.id, entity.id) }.update.apply()
  }

}
