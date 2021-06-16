package com.rison.traffic.scala.common.util

import java.util.Properties

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
 * @author : Rison 2021/6/16 下午3:18
 *Redis工具类
 */
object RedisUtil {
   var jedisPool: JedisPool = _
   val prop: Properties  = PropertiesUtil.load("application.properties")

   /**
    * 创建连接池
    */
   def build() = {
      val jedisPoolConfig = new JedisPoolConfig()
      jedisPoolConfig.setMaxTotal(200) //最大连接数
      jedisPoolConfig.setMaxIdle(20) //连接池中最大空闲的连接数
      jedisPoolConfig.setMinIdle(20) //最小空闲
      jedisPoolConfig.setBlockWhenExhausted(true) //忙碌时是否等待
      jedisPoolConfig.setMaxWaitMillis(2000) //忙碌时等待时长 毫秒
      jedisPoolConfig.setTestOnBorrow(false) //每次获得连接的进行测试
      jedisPool = new JedisPool(jedisPoolConfig, prop.getProperty("redis.host"), prop.getProperty("redis.port").toInt)

   }

   /**
    * 获取jedis
    * @return
    */
   def getJedisCilent(): Jedis = {
      if (jedisPool == null) {
         build
      }
      jedisPool.getResource
   }
}
