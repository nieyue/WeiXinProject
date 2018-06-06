package com.nieyue.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.bean.Role;
import com.nieyue.dao.AccountDao;
import com.nieyue.dao.RoleDao;
import com.nieyue.service.AccountService;
import com.nieyue.util.MyDESutil;
import com.nieyue.util.MyDom4jUtil;
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account,Long> implements AccountService{
	@Autowired
	AccountDao accountDao;
	@Autowired
	RoleDao roleDao;
	/**
	 * 登录
	 */
	@Override
	public Account loginAccount(String adminName, String password, Long accountId) {
		Account account = null;
		Wrapper<Account> wrapper=new EntityWrapper<>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("phone", adminName);
	 	map.put("password", MyDESutil.getMD5(password));
	 	map.put("accountId", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
	 	Map<String,Object> map2=new HashMap<String,Object>();
	 	map2.put("email", adminName);
	 	map2.put("password", MyDESutil.getMD5(password));
	 	map2.put("accountId", accountId);
	 	wrapper.or().allEq(MyDom4jUtil.getNoNullMap(map2));
	 	List<Account> al = accountDao.selectList(wrapper);
	 	if(al.size()>0){
	 		account=al.get(0);
 			Role role = roleDao.selectById(account.getRoleId());
 			account.setRole(role);
 			account.setLoginDate(new Date());
 			accountDao.updateById(account);
	 	}
	 	return account;
	}
	
	@Override
	public List<Account> list(int pageNum, int pageSize, String orderName, String orderWay, Wrapper<Account> wrapper) {
				//分页
				if(pageNum<1){//最小是逻辑1，实际0，
					pageNum=1;
				}
				if(pageSize<1){
					pageSize=0;
				}
				RowBounds rowBounds=new RowBounds(pageNum-1,pageSize);
				//排序
				if(wrapper==null){
					wrapper=new EntityWrapper<>();
				}
				if(!StringUtils.isEmpty(orderName)&&!StringUtils.isEmpty(orderWay)){
					if(orderWay.equals("asc")){
						wrapper=wrapper.orderBy(orderName, true);
						
					}else if(orderWay.equals("desc")){
						wrapper=wrapper.orderBy(orderName, false);
					}
				}
				List<Account> rl = accountDao.selectPage(rowBounds, wrapper);
				if(rl!=null&&rl.size()>0){
			 		rl.forEach((a)->{
			 			Role role = roleDao.selectById(a.getRoleId());
			 			a.setRole(role);
			 		});
				}
				return rl;
	}
	@Override
	public Account load(Long id) {
		Account a = accountDao.selectById(id);
		Role role = roleDao.selectById(a.getRoleId());
		a.setRole(role);
	 	return a;
	}
	
}
