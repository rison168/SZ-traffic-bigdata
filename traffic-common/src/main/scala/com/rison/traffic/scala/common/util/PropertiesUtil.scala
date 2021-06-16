package com.rison.traffic.scala.common.util

import java.util.Properties

/**
 * @author : Rison 2021/6/16 下午2:57
 * Properties 工具类
 */
object PropertiesUtil {
  var prop: Properties = _

  def load(propName: String): Properties = {
    prop = new Properties()
    prop.load(
      Thread.currentThread().getContextClassLoader.getResourceAsStream(propName)
    )
    prop
  }
}
