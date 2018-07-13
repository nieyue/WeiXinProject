package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.business.AccountBusiness;
import com.nieyue.exception.AccountAlreadyAuthException;
import com.nieyue.exception.AccountAuthAuditException;
import com.nieyue.exception.AccountIsExistException;
import com.nieyue.exception.AccountIsNotExistException;
import com.nieyue.exception.AccountIsNotLoginException;
import com.nieyue.exception.AccountPhoneIsExistException;
import com.nieyue.exception.MySessionException;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.VerifyCodeErrorException;
import com.nieyue.service.AccountService;
import com.nieyue.util.MyDESutil;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 账户控制类
 * @author yy
 *
 */
@Api(tags={"account"},value="账户",description="账户管理")
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController<Account, Long>{
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountBusiness accountBusiness;

	/**
	 * 账户分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return 
	 */
	@ApiOperation(value = "账户列表", notes = "账户分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="auth",value="认证，0没认证，1审核中，2已认证",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="phone",value="手机号，模糊查询",dataType="string", paramType = "query"),
	  @ApiImplicitParam(name="email",value="email，模糊查询",dataType="string", paramType = "query"),
	  @ApiImplicitParam(name="realname",value="真实姓名，模糊查询",dataType="string", paramType = "query"),
	  @ApiImplicitParam(name="roleId",value="角色ID",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="status",value="状态，0正常，1锁定，2，异常",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="loginDate",value="最后登陆时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="accountId"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> browsePagingAccount(
			@RequestParam(value="accountId",required=false)Long  accountId,
			@RequestParam(value="auth",required=false)Integer auth,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="realname",required=false)String realname,
			@RequestParam(value="roleId",required=false)Long roleId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="loginDate",required=false)Date loginDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="accountId") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
		Wrapper<Account> wrapper=new EntityWrapper<>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	map.put("auth", auth);
	 	map.put("role_id", roleId);
	 	map.put("status", status);
	 	map.put("create_date", createDate);
	 	map.put("login_date", loginDate);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
	 	Map<String,Object> maplike=new HashMap<String,Object>();
	 	maplike.put("phone", phone);
	 	maplike.put("email", email);
	 	maplike.put("realname", realname);
	 	Set<Entry<String, Object>> newmaplie = MyDom4jUtil.getNoNullMap(maplike).entrySet();
	 	for (Entry<String, Object> entry : newmaplie) {
	 		wrapper.like(entry.getKey(),(String)entry.getValue());			
		}
	 	
	 	List<Account> rl = accountService.list(pageNum, pageSize, orderName, orderWay,wrapper);
	 	if(rl!=null&&rl.size()>0){
			return ResultUtil.getSlefSRSuccessList(rl);
		}else{
			throw new NotAnymoreException();//没有更多
		}
	}

	/**
	 * 账户修改
	 * @return
	 */
	@ApiOperation(value = "账户修改", notes = "账户修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> updateAccount(
		@ModelAttribute Account account,
			HttpSession session)  {
		//账户已经存在
		if(accountService.loginAccount(account.getPhone(), null,account.getAccountId())!=null
				||accountService.loginAccount(account.getEmail(), null,account.getAccountId())!=null
				){
			throw new AccountIsExistException();//账户已经存在
		}
		StateResultList<List<Account>> u = super.update(account);
		return u;
	}
	/**
	 * 账户修改密码
	 * @param adminName 手机号/电子邮箱
	 * @param password  新密码
	 * @param validCode 短信验证码
	 * @return
	 * @throws VerifyCodeErrorException 
	 */
	@ApiOperation(value = "账户修改密码", notes = "账户修改密码")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="adminName",value="手机号/电子邮箱",dataType="string", paramType = "query",required=true),
		  @ApiImplicitParam(name="password",value="新密码",dataType="string", paramType = "query",required=true),
		  @ApiImplicitParam(name="validCode",value="短信验证码",dataType="string", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/updatePassword", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> updateAccountPassword(
			@RequestParam("adminName")String adminName,
			@RequestParam("password")String password,
			@RequestParam(value="validCode",required=false) String validCode,
			HttpSession session) throws VerifyCodeErrorException  {
		//判断是否存在
		Account ac = accountService.loginAccount(adminName, null, null);
		if(ac==null || ac.getAccountId()==null){
			throw new AccountIsNotExistException();//账户不存在
		}
		//手机验证码
		String vc=(String) session.getAttribute("validCode");
		if(!vc.equals(validCode)){
			throw new VerifyCodeErrorException();//验证码错误
		}
		ac.setPassword(MyDESutil.getMD5(password));
		StateResultList<List<Account>> u = super.update(ac);
		return u;
	}
	/**
	 * 账户修改用户信息
	 * @return
	 */
	@ApiOperation(value = "账户修改用户信息", notes = "账户修改用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query",required=true),
		@ApiImplicitParam(name="icon",value="头像",dataType="string", paramType = "query"),
		@ApiImplicitParam(name="nickname",value="昵称",dataType="string", paramType = "query"),
		@ApiImplicitParam(name="sex",value="性别,默认为0未知，为1男性，为2女性",dataType="int", paramType = "query"),
		@ApiImplicitParam(name="country",value="国家",dataType="string", paramType = "query"),
	})
	@RequestMapping(value = "/updateInfo", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> updateInfoAccount(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="icon",required=false)String icon,
			@RequestParam(value="nickname",required=false)String nickname,
			@RequestParam(value="sex",required=false)Integer sex,
			@RequestParam(value="country",required=false)String country,
			HttpSession session)  {
		Account newa = accountService.load(accountId);
		if(!((Account)session.getAttribute("account")).getAccountId().equals(accountId)){
			throw new MySessionException();//没有权限
		}
		if(icon!=null){
			newa.setIcon(icon);			
		}
		if(nickname!=null){
			newa.setNickname(nickname);			
		}
		if(sex!=null){
		newa.setSex(sex);
		}
		if(country!=null){
		newa.setCountry(country);
		}
		 StateResultList<List<Account>> u = super.update(newa);
		 return u;
		
	}
	/**
	 * 账户实名认证
	 * @return
	 */
	@ApiOperation(value = "账户实名认证", notes = "账户实名认证")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query",required=true),
	  @ApiImplicitParam(name="realname",value="真实姓名",dataType="string", paramType = "query",required=true),
	  @ApiImplicitParam(name="identityCards",value="身份证",dataType="string", paramType = "query",required=true),
	  @ApiImplicitParam(name="identityCardsHoldImg",value="手持身份证上半身照",dataType="string", paramType = "query",required=true),
	  @ApiImplicitParam(name="identityCardsFrontImg",value="身份证正面",dataType="string", paramType = "query",required=true),
	  @ApiImplicitParam(name="identityCardsBackImg",value="身份证反面",dataType="string", paramType = "query",required=true)
	 	  })
	@RequestMapping(value = "/auth", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> authAccount(
			@RequestParam("accountId") Long accountId,
			@RequestParam("realname") String realname,
			@RequestParam("identityCards") String identityCards,
			@RequestParam("identityCardsHoldImg") String identityCardsHoldImg,
			@RequestParam("identityCardsFrontImg") String identityCardsFrontImg,
			@RequestParam("identityCardsBackImg") String identityCardsBackImg,
			HttpSession session)  {
		List<Account> list = new ArrayList<Account>();
		Account account = accountService.load(accountId);
		//必须是没认证的
		if(account!=null &&!account.equals("")&& account.getAuth().equals(0)){
			account.setAuth(1);//审核中
			account.setRealname(realname);
			account.setIdentityCards(identityCards);
			account.setIdentityCardsHoldImg(identityCardsHoldImg);
			account.setIdentityCardsFrontImg(identityCardsFrontImg);
			account.setIdentityCardsBackImg(identityCardsBackImg);
			boolean b = accountService.update(account);
			if(b){
				list.add(account);
				return ResultUtil.getSlefSRSuccessList(list);
			}
			return ResultUtil.getSlefSRFailList(list);
			}else{
				if(account.getAuth().equals(1)){
					throw new AccountAuthAuditException();//审核中
					
				}else if(account.getAuth().equals(2)){
					throw new AccountAlreadyAuthException();//已经认证
				}
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 账户增加
	 * @return 
	 */
	
	@ApiOperation(value = "账户增加", notes = "账户增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public  @ResponseBody StateResultList<List<Account>> addAccount(@ModelAttribute Account account, HttpSession session) {
		//账户已经存在
		if(accountService.loginAccount(account.getPhone(), null,null)!=null
				||accountService.loginAccount(account.getEmail(), null,null)!=null
				){
			throw new AccountIsExistException();//账户已经存在
		}
		account.setPassword(MyDESutil.getMD5(account.getPassword()));
		account.setCreateDate(new Date());
		account.setLoginDate(new Date());
		StateResultList<List<Account>> a = super.add(account);
		return a;
	}
	/**
	 * 账户删除
	 * @return
	 */
	@ApiOperation(value = "账户删除", notes = "账户删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query",required=true)
		 	  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> delAccount(
			@RequestParam("accountId") Long accountId,
			HttpSession session)  {
		StateResultList<List<Account>> d = super.delete(accountId);
		return d;
	}
	/**
	 * 账户浏览数量
	 * @return
	 */
	@ApiOperation(value = "账户数量", notes = "根据参数获取账户数量")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType = "query"),
		  @ApiImplicitParam(name="auth",value="认证，0没认证，1审核中，2已认证",dataType="int", paramType = "query"),
		  @ApiImplicitParam(name="phone",value="手机号，模糊查询",dataType="string", paramType = "query"),
		  @ApiImplicitParam(name="email",value="email，模糊查询",dataType="string", paramType = "query"),
		  @ApiImplicitParam(name="realname",value="真实姓名，模糊查询",dataType="string", paramType = "query"),
		  @ApiImplicitParam(name="roleId",value="角色ID",dataType="int", paramType = "query"),
		  @ApiImplicitParam(name="status",value="状态，0正常，1锁定，2，异常",dataType="int", paramType = "query"),
		  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
		  @ApiImplicitParam(name="loginDate",value="最后登陆时间",dataType="date-time", paramType = "query")
		  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> countAll(
			@RequestParam(value="accountId",required=false)Long  accountId,
			@RequestParam(value="auth",required=false)Integer auth,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="realname",required=false)String realname,
			@RequestParam(value="roleId",required=false)Integer roleId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="loginDate",required=false)Date loginDate,
			HttpSession session)  {
		Wrapper<Account> wrapper=new EntityWrapper<>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	map.put("auth", auth);
	 	map.put("role_id", roleId);
	 	map.put("status", status);
	 	map.put("create_date", createDate);
	 	map.put("login_date", loginDate);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
	 	Map<String,Object> maplike=new HashMap<String,Object>();
	 	maplike.put("phone", phone);
	 	maplike.put("email", email);
	 	maplike.put("realname", realname);
	 	Set<Entry<String, Object>> newmaplie = MyDom4jUtil.getNoNullMap(maplike).entrySet();
	 	for (Entry<String, Object> entry : newmaplie) {
	 		wrapper.like(entry.getKey(),(String)entry.getValue());			
		}
	 	StateResultList<List<Integer>> c = super.count(wrapper);
	 	return c;
	}
	/**
	 * 账户单个加载
	 * @return
	 */
	@ApiOperation(value = "账户单个加载", notes = "账户单个加载")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="accountId",value="账户ID",dataType="long", paramType="query",required=true)
	 	  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> loadAccount(@RequestParam("accountId") Long accountId,HttpSession session)  {
		StateResultList<List<Account>> a = super.load(accountId);
		return a;
	
	}
	/**
	 * 管理员登录
	 * @return
	 * @throws MySessionException 
	 */
	@ApiOperation(value = "管理员登录", notes = "管理员登录")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="adminName",value="手机号/电子邮箱",dataType="string", paramType = "query",required=true),
		  @ApiImplicitParam(name="password",value="新密码",dataType="string", paramType = "query",required=true),
		  @ApiImplicitParam(name="random",value="验证码",dataType="string", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Map<String,Object>>> loginAccount(
			@RequestParam(value="adminName") String adminName,
			@RequestParam(value="password") String password,
			@RequestParam(value="random") String random,
			HttpSession session) throws MySessionException  {
		List<Map<String, Object>> list = accountBusiness.login(adminName, password,random, session);
		 return ResultUtil.getSlefSRSuccessList(list);
		
	}
	
	/**
	 * web用户登录
	 * @return
	 */
	@ApiOperation(value = "web用户登录", notes = "web用户登录")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="adminName",value="手机号/电子邮箱",dataType="string", paramType = "query",required=true),
		  @ApiImplicitParam(name="password",value="密码",dataType="string", paramType = "query",required=true),
		  @ApiImplicitParam(name="random",value="验证码",dataType="string", paramType = "query")
		  })
	@RequestMapping(value = "/weblogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Map<String,Object>>> webLoginAccount(
			@RequestParam("adminName") String adminName,
			@RequestParam("password") String password,
			@RequestParam(value="random",required=false) String random,
			HttpSession session)  {
		List<Map<String, Object>> list = accountBusiness.webLogin(adminName, password,random, session);
		return ResultUtil.getSlefSRSuccessList(list);
		
	}

	/**
	 * 手机号账户是否存在
	 * @return
	 */
	@ApiOperation(value = "手机号账户是否存在", notes = "手机号账户是否存在")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="phone",value="手机号",dataType="string", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/phoneIsExist", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<String>> accountIsExist(
			@RequestParam("phone") String phone,
			HttpSession session)  {
		List<String> list = new ArrayList<>();
		//判断是否存在
		Account ac = accountService.loginAccount(phone, null, null);
		if(ac!=null&&ac.getAccountId()!=null){
		   throw new AccountPhoneIsExistException();//手机号已存在
		 }
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 是否登录
	 * @return
	 */
	@ApiOperation(value = "是否登录", notes = "是否登录")
	@RequestMapping(value = "/islogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> isLoginAccount(
			HttpSession session)  {
		Account account = (Account) session.getAttribute("account");
		List<Account> list = new ArrayList<Account>();
		if(account!=null && !account.equals("")){
			list.add(account);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		throw new AccountIsNotLoginException();//没有登录
	}
	/**
	 * 登出
	 * @return
	 */
	@ApiOperation(value = "登出", notes = "登出")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/loginout", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Account>> loginoutAccount(
			@RequestParam("accountId") Long accountId,
			HttpSession session)  {
		session.invalidate();
		//shiro登录退出
		Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
		return ResultUtil.getSlefSRSuccessList(null);
	}
	
}
