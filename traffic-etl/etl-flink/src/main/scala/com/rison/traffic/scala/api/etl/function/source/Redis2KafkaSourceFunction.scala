package com.rison.traffic.scala.api.etl.function.source

import com.alibaba.fastjson.JSON
import com.rison.traffic.scala.common.util.RedisUtil
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import redis.clients.jedis.Jedis

import scala.collection.JavaConversions.asScalaBuffer

/**
 * @author : Rison 2021/6/16 下午3:15
 *
 */
case class Redis2KafkaSourceFunction() extends RichSourceFunction[String]{
  lazy val jedisClient: Jedis = RedisUtil.getJedisCilent()
  override def run(sourceContext: SourceFunction.SourceContext[String]): Unit = {
    for (i <- 1 to 1337) {
      val v = jedisClient.hget("szt:pageJson", i + "")
      val json = JSON.parseObject(v)
      val array = json.getJSONArray("data")
      if (array.size() != 1000) {
        System.err.println(" ----- array size error ---- i=" + i) //这里没有问题
      }
      array.foreach(
        x => {
          val xStr = x.toString
          val data = JSON.parseObject(xStr)
          //if (data.size() != 11 && data.size() != 9) { //这里长度不统一，9|11
          if (data.size() != 11) { //这里长度不统一，9|11
            //System.err.println(" data error ------------------ x=" + x)// TODO 可选是否打印脏数据
          } else {
            // 只保留字段长度为 11 的源数据 ===> kafka: topic-flink-szt
            sourceContext.collect(xStr)
          }
        })
    }
  }

  override def cancel(): Unit = jedisClient.close()
}
