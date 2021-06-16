package com.rison.traffic.scala.common.util

import cn.hutool.core.util.{StrUtil}
import org.junit.Test

import scala.collection.mutable

/**
 * @author : Rison 2021/6/16 上午9:31
 *         支持自动识别明文和密文，一键互转
 *
 *         日志当中卡号脱敏字段密文反解猜想，
 *         由脱敏的密文卡号反推真实卡号，
 *         因为所有卡号密文当中没有J开头的数据，
 *         但是有A开头的数据，A != 0，
 *         所以卡号映射关系如图！！！ .file/.pic/parse_card_no.png
 *         类似摩斯电码解密。。。
 */
object ParseCardNo {
  val char2NoMap = mutable.Map[String, String](
    "A" -> "1",
    "B" -> "2",
    "C" -> "3",
    "D" -> "4",
    "E" -> "5",
    "F" -> "6",
    "G" -> "7",
    "H" -> "8",
    "I" -> "9",
    "J" -> "0",
    "1" -> "A",
    "2" -> "B",
    "3" -> "C",
    "4" -> "D",
    "5" -> "E",
    "6" -> "F",
    "7" -> "G",
    "8" -> "H",
    "9" -> "I",
    "0" -> "J"
  )

  /**
   * 转换
   *
   * @param no
   * @return
   */
  def parse(no: String): StringBuffer = {
    if (StrUtil.isBlank(no)) {
      throw new Exception("不能为空字段！")
    }
    val noCharArr: Array[Char] = no.toCharArray
    val resultBuffer = new StringBuffer()
    noCharArr.foreach(
      ch => {
        resultBuffer.append(
          char2NoMap.get(ch.toString) match {
            case Some(i) => i
            case None => throw new Exception("包含非法字段！")
          }
        )
      }
    )
    resultBuffer
  }

  def main(args: Array[String]): Unit = {
    println(parse("FFEBFACFDO"))
  }
}
