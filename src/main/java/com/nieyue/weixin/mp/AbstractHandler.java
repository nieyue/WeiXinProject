package com.nieyue.weixin.mp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;

/**
 * 处理微信推送消息的处理器接口
 */
public abstract class AbstractHandler implements WxMpMessageHandler {
  protected Logger logger = LoggerFactory.getLogger(getClass());
}
