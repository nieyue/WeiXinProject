package com.nieyue.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.bean.SignPrize;
import com.nieyue.bean.Subscription;
import com.nieyue.dao.SignPrizeDao;
import com.nieyue.service.AccountService;
import com.nieyue.service.SignPrizeService;
import com.nieyue.service.SubscriptionService;
@Service
public class SignPrizeServiceImpl extends BaseServiceImpl<SignPrize,Long> implements SignPrizeService{
	@Autowired
	SignPrizeDao signPrizeDao;
	@Autowired
	AccountService accountService;
	@Autowired
	SubscriptionService subscriptionService;
	
	@Override
	public List<SignPrize> list(int pageNum, int pageSize, String orderName, String orderWay, Wrapper<SignPrize> wrapper) {
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
				List<SignPrize> rl = signPrizeDao.selectPage(rowBounds, wrapper);
				if(rl!=null&&rl.size()>0){
					SignPrize r = rl.get(0);
			 		rl.forEach((a)->{
			 			Account account = accountService.load(r.getAccountId());
			 			a.setAccount(account);
			 		});
			 		rl.forEach((a)->{
			 			Subscription subscription = subscriptionService.load(r.getSubscriptionId());
			 			a.setSubscription(subscription);
			 		});
				}
				return rl;
	}
}
