package com.nieyue.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nieyue.business.SubscriptionBusiness;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.util.DateUtil;
import com.nieyue.util.FileUploadUtil;
import com.nieyue.util.ThumbnailatorUtils;
import com.nieyue.verification.VerificationCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;



/**
 * 工具控制类
 * @author yy
 *
 */
@Api(tags={"tool"},value="工具",description="工具接口管理")
@RestController
@RequestMapping("/tool")
public class ToolController extends BaseController<Object,Long>{
	@Resource
	VerificationCode verificationCode;
	@Value("${uploaderPath.rootPath}")
	String rootPath;
	@Value("${uploaderPath.locationPath}")
	String locationPath;
	@Autowired
	SubscriptionBusiness subscriptionBusiness;
	@Autowired
	private WxMpMessageRouter router;
	
	/**
	 * 验证码
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "验证码", notes = "验证码")
	@RequestMapping(value = "/getVerificationCode", method = {RequestMethod.GET,RequestMethod.POST})
	public void getVerificationCode(
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
			ByteArrayOutputStream vc = verificationCode.execute(session);
			response.getOutputStream().write(vc.toByteArray());
		return ;
	}
	/**
	 * 文件增加、修改
	 * @param editorUpload 上传参数
	 * @param width 固定图片宽度
	 * @param height 固定图片高度
	 * @return
	 * @throws IOException 
	 */
	@ApiOperation(value = "上传文件", notes = "上传文件")
	@RequestMapping(value = "/file/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String addFile(
			@RequestParam("editorUpload") MultipartFile file,
			HttpServletRequest request,HttpSession session ) throws IOException  {
		String fileUrl = null;
		String filedir=DateUtil.getImgDir();
		try{
			fileUrl = FileUploadUtil.FormDataMerImgFileUpload(file, session,rootPath,locationPath,filedir);
		}catch (IOException e) {
			throw new IOException();
		}
		StringBuffer url=request.getRequestURL();
		String redirect_url = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		return redirect_url+fileUrl;
	}
	/**
	 * 图片增加、修改
	 * @param editorUpload 上传图片
	 * @param width （可选）固定图片宽度
	 * @param height （可选）固定图片高度
	 * @param compression 默认0，原图不压缩，1压缩最优
	 * @return
	 * @throws IOException 
	 */
	@ApiOperation(value = "上传图片", notes = "上传图片")
	@RequestMapping(value = "/img/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String addAdvertiseImg(
			@RequestParam("editorUpload") MultipartFile file,
			@RequestParam(value="width",required=false) Integer width,
			@RequestParam(value="height",required=false) Integer height,
			@RequestParam(value="compression",required=false,defaultValue="0")Integer compression,
			HttpServletRequest request,HttpSession session ) throws IOException  {
		String imgUrl = null;
		String imgdir=DateUtil.getImgDir();
		Map<String ,Integer> kgmap=new HashMap<String,Integer>();
		kgmap.put("width", width);
		kgmap.put("height", height);
		kgmap.put("compression", compression);
		try{
			imgUrl = ThumbnailatorUtils.fileUpload(file, session,rootPath,locationPath,imgdir,kgmap);
		}catch (IOException e) {
			throw new IOException();
		}
		StringBuffer url=request.getRequestURL();
		String redirect_url = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		//String redirect_url="http://118.123.15.27";
		return redirect_url+imgUrl;
	}
	/**
	 * 获取Session
	 * @return
	 */
	@ApiOperation(value = "获取sessionId", notes = "获取sessionId")
	@RequestMapping(value = "/getSession", method = {RequestMethod.GET,RequestMethod.POST})
	public String getSession(
			HttpSession	 session
			){
		System.err.println(session.getAttribute("acount"));
		System.err.println(session.getAttribute("role"));
		System.err.println(session.getAttribute("finance"));
		this.logger.debug("\ngetId：\n{} ",session.getId());
		this.logger.info("\ngetId：\n{} ",session.getId());
		this.logger.warn("\ngetId：\n{} ",session.getId());
		//this.logger.error("\ngetId：\n{} ",session.getId());
		return session.getId();
		
	}
	/**
	 * 微信门户
	 * @return
	 */
	@ApiOperation(value = "微信服务门户", notes = "微信服务门户")
	@GetMapping(value = "/weixin/portal/{appid}",produces = "text/plain;charset=utf-8")
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
	 @PostMapping(value = "/weixin/portal/{appid}",produces = "application/xml; charset=UTF-8")
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
	
}
