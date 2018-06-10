package com.nieyue.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.KfArticle;
import com.nieyue.bean.KfMessage;
import com.nieyue.bean.Subscription;
import com.nieyue.business.WeXinMpBusiness;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.KfArticleService;
import com.nieyue.service.KfMessageService;
import com.nieyue.service.SubscriptionService;
import com.nieyue.util.MyDom4jUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import net.sf.json.JSONObject;
@Service
public class KfMessageServiceImpl extends BaseServiceImpl<KfMessage,Long> implements KfMessageService{
	@Autowired
	WeXinMpBusiness weXinMpBusiness;
	@Autowired
	SubscriptionService subscriptionService;
	@Autowired
	KfArticleService kfArticleService;
	/**
	 * 群发客服消息
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public List<KfMessage> sendKfMessage(Long kfMessageId) {
		List<KfMessage> list=new ArrayList<>();
		KfMessage kfMessage = this.load(kfMessageId);
		if(kfMessage==null){
			throw new CommonRollbackException("客服消息不存在");
		}
		Subscription subscription = subscriptionService.load(kfMessage.getSubscriptionId());
		if(subscription==null){
			throw new CommonRollbackException("公众号不存在");
		}
		if(StringUtils.isEmpty(subscription.getAppid())
				||StringUtils.isEmpty(subscription.getAppid())
				){
			throw new CommonRollbackException("公众号缺少appid或者appsecret");
		}
		try {
			weXinMpBusiness.init(subscription.getAppid(), subscription.getAppsecret(), null, "aes");
			List<String> openidList=new ArrayList<>();
			openidList.add("oKs_907lCS6pb2tMNjzooOICN2_o");
			
			Wrapper<KfArticle> wrapper=new EntityWrapper<KfArticle>();
		 	Map<String,Object> map=new HashMap<String,Object>();
		 	map.put("kf_message_id", kfMessageId);
		 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		 	List<KfArticle> kfArticleList = kfArticleService.list(1, Integer.MAX_VALUE, "updateDate", "desc", wrapper);
			weXinMpBusiness.sendWxMpKefuMessage(openidList, kfMessage, kfArticleList);
		} catch (WxErrorException e) {
			if(JSONObject.fromObject(e.getMessage()).get("errcode").equals(45015)){
				//throw new CommonRollbackException("该用户不活跃");
				System.out.println("该用户不活跃");
				
			}else{
				System.out.println("微信接口异常");
				//throw new CommonRollbackException("微信接口异常");				
			}
		}
		list.add(kfMessage);
		return list;
	}
	
}
