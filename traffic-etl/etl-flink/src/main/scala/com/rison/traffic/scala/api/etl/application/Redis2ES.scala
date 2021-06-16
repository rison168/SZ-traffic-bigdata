package com.rison.traffic.scala.api.etl.application

import com.rison.traffic.scala.api.etl.function.sink.Redis2ESSinkFunction
import com.rison.traffic.scala.api.etl.function.source.Redis2KafkaSourceFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink
import org.apache.http.HttpHost



/**
 * @author : Rison 2021/6/16 下午5:08
 *
 */
object Redis2ES {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val dataStream: DataStream[String] = env.addSource(new Redis2KafkaSourceFunction)
    val hosts: List[HttpHost] = List[HttpHost](
      new HttpHost("cdh231", 9200),
      new HttpHost("cdh232", 9200),
      new HttpHost("cdh233", 9200)
    )

    import scala.collection.JavaConverters._
    val esSink: ElasticsearchSink[String] = new ElasticsearchSink.Builder(
      hosts.asJava, new Redis2ESSinkFunction("sz-data")
    ).build()

    dataStream.addSink(esSink)

    env.execute(this.getClass.getSimpleName.stripPrefix("$"))

  }

}
