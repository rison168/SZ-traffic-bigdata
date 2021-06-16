package com.rison.traffic.scala.api.etl.function.sink

import com.alibaba.fastjson.JSONObject
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}

/**
 * @author : Rison 2021/6/16 下午2:44
 *
 */
case class JSon2RedisSinkFunction() extends RedisMapper[JSONObject] {
  override def getCommandDescription: RedisCommandDescription = {
    new RedisCommandDescription(RedisCommand.HSET, "szt:pageJson")
  }

  override def getKeyFromData(t: JSONObject): String = {
    t.getIntValue("page").toString
  }

  override def getValueFromData(t: JSONObject): String = {
    t.toString
  }
}
