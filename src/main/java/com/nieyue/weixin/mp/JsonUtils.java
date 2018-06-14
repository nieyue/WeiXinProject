package com.nieyue.weixin.mp;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * gson工具
 */
public class JsonUtils {
  public static String toJson(Object obj) {
    return WxMpGsonBuilder.create().toJson(obj);
  }
}
