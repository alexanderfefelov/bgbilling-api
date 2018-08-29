package com.github.alexanderfefelov.bgbilling.api.action.kernel

object KernelActionUtils {

  def decodeContractGroupMask(selected : Long): Seq[Int] = "1".r.findAllMatchIn(selected.toBinaryString.reverse).map(_.start).toList
  def encodeContractGroupMask(groupIds: Seq[Int]): Long = groupIds.map(math.pow(2, _).toLong).sum

}
