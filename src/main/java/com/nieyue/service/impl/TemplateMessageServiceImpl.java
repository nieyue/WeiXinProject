package com.nieyue.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Subscription;
import com.nieyue.bean.TemplateData;
import com.nieyue.bean.TemplateMessage;
import com.nieyue.business.WeiXinMpBusiness;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.SubscriptionService;
import com.nieyue.service.TemplateDataService;
import com.nieyue.service.TemplateMessageService;
import com.nieyue.util.MyDom4jUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import net.sf.json.JSONObject;
@Service
public class TemplateMessageServiceImpl extends BaseServiceImpl<TemplateMessage,Long> implements TemplateMessageService{
	@Autowired
	WeiXinMpBusiness weiXinMpBusiness;
	@Autowired
	SubscriptionService subscriptionService;
	@Autowired
	TemplateDataService templateDataService;
	@Override
	public List<TemplateMessage> sendTemplateMessage(Long templateMessageId) {
		List<TemplateMessage> list=new ArrayList<>();
		TemplateMessage templateMessage = this.load(templateMessageId);
		if(templateMessage==null){
			throw new CommonRollbackException("模板消息不存在");
		}
		Subscription subscription = subscriptionService.load(templateMessage.getSubscriptionId());
		if(subscription==null){
			throw new CommonRollbackException("公众号不存在");
		}
		if(StringUtils.isEmpty(subscription.getAppid())
				||StringUtils.isEmpty(subscription.getAppid())
				){
			throw new CommonRollbackException("公众号缺少appid或者appsecret");
		}
		try {
			//初始化公众号
			weiXinMpBusiness.init(subscription.getAppid(), subscription.getAppsecret(), subscription.getToken(), "aes");
			//获取所有账户			
			List<String> openidList=weiXinMpBusiness.getOpenidList();
			//模板消息数据
			List<TemplateData> templateDataList = null;
			Wrapper<TemplateData> wrapper=new EntityWrapper<>();
		 	Map<String,Object> map=new HashMap<String,Object>();
		 	map.put("template_message_id", templateMessageId);
		 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		 	templateDataList = templateDataService.list(1, Integer.MAX_VALUE, "updateDate", "desc", wrapper);
			weiXinMpBusiness.sendWxMpTemplateMessage(openidList, templateMessage, templateDataList);
		} catch (WxErrorException e) {
			if(JSONObject.fromObject(e.getMessage()).get("errcode").equals(45015)){
				//throw new CommonRollbackException("该用户不活跃");
				System.out.println("该用户不活跃");
				
			}else{
				System.out.println("微信接口异常");
				//throw new CommonRollbackException("微信接口异常");				
			}
		}
		list.add(templateMessage);
		return list;
	}
	
}
