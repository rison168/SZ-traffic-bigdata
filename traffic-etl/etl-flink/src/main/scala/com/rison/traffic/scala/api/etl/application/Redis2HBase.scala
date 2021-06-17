package com.rison.traffic.scala.api.etl.application

import com.rison.traffic.scala.api.etl.function.source.MyRedisSourceFunction
import org.apache.flink.streaming.api.scala._

/**
 * @author : Rison 2021/6/17 上午8:23
 *从 redis 或者其他数据源取出 json 串，保存到 hbase 表。
 */
object Redis2HBase {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val dataStream: DataStream[String] = env.addSource(
      MyRedisSourceFunction()
    )

    dataStream.addSink(MyHBaseSinkFunction("flink2hbase", "flink", 10))
    env.execute(this.getClass.getSimpleName.stripPrefix("$"))
  }
}
