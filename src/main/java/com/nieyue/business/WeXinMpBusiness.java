package com.nieyue.business;

import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import net.sf.json.JSONObject;

/**
 * 微信公众号业务
 * @author 聂跃
 * @date 2018年6月8日
 */
public class WeXinMpBusiness {
	
	 WxMpService wxMpService=new WxMpServiceImpl();// 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
	  
	/**
	 * 初始化网页
	 * @throws WxErrorException 
	 */
	public  void initJsApi(
			String redirectUrl
			){
		WxMpService wxMpService = new WxMpServiceImpl();
		//String url = WxMpService.CONNECT_OAUTH2_AUTHORIZE_URL;
		wxMpService.oauth2buildAuthorizationUrl(redirectUrl, WxConsts.OAuth2Scope.SNSAPI_BASE, null);
	}
	/**
	 * 跳转链接
	 * @throws WxErrorException 
	 */
	public  void redirectUrl(
			String code
			){
		try {
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			wxMpUser.getOpenId();
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 初始化
	 * @throws WxErrorException 
	 */
	public  void init(
			String appid,
			String secret,
			String token,
			String aesKey
			) throws WxErrorException{
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
		config.setAppId(appid); // 设置微信公众号的appid
		config.setSecret(secret); // 设置微信公众号的app corpSecret
		config.setToken(token); // 设置微信公众号的token
		config.setAesKey(aesKey); // 设置微信公众号的EncodingAESKey
		config.setAccessToken("10_BwHl2v2ZvCEZQC-N5SkxS1bYN9ZOvYpio5cgFHG2V2dTgaunYi7EfJVKVWaDYCExTJ0TUcgG_nOhbxIONLU7vrIddGN9f37CRjSz6hSOfS3rvSwFU-avXpDjuyrzeDfgpzbMyvlC8kNhL9rrUDLbAAALUC");
		wxMpService.setWxMpConfigStorage(config);
	}
	/**
	 * 客服消息
	 * @param args
	 * @throws WxErrorException 
	 * @throws Exception
	 */
	public  void sendWxMpKefuMessage() {
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
		WeXinMpBusiness weXinMpBusiness=new WeXinMpBusiness();
		weXinMpBusiness.init(appid,secret, token, aesKey);
		weXinMpBusiness.sendWxMpKefuMessage();
		//WeXinMpBusiness.initJsApi("http://www.baidu.com");
	}

}
