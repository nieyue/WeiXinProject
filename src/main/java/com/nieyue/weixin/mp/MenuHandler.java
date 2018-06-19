package com.nieyue.weixin.mp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Subscription;
import com.nieyue.service.SignService;
import com.nieyue.service.SubscriptionService;
import com.nieyue.util.MyDom4jUtil;

import me.chanjar.weixin.common.api.WxConsts.EventType;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 菜单
 */
@Component
public class MenuHandler extends AbstractHandler {
	@Autowired
	SubscriptionService subscriptionService;
	@Autowired
	SignService signService;
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {

    /*String msg = String.format("type:%s, event:%s, key:%s",
        wxMessage.getMsgType(), wxMessage.getEvent(),
        wxMessage.getEventKey());*/
    if (MenuButtonType.VIEW.equals(wxMessage.getEvent())) {
    	
    	System.out.println("view");
      return null;
    }
    //String projectDomainUrl="http://nieyue.free.ngrok.cc";
    //String msg="\n>>><a href='"+projectDomainUrl+"/home/sign_prize.html?openid="+wxMessage.getFromUser()+"'>点击查看签到礼品</a>";
    //点击事件,签到
    if(EventType.CLICK.equals(wxMessage.getEvent())){
    	//if(EventType.CLICK.equals(wxMessage.getEvent())){
    	if(wxMessage.getEventKey().equals("签到有礼")){
    		//System.err.println("签到有礼");
    		//System.err.println("appid="+wxMpService.getWxMpConfigStorage().getAppId());
    		Wrapper<Subscription> wrapper=new EntityWrapper<>();
    	 	Map<String,Object> map=new HashMap<String,Object>();
    	 	map.put("appid", wxMpService.getWxMpConfigStorage().getAppId());
    	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
    		List<Subscription> sl = subscriptionService.list(1, 1, null, null, wrapper);
    		if(sl.size()>0){
    			Subscription s = sl.get(0);
    			List<String> r = signService.openidSign(s.getSubscriptionId(), wxMessage.getFromUser());
    			return WxMpXmlOutMessage.TEXT().content(r.get(0))
    			        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
    			        .build();
    		}
    	}
    }
    return null;
  }

}
