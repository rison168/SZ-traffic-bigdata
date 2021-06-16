package com.rison.traffic.scala.api.etl.application

import java.util.Properties

import com.rison.traffic.scala.api.etl.function.sink.JSon2RedisSinkFunction
import com.rison.traffic.scala.api.etl.function.source.Redis2KafkaSourceFunction
import com.rison.traffic.scala.common.util.PropertiesUtil
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011
import org.apache.flink.streaming.connectors.kafka.internal.FlinkKafkaProducer

/**
 * @author : Rison 2021/6/16 下午2:54
 *
 */
object Redis2Kafka {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val prop: Properties = PropertiesUtil.load("kafka.properties")
    val dataStream: DataStream[String] = env.addSource(new Redis2KafkaSourceFunction)

    dataStream.addSink(
      new FlinkKafkaProducer011[String](prop.getProperty("kafka.broker-list"), prop.getProperty("kafka-topic"), new SimpleStringSchema())
    )

    env.execute(this.getClass.getSimpleName.stripPrefix("$"))
  }
}
