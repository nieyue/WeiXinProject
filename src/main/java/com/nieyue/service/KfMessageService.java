package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.KfMessage;

/**
 * 客服消息逻辑层接口
 * @author yy
 *
 */
public interface KfMessageService extends BaseService<KfMessage, Long>{
	/**
	 * 群发客服消息
	 */
	public List<KfMessage> sendKfMessage(Long kfMessageId);
}
