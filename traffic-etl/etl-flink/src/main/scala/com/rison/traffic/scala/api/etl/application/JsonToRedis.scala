package com.rison.traffic.scala.api.etl.application

import com.alibaba.fastjson.{JSON, JSONObject}
import com.rison.traffic.scala.api.etl.function.sink.JSon2RedisSinkFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.{FlinkJedisClusterConfig, FlinkJedisPoolConfig}

/**
 * @author : Rison 2021/6/16 下午2:33
 *
 */
object JsonToRedis {
  val SAVE_PATH = "/tmp/szt-data/szt-data-page.jsons"

  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val dataStream: DataStream[String] = env.readTextFile(SAVE_PATH)
    val dataJsonObject: DataStream[JSONObject] = dataStream
      .filter(_.nonEmpty)
      .map(
        json => {
          JSON.parseObject(json)
        }
      )

    //定义 redis 参数
    val jedis: FlinkJedisPoolConfig = new FlinkJedisPoolConfig.Builder()
      .setHost("localhost")
      .build()
    dataJsonObject.addSink(new RedisSink[JSONObject](jedis, new JSon2RedisSinkFunction))

    env.execute(this.getClass.getSimpleName.stripPrefix("$"))
  }

}
