package com.nieyue.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.business.SubscriptionBusiness;
import com.nieyue.exception.CommonRollbackException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
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
	private WxMpMessageRouter router;
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
	      WxMpXmlOutMessage outMessage = this.route(inMessage);
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
	      WxMpXmlOutMessage outMessage = this.route(inMessage);
	      if (outMessage == null) {
	        return "";
	      }

	      out = outMessage
	          .toEncryptedXml(wxMpService.getWxMpConfigStorage());
	    }

	    this.logger.debug("\n组装回复信息：{}", out);

	    return out;
	  }

	  private WxMpXmlOutMessage route(WxMpXmlMessage message) {
	    try {
	      return this.router.route(message);
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
	 /* @ApiOperation(value = "微信菜单创建", notes = "微信菜单创建")
	  @RequestMapping(value="/menu/create", method = {RequestMethod.GET,RequestMethod.POST})
	  public String menuCreate(
			  @RequestBody WxMenu menu,
			  @RequestParam(value="appid") String appid
			  ) throws WxErrorException {
		  WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
		  return wxMpService.getMenuService().menuCreate(menu);
	  }*/
	  @ApiOperation(value = "微信菜单创建", notes = "微信菜单创建")
	  @RequestMapping(value="/menu/create", method = {RequestMethod.GET,RequestMethod.POST})
	  public String menuCreateSample(
			  @RequestParam(value="appid") String appid
			  ) throws WxErrorException {
	    WxMenu menu = new WxMenu();
	    WxMenuButton button1 = new WxMenuButton();
	    button1.setType(MenuButtonType.CLICK);
	    button1.setName("今日歌曲");
	    button1.setKey("V1001_TODAY_MUSIC");

//	        WxMenuButton button2 = new WxMenuButton();
//	        button2.setType(WxConsts.BUTTON_MINIPROGRAM);
//	        button2.setName("小程序");
//	        button2.setAppId("wx286b93c14bbf93aa");
//	        button2.setPagePath("pages/lunar/index.html");
//	        button2.setUrl("http://mp.weixin.qq.com");

	    WxMenuButton button3 = new WxMenuButton();
	    button3.setName("菜单");

	    menu.getButtons().add(button1);
//	        menu.getButtons().add(button2);
	    menu.getButtons().add(button3);

	    WxMenuButton button31 = new WxMenuButton();
	    button31.setType(MenuButtonType.VIEW);
	    button31.setName("搜索");
	    button31.setUrl("http://www.soso.com/");

	    WxMenuButton button32 = new WxMenuButton();
	    button32.setType(MenuButtonType.VIEW);
	    button32.setName("视频");
	    button32.setUrl("http://v.qq.com/");

	    WxMenuButton button33 = new WxMenuButton();
	    button33.setType(MenuButtonType.CLICK);
	    button33.setName("赞一下我们");
	    button33.setKey("V1001_GOOD");

	    button3.getSubButtons().add(button31);
	    button3.getSubButtons().add(button32);
	    button3.getSubButtons().add(button33);
	    WxMpService wxMpService=subscriptionBusiness.getWxMpService(appid);
	    return wxMpService.getMenuService().menuCreate(menu);
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
}
