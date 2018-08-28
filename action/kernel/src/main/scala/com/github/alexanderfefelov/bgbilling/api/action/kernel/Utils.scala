package com.github.alexanderfefelov.bgbilling.api.action.kernel

object Utils {

  def decodeContractGroupSelected(selected : Long): Seq[Int] = "1".r.findAllMatchIn(selected.toBinaryString.reverse).map(_.start).toList
  def encodeContractGroupSelected(groups: Seq[Int]): Long = groups.map(math.pow(2, _).toLong).sum

}
