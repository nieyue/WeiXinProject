package com.nieyue.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.nieyue.util.DateUtil;
import com.nieyue.util.FileUploadUtil;
import com.nieyue.util.ThumbnailatorUtils;
import com.nieyue.verification.VerificationCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



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
	@Value("${myPugin.uploaderPath.rootPath}")
	String rootPath;
	@Value("${myPugin.uploaderPath.locationPath}")
	String locationPath;
	
	
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
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	/**
	 * test
	 * @return
	 * @throws InterruptedException 
	 */
	@ApiOperation(value = "test", notes = "test")
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	public String test(
			HttpSession	 session
			) throws InterruptedException{
		BoundValueOperations<String, String> srt = stringRedisTemplate.boundValueOps("test");
		Boolean r = srt.setIfAbsent("aaa11");
        if(r){
        	System.out.println("success:"+srt.get());
        }else{
        	//srt.set("123456",1,TimeUnit.SECONDS);
        	System.out.println("fail"+srt.get());
        	//Thread.sleep(3000);
        }
		return srt.get();
		
	}
}
