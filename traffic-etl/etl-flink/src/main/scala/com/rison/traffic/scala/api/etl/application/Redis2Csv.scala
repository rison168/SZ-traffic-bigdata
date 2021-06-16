package com.rison.traffic.scala.api.etl.application

import com.alibaba.fastjson.{JSON, JSONObject}
import com.rison.traffic.scala.api.etl.function.sink.MyCsvSinkFunction
import com.rison.traffic.scala.api.etl.function.source.MyRedisSourceFunction
import org.apache.flink.streaming.api.scala._

/**
 * @author : Rison 2021/6/16 下午5:40
 * redis -> csv
 * csv 按天保存
 */
object Redis2Csv {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val date = "2018-09-01"
    val dataStream: DataStream[String] = env.addSource(MyRedisSourceFunction())
    dataStream
      .filter(
        json => {
          val jSONObject: JSONObject = JSON.parseObject(json)
          val deal_date: String = jSONObject.getString("deal_date")
          deal_date.startsWith(date)
        }
      )
      .addSink(MyCsvSinkFunction(date))
    env.execute(this.getClass.getSimpleName.stripPrefix("$"))

  }
}
