package com.nieyue.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Subscription;
import com.nieyue.business.WeiXinMpBusiness;
import com.nieyue.service.SubscriptionService;
import com.nieyue.util.DateUtil;
import com.nieyue.util.FileUploadUtil;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.ThumbnailatorUtils;
import com.nieyue.verification.VerificationCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;



/**
 * 工具控制类
 * @author yy
 *
 */
@Api(tags={"tool"},value="工具",description="工具接口管理")
@RestController
@RequestMapping("/tool")
public class ToolController {
	@Resource
	VerificationCode verificationCode;
	@Value("${uploaderPath.rootPath}")
	String rootPath;
	@Value("${uploaderPath.locationPath}")
	String locationPath;
	@Autowired
	private WeiXinMpBusiness weiXinMpBusiness;
	@Autowired
	private SubscriptionService subscriptionService;
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
	@RequestMapping(value = "/getSession", method = {RequestMethod.GET,RequestMethod.POST})
	public String getSession(
			HttpSession	 session
			){
		System.err.println(session.getAttribute("acount"));
		System.err.println(session.getAttribute("role"));
		System.err.println(session.getAttribute("finance"));
		return session.getId();
		
	}
	/**
	 * 微信门户
	 * @return
	 */
	@RequestMapping(value = "/weixin/portal/{appid}", method = {RequestMethod.GET,RequestMethod.POST})
	public String weixinPortal(
			@RequestParam(name = "signature",required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr,
			@PathVariable(value="appid") String appid,
			HttpSession	 session
			){
		
	    if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
	      throw new IllegalArgumentException("请求参数非法，请核实!");
	    }
	    Wrapper<Subscription> wrapper=new EntityWrapper<Subscription>();
 		Map<String,Object> map=new HashMap<String,Object>();
 		map.put("appid", appid);
 		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
 		List<Subscription> sl = subscriptionService.list(1, 1, null, null, wrapper);
	   if(sl.size()>0){
		   Subscription s = sl.get(0);
		   WxMpService wxMpService;
		try {
			wxMpService = weiXinMpBusiness.init(s.getAppid(), s.getAppsecret(), s.getToken(), null);
			if (wxMpService.checkSignature(timestamp, nonce, signature)) {
				return echostr;
			}		   
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			return "非法请求";
		}
	   }
	    return "非法请求";
	  }
	
}
