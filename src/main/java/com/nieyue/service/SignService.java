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
	 * 账户签到
	 * @param subscriptionId 公众号id
	 * @param accountId 账户id
	 * @param wxUuid 微信uuid
	 * @return
	 */
	public  List<Sign> accountSign(Long subscriptionId,Long accountId,String wxUuid); 
	/**
	 * openid签到
	 * @param subscriptionId 公众号id
	 * @param wxOpenid  微信openid
	 * @return
	 */
	public  List<Sign> openidSign(Long subscriptionId,String wxOpenid); 
}
