package com.nieyue.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.bean.Role;
import com.nieyue.bean.RolePermission;
import com.nieyue.exception.AccountLockException;
import com.nieyue.exception.AccountLoginException;
import com.nieyue.exception.MySessionException;
import com.nieyue.exception.VerifyCodeErrorException;
import com.nieyue.service.AccountService;
import com.nieyue.service.RolePermissionService;
import com.nieyue.service.RoleService;
import com.nieyue.util.MyDom4jUtil;
/**
 * 账户业务逻辑
 * @author 聂跃
 * @date 2017年8月19日
 */
@Configuration
public class AccountBusiness {
	@Resource
	AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
	@Value("${myPugin.projectName}")
	String projectName;
    /**
     * 存储账户会话信息
     */
    public List<Map<String,Object>> accountSession(
    		Account account
    		){
    	
    	Session session=SecurityUtils.getSubject().getSession();
    	List<Map<String,Object>> list = new ArrayList<>();
	        //将用户信息放入session中
			session.setAttribute("account", account);
			//角色
	        Long roleId = account.getRoleId();
			Role r = roleService.load(roleId);
			session.setAttribute("role", r);
			//权限放入session
			Wrapper<RolePermission> wrapper=new EntityWrapper<>();
		 	Map<String,Object> mw=new HashMap<>();
		 	mw.put("role_id",  account.getRoleId());
		 	wrapper.allEq(MyDom4jUtil.getNoNullMap(mw));
			List<RolePermission> rolePermissionList = rolePermissionService.list(1, Integer.MAX_VALUE, null, null, wrapper);
	        session.setAttribute("rolePermissionList", rolePermissionList);
	        //map放入session
	        Map<String,Object> map=new HashMap<>();
	        //账户
			map.put("account", account);
			//自身角色
			map.put("role", r);
			//自身权限
			map.put("rolePermissionList", rolePermissionList);
			session.setAttribute("loginMap", map);//可以放入session
			list.add(map);
			return list;
    	
    }
	/**
	 * 登录通用
	 */
	public List<Map<String,Object>> loginCommon(
			String adminName,
			String password
			){
		List<Map<String,Object>> list = new ArrayList<>();
		 Account account = accountService.loginAccount(adminName, password, null);
	    	//自动登陆
//			if(account==null|| account.equals("")){
//				account=accountService.loginAccount(adminName, password, null);
//			}
	        if (ObjectUtils.isEmpty(account)) {
	            throw new AccountLoginException();//账户或密码错误
	        }
	        if(account.getStatus().equals(1)){
				throw new AccountLockException();//账户锁定
			}
	        //更新登陆时间
	        account.setLoginDate(new Date());
			boolean b = accountService.update(account);
			if(b){
				list=accountSession(account);
			}
			return list;
	}
	/**
	 * web登录
	 * @return false 不包含，  true 包含
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> webLogin(
			String adminName,
			String password,
			String random,
			HttpSession session
			){
		//1代验证码
//		String ran= (String) session.getAttribute("random");
//		if(ran==null||!ran.equals(random)){
//			throw new VerifyCodeErrorException();
//		}
		List<Map<String,Object>> list = new ArrayList<>();
		 Subject currentUser = SecurityUtils.getSubject();
		 UsernamePasswordToken token = new UsernamePasswordToken(adminName, password);
		 //UsernamePasswordToken token = new UsernamePasswordToken(adminName, MyDESutil.getMD5(password));
		 try {
	            currentUser.login(token);
	        } catch (AuthenticationException e) {
	            throw new AccountLoginException();//
	        }
		 Map<String,Object> loginMap=(Map<String, Object>) session.getAttribute("loginMap");
		 list.add(loginMap);
		 session.removeAttribute("loginMap");//去掉
		return list;
	}
	/**
	 * 管理员登录
	 * @return false 不包含，  true 包含
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> login(
			String adminName,
			String password,
			String random,
			HttpSession session
			){
		//1代验证码
		String ran= (String) session.getAttribute("random");
		if(ran==null||!ran.equals(random)){
			throw new VerifyCodeErrorException();//验证码
		}
		List<Map<String,Object>> list = new ArrayList<>();
		Subject currentUser = SecurityUtils.getSubject();
		 UsernamePasswordToken token = new UsernamePasswordToken(adminName, password);
		 //UsernamePasswordToken token = new UsernamePasswordToken(adminName, MyDESutil.getMD5(password));
		 try {
	            currentUser.login(token);
	        } catch (AuthenticationException e) {
	            throw new AccountLoginException();//
	        }
		 Role r = (Role) session.getAttribute("role");
		 if(!ObjectUtils.isEmpty(r)){
			 if(r.getName().indexOf("管理员")<0){
				 session.invalidate();//不能登录
				 throw new MySessionException();
			 }
		 }
		 Map<String,Object> loginMap=(Map<String, Object>) session.getAttribute("loginMap");
		 list.add(loginMap);
		 session.removeAttribute("loginMap");//去掉
		 return list;
		}

}
