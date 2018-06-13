package com.nieyue.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nieyue.bean.KfArticle;
import com.nieyue.bean.KfMessage;
import com.nieyue.exception.CommonRollbackException;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.EventType;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
import net.sf.json.JSONObject;

/**
 * 微信公众号业务
 * @author 聂跃
 * @date 2018年6月8日
 */
@Configuration
public class WeiXinMpBusiness {
	//存放多个WxMpInMemoryConfigStorage
	Map<String,WxMpInMemoryConfigStorage> mapWxMpInMemoryConfigStorage=new HashMap<>();
	//存放多个WxMpService
	Map<String,WxMpService> mapWxMpService=new HashMap<>();
	WxMpService wxMpService;
	Logger logger=LoggerFactory.getLogger(WeiXinMpBusiness.class);
	

	  @Bean
	  public WxMpMessageRouter router() {
	    final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
	    // 接收客服会话管理事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
	        .handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			}).end();
	    
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
	        .handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			})
	        .end();
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
	        .handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			}).end();

	    // 门店审核事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.POI_CHECK_NOTIFY)
	        .handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			}).end();

	    // 自定义菜单事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(MenuButtonType.CLICK).handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			}).end();

	    // 点击菜单连接事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(MenuButtonType.VIEW).handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					System.out.println(111);
					System.out.println(context.toString());
					System.out.println(wxMessage.getFromUser());
					logger.info("点击菜单连接事件context={}", context);
		    		logger.info("点击菜单连接事件wxMessage={}", wxMessage);
					return null;
				}
			}).end();
	    // 点击菜单推事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	    .event(MenuButtonType.CLICK).handler(new WxMpMessageHandler() {
	    	
	    	@Override
	    	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
	    			WxSessionManager sessionManager) throws WxErrorException {
	    		System.out.println(111222222);
	    		System.out.println(context.toString());
				System.out.println(wxMessage.getFromUser());
	    		logger.info("点击菜单推事件context={}", context);
	    		logger.info("点击菜单推事件wxMessage={}", wxMessage);
	    		return null;
	    	}
	    }).end();

	    // 关注事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.SUBSCRIBE).handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			})
	        .end();

	    // 取消关注事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.UNSUBSCRIBE)
	        .handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			}).end();

	    // 上报地理位置事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.LOCATION).handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			})
	        .end();

	    // 接收地理位置消息
	    newRouter.rule().async(false).msgType(XmlMsgType.LOCATION)
	        .handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			}).end();

	    // 扫码事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.SCAN).handler(new WxMpMessageHandler() {
				
				@Override
				public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
						WxSessionManager sessionManager) throws WxErrorException {
					// TODO Auto-generated method stub
					return null;
				}
			}).end();

	    // 默认
	    newRouter.rule().async(false).handler(new WxMpMessageHandler() {
			
			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
					WxSessionManager sessionManager) throws WxErrorException {

			    if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
			      //TODO 可以选择将消息保存到本地
			    }

			    //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
			    try {
			      if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
			          && wxMpService.getKefuService().kfOnlineList()
			          .getKfOnlineList().size() > 0) {
			        return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
			            .fromUser(wxMessage.getToUser())
			            .toUser(wxMessage.getFromUser()).build();
			      }
			    } catch (WxErrorException e) {
			      e.printStackTrace();
			    }

			    //TODO 组装回复消息
			    String content = "收到信息内容：" + WxMpGsonBuilder.create().toJson(wxMessage);
			   return WxMpXmlOutMessage.TEXT().content(content)
			            .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
			            .build();

			  }
		}).end();

	    return newRouter;
	  }
	/**
	 * 初始化网页
	 * @throws WxErrorException 
	 */
	public  WxMpUser initJsApi(
			String redirectUrl
			){
		 wxMpService = new WxMpServiceImpl();
		//String url = WxMpService.CONNECT_OAUTH2_AUTHORIZE_URL;
		String code = wxMpService.oauth2buildAuthorizationUrl(redirectUrl, WxConsts.OAuth2Scope.SNSAPI_BASE, null);
		WxMpUser wxMpUser=redirectUrl(code);
		return wxMpUser;
	}
	/**
	 * 获取openid
	 * @throws WxErrorException 
	 */
	public  WxMpUser redirectUrl(
			String code
			){
		WxMpUser wxMpUser=null;
		try {
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			System.err.println(wxMpUser.getOpenId());
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wxMpUser;
	}
	/**
	 * 初始化
	 * @param appid 公众号appid
	 * @param secret 公众号 秘钥
	 * @param token 公众号服务token
	 * @param aesKey 加密协议
	 * @throws WxErrorException 
	 * @return  wxMpService 当前公众号服务
	 */
	public WxMpService init(
			String appid,
			String secret,
			String token,
			String aesKey
			) throws WxErrorException{
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(appid); // 设置微信公众号的appid
		config.setSecret(secret); // 设置微信公众号的app corpSecret
		if(token==null||"".equals(token)){
			config.setToken("nieyue"); // 设置微信公众号的token						
		}else{
			config.setToken(token); // 设置微信公众号的token			
		}
		config.setAesKey(aesKey); // 设置微信公众号的EncodingAESKey
		//如果存在直接取，
		if(mapWxMpInMemoryConfigStorage.get(appid)!=null){
		//System.out.println(appid);
		wxMpService=mapWxMpService.get(appid);
		}else{
			mapWxMpInMemoryConfigStorage.put(appid, config);
			wxMpService=new WxMpServiceImpl();
			wxMpService.setWxMpConfigStorage(config);
			mapWxMpService.put(appid, wxMpService);
		}
		return wxMpService;
	}
	/**
	 * 获取该公众号用户列表
	 * @throws WxErrorException
	 * @return openidList 当前公众号所有用户openid
	 */
	public List<String> getOpenidList() throws WxErrorException{
		List<String> openidList=new ArrayList<>();
		 //获取用户openid列表
		 WxMpUserList wxUserList = wxMpService.getUserService().userList(null);
		 List<String> list = wxUserList.getOpenids();
		 openidList.addAll(list);
		/* list.forEach((e)->{
				System.err.println("openid="+e);
			});*/
		 while(!StringUtils.isEmpty(wxUserList.getNextOpenid())){
			 wxUserList = wxMpService.getUserService().userList(wxUserList.getNextOpenid());
			List<String> list2 = wxUserList.getOpenids();
			openidList.addAll(list2);
			/*list2.forEach((e)->{
				System.err.println("openid="+e);
			});*/
		 }
		 return openidList;
	}
	/**
	 * 客服消息
	 * @param openidList 微信openid集合
	 * @param kfMessage 客服消息
	 * @param kfArticleList 客服消息文章，外链图文必选
	 * @throws WxErrorException 
	 * @throws Exception
	 */
	public  void sendWxMpKefuMessage(
			List<String> openidList,
			KfMessage kfMessage,
			List<KfArticle> kfArticleList
			) throws WxErrorException {
		//外链图文文章
		List<WxMpKefuMessage.WxArticle> list=new ArrayList<>();
		if(kfMessage.getMsgtype().equals("news")){
			kfArticleList.forEach((ka)->{
				WxMpKefuMessage.WxArticle wx=new WxMpKefuMessage.WxArticle();
				wx.setTitle(ka.getTitle());
				wx.setUrl(ka.getUrl());
				wx.setPicUrl(ka.getPicurl());
				wx.setDescription(ka.getDescription());
				list.add(wx);
			});
		}
		openidList.forEach((e)->{
			 WxMpKefuMessage message;
			if(kfMessage.getMsgtype().equals("text")){
				message = WxMpKefuMessage.TEXT().toUser(e).content(kfMessage.getContent()).build();
			}else if(kfMessage.getMsgtype().equals("image")){
				message = WxMpKefuMessage.IMAGE().toUser(e).mediaId(kfMessage.getMediaId()).build();
			}else if(kfMessage.getMsgtype().equals("voice")){
				message = WxMpKefuMessage.VOICE().toUser(e).mediaId(kfMessage.getMediaId()).build();
			}else if(kfMessage.getMsgtype().equals("video")){
				message = WxMpKefuMessage.VIDEO().toUser(e).mediaId(kfMessage.getMediaId()).thumbMediaId(kfMessage.getThumbMediaId()).title(kfMessage.getTitle()).description(kfMessage.getDescription()).build();
			}else if(kfMessage.getMsgtype().equals("music")){
				message = WxMpKefuMessage.MUSIC().toUser(e).thumbMediaId(kfMessage.getThumbMediaId()).title(kfMessage.getTitle()).description(kfMessage.getDescription()).musicUrl(kfMessage.getMusicurl()).hqMusicUrl(kfMessage.getHqmusicurl()).build();
			}else if(kfMessage.getMsgtype().equals("news")){
				message = WxMpKefuMessage.NEWS().toUser(e).articles(list).toUser(e).build();
			}else if(kfMessage.getMsgtype().equals("mpnews")){
				message = WxMpKefuMessage.MPNEWS().toUser(e).mediaId(kfMessage.getMediaId()).build();
			}else if(kfMessage.getMsgtype().equals("wxcard")){
				message = WxMpKefuMessage.WXCARD().toUser(e).cardId(kfMessage.getCardId()).build();
			}else{
				throw new CommonRollbackException("没有此客服类型");
			}
			/*//小程序暂无
			else if(kfMessage.getMsgtype().equals("miniprogrampage")){
			}*/
				try {
					wxMpService.getKefuService().sendKefuMessage(message);
				} catch (WxErrorException e1) {
					if(JSONObject.fromObject(e1.getMessage()).get("errcode").equals(45015)){
						//throw new CommonRollbackException("该用户不活跃");
						System.out.println("该用户不活跃");
					}
				}
		});
	}
	/**
	 * 客服消息
	 * @param args
	 * @throws WxErrorException 
	 * @throws Exception
	 */
	public  void sendWxMpKefuMessage2() {
		// 用户的openid在下面地址获得 
		// https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index&type=用户管理&form=获取关注者列表接口%20/user/get *--------------------------------------
		String openid = "oKs_907lCS6pb2tMNjzooOICN2_o";
		WxMpKefuMessage message = WxMpKefuMessage.TEXT().toUser(openid).content("Hello World!<br/><a href='http://www.baidu.com'>百度</a>").build();
		try {
			 wxMpService.getKefuService().sendKefuMessage(message);
		} catch (WxErrorException e) {
			if(JSONObject.fromObject(e.getMessage()).get("errcode").equals(45015)){
				//throw new CommonRollbackException("该用户不活跃");
				System.out.println("该用户不活跃");
			}
		}
	}
	/**
	 * 模板消息
	 * @param args
	 * @throws WxErrorException 
	 * @throws Exception
	 */
	public  void sendTemplateMsg() throws WxErrorException{
		//用户列表
		// wxService.getUserService().userList(openid);
		//w
		List<WxMpTemplateData> tl=new ArrayList<>();
		WxMpTemplateData wt=new WxMpTemplateData("a","sdfs地方<a href='http://www.baidu.com'>百度</a>水电费","red");
		WxMpTemplateData wt2=new WxMpTemplateData("b","sdfs地方2<a href='http://www.baidu.com'>百度2</a>水电费2","#000");
		tl.add(wt);
		tl.add(wt2);
		wxMpService.getTemplateMsgService().sendTemplateMsg(
				WxMpTemplateMessage.builder()
				//.toUser(openid)
				//.templateId("AXnL71biQYCba631H12ObXNOqCAzntwpZ2267INBgEQ")
				.templateId("SYrrEd3tdozrH1jfHAHUl_ordoEwUZcCeIpnFKneBW4")
				.url("http://www.baidu.com")
				.data(tl)
				.build());
	}
	public static void main(String[] args) throws Exception {
		String appid="wxc2526a4f29d74dc4";
		String secret="326c00a8da2764bce31d334302ed510e";
		String token="nieyue";
		//String aesKey="raw";
		String aesKey="aes";
		String accessToken="10_BwHl2v2ZvCEZQC-N5SkxS1bYN9ZOvYpio5cgFHG2V2dTgaunYi7EfJVKVWaDYCExTJ0TUcgG_nOhbxIONLU7vrIddGN9f37CRjSz6hSOfS3rvSwFU-avXpDjuyrzeDfgpzbMyvlC8kNhL9rrUDLbAAALUC";
		WeiXinMpBusiness weXinMpBusiness=new WeiXinMpBusiness();
		weXinMpBusiness.init(appid,secret, token, aesKey);
		weXinMpBusiness.sendWxMpKefuMessage2();
		weXinMpBusiness.init(appid,secret, token, aesKey);
		weXinMpBusiness.sendWxMpKefuMessage2();
		//WeXinMpBusiness.initJsApi("http://www.baidu.com");
		weXinMpBusiness.initJsApi("http://www.baidu.com");
	}

}
