package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.TemplateMessage;

/**
 * 模板消息逻辑层接口
 * @author yy
 *
 */
public interface TemplateMessageService extends BaseService<TemplateMessage, Long>{
	/**
	 * 群发模板消息
	 */
	public List<TemplateMessage> sendTemplateMessage(Long templateMessageId);
}
