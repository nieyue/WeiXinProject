package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.Sign;

/**
 * 签到逻辑层接口
 * @author yy
 *
 */
public interface SignService extends BaseService<Sign, Long>{
	/**
	 * 签到
	 * @param subscriptionId 公众号id
	 * @param accountId 账户id
	 * @param wxOpenid  微信openid
	 * @param wxUuid 微信uuid
	 * @return
	 */
	public  List<Sign> signSign(Long subscriptionId,Long accountId,String wxOpenid,String wxUuid); 
}
