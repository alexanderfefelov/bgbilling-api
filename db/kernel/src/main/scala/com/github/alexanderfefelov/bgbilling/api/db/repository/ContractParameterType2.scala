package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType2(
  cid: Int,
  pid: Int,
  hid: Option[Int] = None,
  flat: Option[String] = None,
  room: String,
  pod: Option[Int] = None,
  floor: Option[Int] = None,
  address: Option[String] = None,
  comment: Option[String] = None,
  formatKey: Option[String] = None) {

  def save()(implicit session: DBSession = ContractParameterType2.autoSession): ContractParameterType2 = ContractParameterType2.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType2.autoSession): Int = ContractParameterType2.destroy(this)(session)

}


object ContractParameterType2 extends SQLSyntaxSupport[ContractParameterType2] {

  override val tableName = "contract_parameter_type_2"

  override val columns = Seq("cid", "pid", "hid", "flat", "room", "pod", "floor", "address", "comment", "format_key")

  def apply(cpt: SyntaxProvider[ContractParameterType2])(rs: WrappedResultSet): ContractParameterType2 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType2])(rs: WrappedResultSet): ContractParameterType2 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType2.syntax("cpt")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType2] = {
    withSQL {
      select.from(ContractParameterType2 as cpt).where.eq(cpt.cid, cid).and.eq(cpt.pid, pid)
    }.map(ContractParameterType2(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType2] = {
    withSQL(select.from(ContractParameterType2 as cpt)).map(ContractParameterType2(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType2 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType2] = {
    withSQL {
      select.from(ContractParameterType2 as cpt).where.append(where)
    }.map(ContractParameterType2(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType2] = {
    withSQL {
      select.from(ContractParameterType2 as cpt).where.append(where)
    }.map(ContractParameterType2(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType2 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    hid: Option[Int] = None,
    flat: Option[String] = None,
    room: String,
    pod: Option[Int] = None,
    floor: Option[Int] = None,
    address: Option[String] = None,
    comment: Option[String] = None,
    formatKey: Option[String] = None)(implicit session: DBSession = autoSession): ContractParameterType2 = {
    withSQL {
      insert.into(ContractParameterType2).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.hid -> hid,
        column.flat -> flat,
        column.room -> room,
        column.pod -> pod,
        column.floor -> floor,
        column.address -> address,
        column.comment -> comment,
        column.formatKey -> formatKey
      )
    }.update.apply()

    ContractParameterType2(
      cid = cid,
      pid = pid,
      hid = hid,
      flat = flat,
      room = room,
      pod = pod,
      floor = floor,
      address = address,
      comment = comment,
      formatKey = formatKey)
  }

  def batchInsert(entities: Seq[ContractParameterType2])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'hid -> entity.hid,
        'flat -> entity.flat,
        'room -> entity.room,
        'pod -> entity.pod,
        'floor -> entity.floor,
        'address -> entity.address,
        'comment -> entity.comment,
        'formatKey -> entity.formatKey))
    SQL("""insert into contract_parameter_type_2(
      cid,
      pid,
      hid,
      flat,
      room,
      pod,
      floor,
      address,
      comment,
      format_key
    ) values (
      {cid},
      {pid},
      {hid},
      {flat},
      {room},
      {pod},
      {floor},
      {address},
      {comment},
      {formatKey}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType2)(implicit session: DBSession = autoSession): ContractParameterType2 = {
    withSQL {
      update(ContractParameterType2).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.hid -> entity.hid,
        column.flat -> entity.flat,
        column.room -> entity.room,
        column.pod -> entity.pod,
        column.floor -> entity.floor,
        column.address -> entity.address,
        column.comment -> entity.comment,
        column.formatKey -> entity.formatKey
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType2)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType2).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
