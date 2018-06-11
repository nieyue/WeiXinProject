package com.nieyue;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.toolkit.StringUtils;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

public class Test1 {
	public static void main(String[] args) throws WxErrorException {
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId("wxc2526a4f29d74dc4"); // 设置微信公众号的appid
		config.setSecret("326c00a8da2764bce31d334302ed510e"); // 设置微信公众号的app corpSecret
		config.setToken("nieyue"); // 设置微信公众号的token
		config.setAesKey("明文模式"); // 设置微信公众号的EncodingAESKey
		config.setAccessToken("10_8EoD8UnF9oAyl5mEiTBqNRfwYizkKsd9FdYH_gUEm76CgNGkEM7fTVttriqydUv-AYWOZJ3CqUInAR9OAx0Y-y7jQM-83e5o5qf2QhD0xlDBJEP1GWEWCdsOVs1vg_t4d26NaKXc54shHm2JIWGaAHAOCM");

		WxMpService wxService = new WxMpServiceImpl();// 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
		wxService.setWxMpConfigStorage(config);

		// 用户的openid在下面地址获得 
		// https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index&type=用户管理&form=获取关注者列表接口%20/user/get *--------------------------------------
		String openid = "oKs_907lCS6pb2tMNjzooOICN2_o";
		WxMpKefuMessage message = WxMpKefuMessage.TEXT().toUser(openid).content("Hello World!<br/><a href='http://www.baidu.com'>百度</a>").build();
		//wxService.getKefuService().sendKefuMessage(message);
		
		//用户列表
		// wxService.getUserService().userList(openid);
		 //w
		List<WxMpTemplateData> tl=new ArrayList<>();
		WxMpTemplateData wt=new WxMpTemplateData("a","sdfs地方<a href='http://www.baidu.com'>百度</a>水电费","red");
		WxMpTemplateData wt2=new WxMpTemplateData("b","sdfs地方2<a href='http://www.baidu.com'>百度2</a>水电费2","#000");
		tl.add(wt);
		tl.add(wt2);
		 wxService.getTemplateMsgService().sendTemplateMsg(
				 WxMpTemplateMessage.builder()
				 .toUser(openid)
				 //.templateId("AXnL71biQYCba631H12ObXNOqCAzntwpZ2267INBgEQ")
				 .templateId("SYrrEd3tdozrH1jfHAHUl_ordoEwUZcCeIpnFKneBW4")
				 .url("http://www.baidu.com")
				 .data(tl)
				 .build());
		 String lang = "zh_CN"; //语言
		//获取用户信息
		 WxMpUser user = wxService.getUserService().userInfo(openid,lang);
		 System.err.println(user.toString());
		 //获取用户openid列表
		 WxMpUserList wxUserList = wxService.getUserService().userList(null);
		 List<String> list = wxUserList.getOpenids();
		 list.forEach((e)->{
				System.err.println("openid="+e);
			});
		 while(!StringUtils.isEmpty(wxUserList.getNextOpenid())){
			 wxUserList = wxService.getUserService().userList(wxUserList.getNextOpenid());
			List<String> list2 = wxUserList.getOpenids();
			list2.forEach((e)->{
				System.err.println("openid="+e);
			});
		 }
		 

	}
}
