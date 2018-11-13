package com.nieyue.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nieyue.bean.Subscription;
import com.nieyue.bean.TemplateMessage;
import com.nieyue.business.SubscriptionBusiness;
import com.nieyue.business.WeiXinMpBusiness;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.SubscriptionService;
import com.nieyue.service.TemplateMessageService;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.menu.WxMpSelfMenuInfo.WxMpSelfMenuButton;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
/**
 * 微信公众号类
 * @author yy
 *
 */
@Api(tags={"weixin"},value="微信公众号",description="微信公众号接口管理")
@RestController
@RequestMapping("/weixin")
public class WeiXinController extends BaseController<Object,Long>{
	@Autowired
	SubscriptionBusiness subscriptionBusiness;
	@Autowired
	WeiXinMpBusiness weiXinMpBusiness;
	@Autowired
	TemplateMessageService templateMessageService;
	@Autowired
	SubscriptionService subscriptionService;
	/**
	 * 微信门户
	 * @return
	 */
	@ApiOperation(value = "微信服务门户，接受text", notes = "微信服务门户，接受text")
	@GetMapping(value = "/portal/{appid}",produces = "text/plain;charset=utf-8")
	public String weixinPortal(
			@RequestParam(name = "signature",required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr,
			@PathVariable(value="appid") String appid,
			HttpSession	 session
			){
	    if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
	      throw new CommonRollbackException("请求参数非法，请核实!");
	    }
		WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
		if (wxMpService!=null&&wxMpService.checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}		   
	    return "非法请求";
	  }
	@ApiOperation(value = "微信服务门户，接受xml", notes = "微信服务门户，接受xml")
	@PostMapping(value = "/portal/{appid}",produces = "application/xml; charset=UTF-8")
	  public String post(@RequestBody String requestBody,
	                     @RequestParam("signature") String signature,
	                     @RequestParam("timestamp") String timestamp,
	                     @RequestParam("nonce") String nonce,
	                     @RequestParam(name = "encrypt_type",
	                         required = false) String encType,
	                     @RequestParam(name = "msg_signature",
	                         required = false) String msgSignature,
	                 	@PathVariable(value="appid") String appid) {
		 WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
	    if (wxMpService==null||!wxMpService.checkSignature(timestamp, nonce, signature)) {
	      throw new CommonRollbackException("请求参数非法，请核实!");
	    }

	    String out = null;
	    if (encType == null) {
	      // 明文传输的消息
	      WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
	      WxMpXmlOutMessage outMessage = this.route(inMessage,appid);
	      if (outMessage == null) {
	        return "";
	      }

	      out = outMessage.toXml();
	    } else if ("aes".equals(encType)) {
	      // aes加密的消息
	      WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
	          requestBody, wxMpService.getWxMpConfigStorage(), timestamp,
	          nonce, msgSignature);
	      this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
	      WxMpXmlOutMessage outMessage = this.route(inMessage,appid);
	      if (outMessage == null) {
	        return "";
	      }

	      out = outMessage
	          .toEncryptedXml(wxMpService.getWxMpConfigStorage());
	    }

	    this.logger.debug("\n组装回复信息：{}", out);

