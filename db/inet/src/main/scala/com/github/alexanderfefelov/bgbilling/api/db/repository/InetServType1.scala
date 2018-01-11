package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetServType1(
  id: Int,
  title: String,
  parenttypeids: String,
  sessioninitiationtype: Byte,
  sessioncountlimit: Int,
  sessioncountlimitlock: Byte,
  addresstype: Byte,
  addressallinterface: Byte,
  traffictypelinkid: Int,
  needlogin: Byte,
  needdevice: Byte,
  needinterface: Byte,
  personalinterface: Byte,
  needvlan: Byte,
  needidentifier: Byte,
  needmacaddress: Byte,
  needcontractobject: Byte,
  needrestriction: Byte,
  config: Option[String] = None,
  personalvlan: Byte,
  ipfromparentrange: Option[Byte] = None) {

  def save()(implicit session: DBSession = InetServType1.autoSession): InetServType1 = InetServType1.save(this)(session)

  def destroy()(implicit session: DBSession = InetServType1.autoSession): Int = InetServType1.destroy(this)(session)

}


object InetServType1 extends SQLSyntaxSupport[InetServType1] {

  override val tableName = "inet_serv_type_1"

  override val columns = Seq("id", "title", "parentTypeIds", "sessionInitiationType", "sessionCountLimit", "sessionCountLimitLock", "addressType", "addressAllInterface", "trafficTypeLinkId", "needLogin", "needDevice", "needInterface", "personalInterface", "needVlan", "needIdentifier", "needMacAddress", "needContractObject", "needRestriction", "config", "personalVlan", "ipFromParentRange")

  def apply(ist: SyntaxProvider[InetServType1])(rs: WrappedResultSet): InetServType1 = autoConstruct(rs, ist)
  def apply(ist: ResultName[InetServType1])(rs: WrappedResultSet): InetServType1 = autoConstruct(rs, ist)

