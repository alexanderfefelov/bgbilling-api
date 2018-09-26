package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectParamValueAddress(
  objectId: Int,
  paramId: Int,
  hid: Int,
  flat: String,
  room: String,
  pod: Int,
  floor: Int,
  address: String,
  comment: String,
  formatKey: String) {

  def save()(implicit session: DBSession = ObjectParamValueAddress.autoSession): ObjectParamValueAddress = ObjectParamValueAddress.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueAddress.autoSession): Int = ObjectParamValueAddress.destroy(this)(session)

}


object ObjectParamValueAddress extends SQLSyntaxSupport[ObjectParamValueAddress] {

  override val tableName = "object_param_value_address"

  override val columns = Seq("object_id", "param_id", "hid", "flat", "room", "pod", "floor", "address", "comment", "format_key")

  def apply(opva: SyntaxProvider[ObjectParamValueAddress])(rs: WrappedResultSet): ObjectParamValueAddress = autoConstruct(rs, opva)
  def apply(opva: ResultName[ObjectParamValueAddress])(rs: WrappedResultSet): ObjectParamValueAddress = autoConstruct(rs, opva)

  val opva = ObjectParamValueAddress.syntax("opva")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueAddress] = {
    withSQL {
      select.from(ObjectParamValueAddress as opva).where.eq(opva.objectId, objectId).and.eq(opva.paramId, paramId)
    }.map(ObjectParamValueAddress(opva.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueAddress] = {
    withSQL(select.from(ObjectParamValueAddress as opva)).map(ObjectParamValueAddress(opva.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueAddress as opva)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueAddress] = {
    withSQL {
      select.from(ObjectParamValueAddress as opva).where.append(where)
    }.map(ObjectParamValueAddress(opva.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueAddress] = {
    withSQL {
      select.from(ObjectParamValueAddress as opva).where.append(where)
    }.map(ObjectParamValueAddress(opva.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueAddress as opva).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    hid: Int,
    flat: String,
    room: String,
    pod: Int,
    floor: Int,
    address: String,
    comment: String,
    formatKey: String)(implicit session: DBSession = autoSession): ObjectParamValueAddress = {
    withSQL {
      insert.into(ObjectParamValueAddress).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
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

    ObjectParamValueAddress(
      objectId = objectId,
      paramId = paramId,
      hid = hid,
      flat = flat,
      room = room,
      pod = pod,
      floor = floor,
      address = address,
      comment = comment,
      formatKey = formatKey)
  }

  def batchInsert(entities: collection.Seq[ObjectParamValueAddress])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'hid -> entity.hid,
        'flat -> entity.flat,
        'room -> entity.room,
        'pod -> entity.pod,
        'floor -> entity.floor,
        'address -> entity.address,
        'comment -> entity.comment,
        'formatKey -> entity.formatKey))
    SQL("""insert into object_param_value_address(
      object_id,
      param_id,
      hid,
      flat,
      room,
      pod,
      floor,
      address,
      comment,
      format_key
    ) values (
      {objectId},
      {paramId},
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

  def save(entity: ObjectParamValueAddress)(implicit session: DBSession = autoSession): ObjectParamValueAddress = {
    withSQL {
      update(ObjectParamValueAddress).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.hid -> entity.hid,
        column.flat -> entity.flat,
        column.room -> entity.room,
        column.pod -> entity.pod,
        column.floor -> entity.floor,
        column.address -> entity.address,
        column.comment -> entity.comment,
        column.formatKey -> entity.formatKey
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueAddress)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueAddress).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId) }.update.apply()
  }

}
