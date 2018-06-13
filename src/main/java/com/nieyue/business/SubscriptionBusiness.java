package com.nieyue.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Subscription;
import com.nieyue.service.SubscriptionService;
import com.nieyue.util.MyDom4jUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
/**
 * 公众号业务逻辑
 * @author 聂跃
 * @date 2017年8月19日
 */
@Configuration
public class SubscriptionBusiness {
	@Autowired
	SubscriptionService subscriptionService;
    @Autowired
    WeiXinMpBusiness weiXinMpBusiness;

	/**
	 * 根据微信公众号appid获取微信服务
	 * @param appid 微信公众号appid
	 * @return wxMpService
	 */
	public WxMpService getWxMpService(
			String appid
			){
		 Wrapper<Subscription> wrapper=new EntityWrapper<Subscription>();
	 		Map<String,Object> map=new HashMap<String,Object>();
	 		map.put("appid", appid);
	 		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
	 		List<Subscription> sl = subscriptionService.list(1, 1, null, null, wrapper);
	 		WxMpService wxMpService = null;
		   if(sl.size()>0){
			   Subscription s = sl.get(0);
			try {
				wxMpService = weiXinMpBusiness.init(s.getAppid(), s.getAppsecret(), s.getToken(), "aes");
			} catch (WxErrorException e) {
			return wxMpService;
			}
		   }
		   return wxMpService;
		   }
}