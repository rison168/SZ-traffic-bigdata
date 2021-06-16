package com.rison.traffic.scala.api.etl.function.sink

import java.util.StringJoiner

import cn.hutool.core.io.FileUtil
import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.flink.streaming.api.functions.sink.SinkFunction

/**
 * @author : Rison 2021/6/16 下午5:57
 *
 */
case class MyCsvSinkFunction(day: String) extends SinkFunction[String]{
  val SAVE_PATH = "/tmp/szt-data/szt-data_" + day + ".csv"

  override def invoke(value: String, context: SinkFunction.Context[_]): Unit = {
    //11 个字段
    val jsonObject: JSONObject = JSON.parseObject(value)
    val deal_date = jsonObject.getString("deal_date")
    val close_date = jsonObject.getString("close_date")
    val card_no = jsonObject.getString("card_no")
    val deal_value = jsonObject.getString("deal_value")
    val deal_type = jsonObject.getString("deal_type")
    val company_name = jsonObject.getString("company_name")
    val car_no = jsonObject.getString("car_no")
    val station = jsonObject.getString("station")
    val conn_mark = jsonObject.getString("conn_mark")
    val deal_money = jsonObject.getString("deal_money")
    val equ_no = jsonObject.getString("equ_no")

    val csv = new StringJoiner(",")
      .add(close_date)
      .add(card_no)
      .add(deal_value)
      .add(deal_type)
      .add(company_name)
      .add(car_no)
      .add(station)
      .add(conn_mark)
      .add(deal_money)
      .add(equ_no)
      .toString
    FileUtil.appendUtf8String(csv + "\n", SAVE_PATH)
  }
}