	    return out;
	  }

	  private WxMpXmlOutMessage route(WxMpXmlMessage message,String appid) {
	    try {
	    	//return this.router.route(message);
	    	//更改为appid的服务
	    	WxMpMessageRouter newRouter=new WxMpMessageRouter(subscriptionBusiness.getWxMpService(appid));
	      return weiXinMpBusiness.router(newRouter).route(message);
	    } catch (Exception e) {
	      this.logger.error(e.getMessage(), e);
	    }

	    return null;
	  }
	/**
	   * <pre>
	   * 自定义菜单创建接口
	   * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN
	   * 如果要创建个性化菜单，请设置matchrule属性
	   * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
	   * </pre>
	   *
	   * @param menu
	   * @return 如果是个性化菜单，则返回menuid，否则返回null
	   */
	 @ApiOperation(value = "微信菜单创建", notes = "微信菜单创建")
	  @RequestMapping(value="/menu/create", method = {RequestMethod.GET,RequestMethod.POST})
	  public String menuCreate(
			  @RequestBody WxMenu menu,
			  //@RequestParam(value="menu") String menu,
			  @RequestParam(value="appid") String appid
			  ) throws WxErrorException {
		  WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
		  return wxMpService.getMenuService().menuCreate(menu);
	  }
	  @ApiOperation(value = "微信菜单创建", notes = "微信菜单创建")
	  @RequestMapping(value="/menu/testcreate", method = {RequestMethod.GET,RequestMethod.POST})
	  public  StateResultList<List<String>> menuCreateSample(
			  @RequestParam(value="appid") String appid
			  ) throws WxErrorException {
		  WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
		    //WxMpMenu oldMenu = wxMpService.getMenuService().menuGet();
		   // List<WxMenuButton> buttons = oldMenu.getMenu().getButtons();
		    WxMpGetSelfMenuInfoResult osmi = wxMpService.getMenuService().getSelfMenuInfo();
		    List<WxMpSelfMenuButton> buttons2 = osmi.getSelfMenuInfo().getButtons();
		    
		  List<WxMenuButton> buttons = new ArrayList<>();
		  for (int i = 0; i < buttons2.size(); i++) {
			  WxMenuButton button = new WxMenuButton();
			  WxMpSelfMenuButton button2 = buttons2.get(i);
			  button.setAppId(appid);
			 // button.setKey(button2.getKey());
			 // button.setName(button2.getName());
			  //button.setType(button2.getType());
			  //button.setUrl(button2.getUrl());
			  org.springframework.beans.BeanUtils.copyProperties(button2, button);
			  List<WxMenuButton> ll=new ArrayList<>();
			  for (int j = 0; j <button2.getSubButtons().getSubButtons().size(); j++) {
				  WxMenuButton subbutton = new WxMenuButton();
				  WxMpSelfMenuButton subbutton2 = button2.getSubButtons().getSubButtons().get(j);
				 // subbutton.setAppId(appid);
				 // subbutton.setKey(subbutton2.getKey());
				  //subbutton.setName(subbutton2.getName());
				  //subbutton.setType(subbutton2.getType());
				 // subbutton.setUrl(subbutton2.getUrl());
				  subbutton.setMediaId(subbutton2.getValue());
				  org.springframework.beans.BeanUtils.copyProperties(subbutton2, subbutton);
				  ll.add(subbutton);
			}
			  button.setSubButtons(ll);
			  buttons.add(button);
			  
		}
		    boolean haveButton=false;//是否已经有了
		    //循环当前的所有按钮找到签到按钮
		   loop: for (int i = 0; i < buttons.size(); i++) {
		    	WxMenuButton button = buttons.get(i);
		    	if(button.getSubButtons().size()>0){
		    		for (int j = 0; j < button.getSubButtons().size(); j++) {
		    			WxMenuButton subButton = button.getSubButtons().get(j);
		    			if(subButton.getName().equals("签到有礼")){
		    				subButton.setType("click");
		    				subButton.setKey("签到有礼");
		    				haveButton=true;
		    				break loop;
		    			}
		    		}
		    	}else{
		    		if(button.getName().equals("签到有礼")){
	    				button.setType("click");
	    				button.setKey("签到有礼");
	    				haveButton=true;
	    				break loop;
	    			}
		    	}
			}
		    WxMenu menu = new WxMenu();
		    //不存在，新建一个
		    if(!haveButton){
		    	WxMenuButton subButton = new WxMenuButton();
		    	subButton.setName("签到有礼");
		    	subButton.setType("click");
		    	subButton.setKey("签到有礼");
		    	WxMenuButton lastbutton = buttons.get(buttons.size()-1);
		    	lastbutton.getSubButtons().add(subButton);
		    	//放在最后一个
		    	buttons.get(buttons.size()-1).setSubButtons(lastbutton.getSubButtons());
		    }
		    //放入
		    menu.setButtons(buttons);
		    StateResultList<List<String>> srl=new  StateResultList<>();
		    srl.setCode(200);
		    srl.setMsg("成功");
		    List<String> list=new ArrayList<>();
		    list.add(wxMpService.getMenuService().menuCreate(menu));
		    srl.setData(list);
		    return srl;
		 /*   WxMpGetSelfMenuInfoResult osmi = wxMpService.getMenuService().getSelfMenuInfo();
		    List<WxMpSelfMenuButton> buttons = osmi.getSelfMenuInfo().getButtons();
		    boolean haveButton=false;//是否已经有了
		    //循环当前的所有按钮找到签到按钮
		   loop: for (int i = 0; i < buttons.size(); i++) {
			   WxMpSelfMenuButton button = buttons.get(i);
		    	if(button.getSubButtons().getSubButtons().size()>0){
		    		for (int j = 0; j < button.getSubButtons().getSubButtons().size(); j++) {
		    			WxMpSelfMenuButton subButton = button.getSubButtons().getSubButtons().get(j);
		    			if(subButton.getName().equals("签到有礼")){
		    				subButton.setType("click");
		    				subButton.setKey("签到有礼");
		    				haveButton=true;
		    				break loop;
		    			}
		    		}
		    	}else{
		    		if(button.getName().equals("签到有礼")){
	    				button.setType("click");
	    				button.setKey("签到有礼");
	    				haveButton=true;
	    				break loop;
	    			}
		    	}
			}
		    WxMenu menu = new WxMenu();
		    //不存在，新建一个
		    if(!haveButton){
		    	WxMpSelfMenuButton subButton = new WxMpSelfMenuButton();
		    	subButton.setName("签到有礼");
		    	subButton.setType("click");
		    	subButton.setKey("签到有礼");
		    	WxMpSelfMenuButton lastbutton = buttons.get(buttons.size()-1);
		    	lastbutton.getSubButtons().getSubButtons().add(subButton);
		    	//放在最后一个
		    	buttons.get(buttons.size()-1).setSubButtons(lastbutton.getSubButtons());
		    }
		    //放入
		    List<WxMenuButton> lll=new ArrayList<>();
		    org.springframework.beans.BeanUtils.copyProperties(buttons, lll);
		    menu.setButtons(lll);
		    StateResultList<List<String>> srl=new  StateResultList<>();
		    srl.setCode(200);
		    srl.setMsg("成功");
		    List<String> list=new ArrayList<>();
		    list.add(wxMpService.getMenuService().menuCreate(menu));
		    srl.setData(list);
		    return srl;*/
	  }


	  /**
	   * <pre>
	   * 删除个性化菜单接口
	   * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
	   * </pre>
	   *
	   * @param menuId 个性化菜单的menuid
	   */
	  @ApiOperation(value = "删除个性化菜单接口", notes = "删除个性化菜单接口")
	  @RequestMapping(value="/menu/delete", method = {RequestMethod.GET,RequestMethod.POST})
	  public void menuDelete(
			  @RequestParam(value="appid") String appid,
			  @RequestParam(value="menuId",required=false) String menuId
			  ) throws WxErrorException {
		  WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
		  if(menuId==null){
			  wxMpService.getMenuService().menuDelete();			  
		  }else{
			  wxMpService.getMenuService().menuDelete(menuId);			  
		  }
	  }

	  /**
	   * <pre>
	   * 自定义菜单查询接口
	   * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN
	   * </pre>
	   */
	  @ApiOperation(value = "自定义菜单查询接口", notes = "自定义菜单查询接口")
	  @RequestMapping(value="/menu/get", method = {RequestMethod.GET,RequestMethod.POST})
	  public WxMpMenu menuGet(
			  @RequestParam(value="appid") String appid
			  ) throws WxErrorException {
		  WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
	    return wxMpService.getMenuService().menuGet();
	  }

	  /**
	   * <pre>
	   * 测试个性化菜单匹配结果
	   * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
	   * </pre>
	   *
	   * @param userid 可以是粉丝的OpenID，也可以是粉丝的微信号。
	   */
	  @ApiOperation(value = "测试个性化菜单匹配结果", notes = "测试个性化菜单匹配结果")
	  @RequestMapping(value="/menu/menuTryMatch", method = {RequestMethod.GET,RequestMethod.POST})
	  public WxMenu menuTryMatch(
			  @RequestParam(value="appid") String appid,
			  @RequestParam(value="userid") String userid
			  ) throws WxErrorException {
		  WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
	    return wxMpService.getMenuService().menuTryMatch(userid);
	  }

	  /**
	   * <pre>
	   * 获取自定义菜单配置接口
	   * 本接口将会提供公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
	   * 请注意：
	   * 1、第三方平台开发者可以通过本接口，在旗下公众号将业务授权给你后，立即通过本接口检测公众号的自定义菜单配置，并通过接口再次给公众号设置好自动回复规则，以提升公众号运营者的业务体验。
	   * 2、本接口与自定义菜单查询接口的不同之处在于，本接口无论公众号的接口是如何设置的，都能查询到接口，而自定义菜单查询接口则仅能查询到使用API设置的菜单配置。
	   * 3、认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
	   * 4、从第三方平台的公众号登录授权机制上来说，该接口从属于消息与菜单权限集。
	   * 5、本接口中返回的图片/语音/视频为临时素材（临时素材每次获取都不同，3天内有效，通过素材管理-获取临时素材接口来获取这些素材），本接口返回的图文消息为永久素材素材（通过素材管理-获取永久素材接口来获取这些素材）。
	   *  接口调用请求说明:
	   * http请求方式: GET（请使用https协议）
	   * https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN
	   * </pre>
	   */
	  @ApiOperation(value = "获取自定义菜单配置接口", notes = "获取自定义菜单配置接口")
	  @RequestMapping(value="/menu/getSelfMenuInfo", method = {RequestMethod.GET,RequestMethod.POST})
	  public WxMpGetSelfMenuInfoResult getSelfMenuInfo( 
			  @RequestParam(value="appid") String appid
			  ) throws WxErrorException {
		  WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
	    return wxMpService.getMenuService().getSelfMenuInfo();
	  }
	  
	  /**
		 * 微信js授权访问
		 *
		 * @return 重定向跳转
	 * @throws WxErrorException 
		 */
		@ApiOperation(value = "微信js授权访问", notes = "微信js授权访问")
		@ApiImplicitParams({
			@ApiImplicitParam(name="templateMessageId",value="客服消息ID",dataType="long", paramType = "query",required=true),
		})
		@RequestMapping(value="/authorize",method={RequestMethod.GET,RequestMethod.POST})
		public  RedirectView authorize(
				@RequestParam("templateMessageId") Long templateMessageId,
				HttpServletRequest request) throws WxErrorException{
			StringBuffer url = request.getRequestURL();
			String baseurl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
			String state = request.getHeader("Referer");
			TemplateMessage templateMessage = templateMessageService.load(templateMessageId);
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
			//初始化公众号
			weiXinMpBusiness.init(subscription.getAppid(), subscription.getAppsecret(), subscription.getToken(), "aes");
			return new RedirectView(weiXinMpBusiness.authorize(baseurl+"/weixin/openid",2,state));

		}
		/**
		 * 此链接为  微信js授权访问 跳转后 微信链接跳回的链接
		 * 微信登录获取openid
		 *
		 * @return 重定向跳转
		 */
		@ApiOperation(value = "微信登录获取openid", notes = "微信登录获取openid")
		@ApiImplicitParams({
				@ApiImplicitParam(name="code",value="授权码",dataType="string", paramType = "query"),
				@ApiImplicitParam(name="state",value="请求网页原地址",dataType="string", paramType = "query"),
		})
		@RequestMapping(value="/openid",method={RequestMethod.GET,RequestMethod.POST})
		public  RedirectView openid(
				@RequestParam("code")String code,
				@RequestParam("state")String state,
				HttpSession session) throws IOException, WxErrorException {
			//如果存在session则返回
			if(session.getAttribute("openid")!=null){
				return new RedirectView(state);
			}
			WxMpUser wxMpUser = weiXinMpBusiness.redirectUrl(code);
			session.setAttribute("openid",wxMpUser.getOpenId());
			return new RedirectView(state);
		}
		/**
		 * 微信jssdk 接口
		 *
		 * @return
		 */
		@ApiOperation(value = "微信jssdk 接口", notes = "微信jssdk 接口")
		@ApiImplicitParams({
				@ApiImplicitParam(name="url",value="请求网页原地址",dataType="string", paramType = "query"),
		})
		@RequestMapping(value="/js/connection",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody WxJsapiSignature connectionWeiXin(@RequestParam("url")String url, HttpSession session)
				throws WxErrorException {
			WxJsapiSignature wxJsapiSignature = weiXinMpBusiness.initJsApi(url);
			return wxJsapiSignature;

		}
}
