package com.nieyue.controller;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class ToolController {
	@Resource
	VerificationCode verificationCode;
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
	
}
