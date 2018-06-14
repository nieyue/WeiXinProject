package com.nieyue.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nieyue.bean.KfArticle;
import com.nieyue.bean.KfMessage;
import com.nieyue.bean.Subscription;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.SubscriptionService;
import com.nieyue.weixin.mp.KfSessionHandler;
import com.nieyue.weixin.mp.LogHandler;
import com.nieyue.weixin.mp.MenuHandler;
import com.nieyue.weixin.mp.MsgHandler;
import com.nieyue.weixin.mp.NullHandler;
import com.nieyue.weixin.mp.ScanHandler;
import com.nieyue.weixin.mp.StoreCheckNotifyHandler;
import com.nieyue.weixin.mp.SubscribeHandler;
import com.nieyue.weixin.mp.UnsubscribeHandler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.EventType;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import net.sf.json.JSONObject;

/**
 * 微信公众号业务
 * @author 聂跃
 * @date 2018年6月8日
 */
@Configuration
public class WeiXinMpBusiness {
	//存放多个WxMpService
	Map<String,WxMpService> mapWxMpService=new HashMap<>();
	WxMpService wxMpService;
	Logger logger=LoggerFactory.getLogger(WeiXinMpBusiness.class);
	
	
	  @Autowired
	  protected SubscriptionService subscriptionService;
	  @Autowired
	  protected LogHandler logHandler;
	  @Autowired
	  protected NullHandler nullHandler;
	  @Autowired
	  protected KfSessionHandler kfSessionHandler;
	  @Autowired
	  protected StoreCheckNotifyHandler storeCheckNotifyHandler;
	 /* @Autowired
	  private LocationHandler locationHandler;*/
	  @Autowired
	  private MenuHandler menuHandler;
	  @Autowired
	  private MsgHandler msgHandler;
	  @Autowired
	  private UnsubscribeHandler unsubscribeHandler;
	  @Autowired
	  private SubscribeHandler subscribeHandler;
	  @Autowired
	  private ScanHandler scanHandler;
	  //类初始化,把所有的公众号配置放入内存
	  @PostConstruct
	  public void  WeiXinMpBusinessInit() {
		  List<Subscription> sl = subscriptionService.list(1, Integer.MAX_VALUE, null, null, null);
			sl.forEach((e)->{				
		  //初始化，不然新关注用户不能获取到账户信息
			try {
				init(e.getAppid(), e.getAppsecret(), e.getToken(), "aes");
			} catch (WxErrorException e1) {
				this.logger.info("初始化失败1");
			}
			});
		}
	  @Bean
	  public WxMpMessageRouter router() {
	    final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

	    // 记录所有事件的日志 （异步执行）
	    newRouter.rule().handler(this.logHandler).next();

	    // 接收客服会话管理事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
	        .handler(this.kfSessionHandler).end();
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
	        .handler(this.kfSessionHandler)
	        .end();
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
	        .handler(this.kfSessionHandler).end();

	    // 门店审核事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(WxMpEventConstants.POI_CHECK_NOTIFY)
	        .handler(this.storeCheckNotifyHandler).end();

	    // 自定义菜单事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(MenuButtonType.CLICK).handler(this.menuHandler).end();

	    // 点击菜单连接事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(MenuButtonType.VIEW).handler(this.nullHandler).end();

	    // 关注事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.SUBSCRIBE).handler(this.subscribeHandler)
	        .end();

	    // 取消关注事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.UNSUBSCRIBE)
	        .handler(this.unsubscribeHandler).end();

	    // 上报地理位置事件
	    /*newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.LOCATION).handler(this.locationHandler)
	        .end();*/

	    // 接收地理位置消息
	   /* newRouter.rule().async(false).msgType(XmlMsgType.LOCATION)
	        .handler(this.locationHandler).end();*/

	    // 扫码事件
	    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
	        .event(EventType.SCAN).handler(this.scanHandler).end();

	    // 默认
	    //newRouter.rule().async(false).handler(this.msgHandler).end();

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
	 * 初始化增加
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
		//如果存在直接取，
		if(mapWxMpService.get(appid)!=null){
		//System.out.println(appid);
		wxMpService=mapWxMpService.get(appid);
		}else{
			WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
			config.setAppId(appid); // 设置微信公众号的appid
			config.setSecret(secret); // 设置微信公众号的app corpSecret
			if(token==null||"".equals(token)){
				config.setToken("nieyue"); // 设置微信公众号的token						
			}else{
				config.setToken(token); // 设置微信公众号的token			
			}
			config.setAesKey(aesKey); // 设置微信公众号的EncodingAESKey
			wxMpService=new WxMpServiceImpl();
			wxMpService.setWxMpConfigStorage(config);
			mapWxMpService.put(appid, wxMpService);
		}
		return wxMpService;
	}
	/**
	 * 初始化修改
	 * @param appid 公众号appid
	 * @param secret 公众号 秘钥
	 * @param token 公众号服务token
	 * @param aesKey 加密协议
	 * @throws WxErrorException 
	 * @return  wxMpService 当前公众号服务
	 */
	public WxMpService initupdate(
			String appid,
			String secret,
			String token,
			String aesKey
			) throws WxErrorException{
		//如果存在直接取修改，
		if(mapWxMpService.get(appid)!=null){
			wxMpService=mapWxMpService.get(appid);
		}else{
			wxMpService=new WxMpServiceImpl();
		}
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(appid); // 设置微信公众号的appid
		config.setSecret(secret); // 设置微信公众号的app corpSecret
		if(token==null||"".equals(token)){
			config.setToken("nieyue"); // 设置微信公众号的token						
		}else{
			config.setToken(token); // 设置微信公众号的token			
		}
		config.setAesKey(aesKey); // 设置微信公众号的EncodingAESKey
		wxMpService.setWxMpConfigStorage(config);
		mapWxMpService.put(appid, wxMpService);
		return wxMpService;
	}
	/**
	 * 初始化删除
	 * @param appid 公众号appid
	 * @param secret 公众号 秘钥
	 * @param token 公众号服务token
	 * @param aesKey 加密协议
	 * @throws WxErrorException 
	 * @return  wxMpService 当前公众号服务
	 */
	public boolean initdelete(
			String appid,
			String secret,
			String token,
			String aesKey
			) throws WxErrorException{
		//如果存在直接取修改，
		if(mapWxMpService.get(appid)!=null){
			mapWxMpService.remove(appid);
			return true;
		}
		return false;
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