  val ist = InetServType1.syntax("ist")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetServType1] = {
    withSQL {
      select.from(InetServType1 as ist).where.eq(ist.id, id)
    }.map(InetServType1(ist.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetServType1] = {
    withSQL(select.from(InetServType1 as ist)).map(InetServType1(ist.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetServType1 as ist)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetServType1] = {
    withSQL {
      select.from(InetServType1 as ist).where.append(where)
    }.map(InetServType1(ist.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetServType1] = {
    withSQL {
      select.from(InetServType1 as ist).where.append(where)
    }.map(InetServType1(ist.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetServType1 as ist).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    parenttypeids: String,
    sessioninitiationtype: Byte,
    sessioncountlimit: Int,
    sessioncountlimitlock: Byte,
    addresstype: Byte,
    addressallinterface: Byte,
    traffictypelinkid: Int,
    needlogin: Byte,
    needdevice: Byte,
    needinterface: Byte,
    personalinterface: Byte,
    needvlan: Byte,
    needidentifier: Byte,
    needmacaddress: Byte,
    needcontractobject: Byte,
    needrestriction: Byte,
    config: Option[String] = None,
    personalvlan: Byte,
    ipfromparentrange: Option[Byte] = None)(implicit session: DBSession = autoSession): InetServType1 = {
    val generatedKey = withSQL {
      insert.into(InetServType1).namedValues(
        column.title -> title,
        column.parenttypeids -> parenttypeids,
        column.sessioninitiationtype -> sessioninitiationtype,
        column.sessioncountlimit -> sessioncountlimit,
        column.sessioncountlimitlock -> sessioncountlimitlock,
        column.addresstype -> addresstype,
        column.addressallinterface -> addressallinterface,
        column.traffictypelinkid -> traffictypelinkid,
        column.needlogin -> needlogin,
        column.needdevice -> needdevice,
        column.needinterface -> needinterface,
        column.personalinterface -> personalinterface,
        column.needvlan -> needvlan,
        column.needidentifier -> needidentifier,
        column.needmacaddress -> needmacaddress,
        column.needcontractobject -> needcontractobject,
        column.needrestriction -> needrestriction,
        column.config -> config,
        column.personalvlan -> personalvlan,
        column.ipfromparentrange -> ipfromparentrange
      )
    }.updateAndReturnGeneratedKey.apply()

    InetServType1(
      id = generatedKey.toInt,
      title = title,
      parenttypeids = parenttypeids,
      sessioninitiationtype = sessioninitiationtype,
      sessioncountlimit = sessioncountlimit,
      sessioncountlimitlock = sessioncountlimitlock,
      addresstype = addresstype,
      addressallinterface = addressallinterface,
      traffictypelinkid = traffictypelinkid,
      needlogin = needlogin,
      needdevice = needdevice,
      needinterface = needinterface,
      personalinterface = personalinterface,
      needvlan = needvlan,
      needidentifier = needidentifier,
      needmacaddress = needmacaddress,
      needcontractobject = needcontractobject,
      needrestriction = needrestriction,
      config = config,
      personalvlan = personalvlan,
      ipfromparentrange = ipfromparentrange)
  }

  def batchInsert(entities: Seq[InetServType1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'parenttypeids -> entity.parenttypeids,
        'sessioninitiationtype -> entity.sessioninitiationtype,
        'sessioncountlimit -> entity.sessioncountlimit,
        'sessioncountlimitlock -> entity.sessioncountlimitlock,
        'addresstype -> entity.addresstype,
        'addressallinterface -> entity.addressallinterface,
        'traffictypelinkid -> entity.traffictypelinkid,
        'needlogin -> entity.needlogin,
        'needdevice -> entity.needdevice,
        'needinterface -> entity.needinterface,
        'personalinterface -> entity.personalinterface,
        'needvlan -> entity.needvlan,
        'needidentifier -> entity.needidentifier,
        'needmacaddress -> entity.needmacaddress,
        'needcontractobject -> entity.needcontractobject,
        'needrestriction -> entity.needrestriction,
        'config -> entity.config,
        'personalvlan -> entity.personalvlan,
        'ipfromparentrange -> entity.ipfromparentrange))
    SQL("""insert into inet_serv_type_1(
      title,
      parentTypeIds,
      sessionInitiationType,
      sessionCountLimit,
      sessionCountLimitLock,
      addressType,
      addressAllInterface,
      trafficTypeLinkId,
      needLogin,
      needDevice,
      needInterface,
      personalInterface,
      needVlan,
      needIdentifier,
      needMacAddress,
      needContractObject,
      needRestriction,
      config,
      personalVlan,
      ipFromParentRange
    ) values (
      {title},
      {parenttypeids},
      {sessioninitiationtype},
      {sessioncountlimit},
      {sessioncountlimitlock},
      {addresstype},
      {addressallinterface},
      {traffictypelinkid},
      {needlogin},
      {needdevice},
      {needinterface},
      {personalinterface},
      {needvlan},
      {needidentifier},
      {needmacaddress},
      {needcontractobject},
      {needrestriction},
      {config},
      {personalvlan},
      {ipfromparentrange}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetServType1)(implicit session: DBSession = autoSession): InetServType1 = {
    withSQL {
      update(InetServType1).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.parenttypeids -> entity.parenttypeids,
        column.sessioninitiationtype -> entity.sessioninitiationtype,
        column.sessioncountlimit -> entity.sessioncountlimit,
        column.sessioncountlimitlock -> entity.sessioncountlimitlock,
        column.addresstype -> entity.addresstype,
        column.addressallinterface -> entity.addressallinterface,
        column.traffictypelinkid -> entity.traffictypelinkid,
        column.needlogin -> entity.needlogin,
        column.needdevice -> entity.needdevice,
        column.needinterface -> entity.needinterface,
        column.personalinterface -> entity.personalinterface,
        column.needvlan -> entity.needvlan,
        column.needidentifier -> entity.needidentifier,
        column.needmacaddress -> entity.needmacaddress,
        column.needcontractobject -> entity.needcontractobject,
        column.needrestriction -> entity.needrestriction,
        column.config -> entity.config,
        column.personalvlan -> entity.personalvlan,
        column.ipfromparentrange -> entity.ipfromparentrange
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetServType1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetServType1).where.eq(column.id, entity.id) }.update.apply()
  }

}
