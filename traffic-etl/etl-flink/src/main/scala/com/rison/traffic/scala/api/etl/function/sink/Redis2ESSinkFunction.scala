package com.rison.traffic.scala.api.etl.function.sink

import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSinkFunction, RequestIndexer}
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.common.xcontent.XContentType

/**
 * @author : Rison 2021/6/16 下午5:16
 *
 */
case class Redis2ESSinkFunction(index: String) extends ElasticsearchSinkFunction[String]{
  override def process(t: String, runtimeContext: RuntimeContext, requestIndexer: RequestIndexer): Unit = {
    val jSONObject: JSONObject = JSON.parseObject(t)
    val request: IndexRequest = new IndexRequest(index).source(
      jSONObject,
      XContentType.JSON
    )
    requestIndexer.add(request)
  }
}
