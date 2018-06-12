package com.nieyue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.bean.SignPrize;
import com.nieyue.bean.Subscription;
import com.nieyue.service.AccountService;
import com.nieyue.service.SignPrizeService;
import com.nieyue.service.SubscriptionService;
@Service
public class SignPrizeServiceImpl extends BaseServiceImpl<SignPrize,Long> implements SignPrizeService{
	@Autowired
	AccountService accountService;
	@Autowired
	SubscriptionService subscriptionService;
	
	@Override
	public List<SignPrize> list(int pageNum, int pageSize, String orderName, String orderWay, Wrapper<SignPrize> wrapper) {
				List<SignPrize> rl = super.list(pageNum, pageSize, orderName, orderWay, wrapper);
				if(rl!=null&&rl.size()>0){
			 		rl.forEach((a)->{
			 			//公众号
			 			if(!StringUtils.isEmpty(a.getSubscriptionId())){			 				
			 			Subscription subscription = subscriptionService.load(a.getSubscriptionId());
			 			a.setSubscription(subscription);
			 			}
			 			//账户
			 			if(!StringUtils.isEmpty(a.getAccountId())){			 				
			 			Account account = accountService.load(a.getAccountId());
			 			a.setAccount(account);
			 			}
			 		});
				}
				return rl;
	}
}
