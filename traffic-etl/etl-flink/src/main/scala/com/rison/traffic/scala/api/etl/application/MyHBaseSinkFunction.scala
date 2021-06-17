package com.rison.traffic.scala.api.etl.application

import java.io.IOException

import cn.hutool.json.{JSONObject, JSONUtil}
import org.apache.commons.lang3.StringUtils
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.hadoop.conf
import org.apache.hadoop.hbase.client.{Admin, Connection, ConnectionFactory, Put, Table}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, NamespaceDescriptor, NamespaceNotFoundException, TableName}
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

/**
 * @author : Rison 2021/6/17 上午8:27
 *
 */
case class MyHBaseSinkFunction(tableName: String, nameSpace: String, version: Int) extends RichSinkFunction[String] {

  val zk = "cdh231,cdh232,cdh233"
  val cf = "info"
  var conn: Connection = _
  var table: Table = _

  override def open(parameters: Configuration): Unit = {
    val config: conf.Configuration = HBaseConfiguration.create
    config.set("hbase.zookeeper.quorum", zk)
    val admin: Admin = conn.getAdmin
    admin.getNamespaceDescriptor(nameSpace)
    val strTableName: TableName = TableName.valueOf(nameSpace + ":" + tableName)
    if (!admin.tableExists(strTableName)){
      admin.createTable(
        new HTableDescriptor(nameSpace).addFamily(new HColumnDescriptor(cf).setMaxVersions(version))
      )
    }
    table = conn.getTable(strTableName)

  }

  override def invoke(value: String, context: SinkFunction.Context[_]): Unit = {
    insert(value)

  }

  override def close(): Unit = {
    conn.close()
  }

  def putCell(rowKey: String, family: String, column: String, value: String) = {
    val put = new Put(Bytes.toBytes(rowKey))
    put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value))
    table.put(put)
  }

  def insert(jsonStr: String) = {
    val jSONObject: JSONObject = JSONUtil.parseObj(jsonStr)
    val card_no_re: String = StringUtils.reverse(jSONObject.getStr("card_no"))
    val keys = jSONObject.keySet().toList
    val size = keys.size

    for (i <- 0 until size) {
      val key = keys.get(i)
      val value = jSONObject.getStr(key)
      putCell(card_no_re, cf, key, value)
    }
  }

}
