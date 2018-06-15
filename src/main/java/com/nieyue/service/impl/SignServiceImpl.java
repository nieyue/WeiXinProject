package com.nieyue.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.bean.Prize;
import com.nieyue.bean.Sign;
import com.nieyue.bean.SignPrize;
import com.nieyue.bean.SignRecord;
import com.nieyue.bean.Subscription;
import com.nieyue.bean.ThirdInfo;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.AccountService;
import com.nieyue.service.PrizeService;
import com.nieyue.service.SignPrizeService;
import com.nieyue.service.SignRecordService;
import com.nieyue.service.SignService;
import com.nieyue.service.SubscriptionService;
import com.nieyue.service.ThirdInfoService;
import com.nieyue.util.DateUtil;
import com.nieyue.util.MyDom4jUtil;
@Service
public class SignServiceImpl extends BaseServiceImpl<Sign,Long> implements SignService{
	@Autowired
	SubscriptionService subscriptionService;
	@Autowired
	AccountService accountService;
	@Autowired
	ThirdInfoService thirdInfoService;
	@Autowired
	SignRecordService signRecordService;
	@Autowired
	PrizeService prizeService;
	@Autowired
	SignPrizeService signPrizeService;
	@Value("${myPugin.projectDomainUrl}")
	String projectDomainUrl;
	@Override
	public List<Sign> list(int pageNum, int pageSize, String orderName, String orderWay, Wrapper<Sign> wrapper) {
				List<Sign> rl = super.list(pageNum, pageSize, orderName, orderWay, wrapper);
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
	@Override
	public Sign load(Long id) {
		Sign a = super.load(id);
		//公众号
			if(!StringUtils.isEmpty(a)&&!StringUtils.isEmpty(a.getSubscriptionId())){			 				
			Subscription subscription = subscriptionService.load(a.getSubscriptionId());
			a.setSubscription(subscription);
			}
			//账户
			if(!StringUtils.isEmpty(a)&&!StringUtils.isEmpty(a.getSubscriptionId())){			 				
			Account account = accountService.load(a.getAccountId());
			a.setAccount(account);
			}
	 	return a;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public List<String> accountSign(Long subscriptionId, Long accountId, String uuid) {
		List<String> list=new ArrayList<>();
		//公众号
		Subscription subscription = subscriptionService.load(subscriptionId);
		if(subscription==null){
			throw new CommonRollbackException("公众号不存在");	
		}
		//查询账户的第三方信息
		Wrapper<ThirdInfo> wrapper=new EntityWrapper<>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	map.put("wx_uuid", uuid);
	 	Map<String, Object> nmap = MyDom4jUtil.getNoNullMap(map);
	 	if(nmap.size()<=0){
	 		throw new CommonRollbackException("最少一个参数");	 		
	 	}
	 	wrapper.allEq(nmap);
		List<ThirdInfo> tl = thirdInfoService.list(1, 1, null, null, wrapper);
		if(tl.size()<=0){
			throw new CommonRollbackException("账户不存在");
		}
		
		ThirdInfo thirdInfo = tl.get(0);
		//查询账户的签到
		Wrapper<Sign> sw=new EntityWrapper<>();
	 	Map<String,Object> swmap=new HashMap<String,Object>();
	 	swmap.put("subscription_id", subscriptionId);
	 	swmap.put("account_id", thirdInfo.getAccountId());
	 	Map<String, Object> nswmap = MyDom4jUtil.getNoNullMap(swmap);
	 	sw.allEq(nswmap);
		List<Sign> signlist = this.list(1, 1, null, null, sw);
		boolean b=false;
		//签到连续天数
		Integer realDayNumber=0;
		if(signlist.size()<=0){
			//签到表不存在，第一次签到
			Sign s=new Sign();
			s.setCreateDate(new Date());
			s.setUpdateDate(new Date());
			s.setDayNumber(1);//初始化连续天数
			s.setIntegral(1.0);
			s.setSubscriptionId(subscriptionId);
			s.setAccountId(thirdInfo.getAccountId());
			b = this.add(s);
			realDayNumber=1;
		}else{
			//记录签到
			Sign s = signlist.get(0);
			Wrapper<SignRecord> srw=new EntityWrapper<>();
			srw.allEq(nswmap);
			//初始化连续天数,先查当前公众号最近一天的
			List<SignRecord> srl = signRecordService.list(1, 1, "signDate", "desc", srw);
			if(srl.size()>0){
				SignRecord sr = srl.get(0);
				Long st = DateUtil.getSeparatedTime(new Date(), sr.getSignDate());
				if(st<1){//同一天
					throw new CommonRollbackException("今天已经签过到了");
				}else if(st>1){//相隔天数大于1，从新计算连续天数
					s.setDayNumber(1);
				}else if(st==1){//相隔天数为1，连续天数+1
					s.setDayNumber(s.getDayNumber()+1);
				}
				realDayNumber=s.getDayNumber();
			}
			s.setCreateDate(new Date());
			s.setUpdateDate(new Date());
			s.setIntegral(s.getIntegral()+1);
			s.setSubscriptionId(subscriptionId);
			s.setAccountId(thirdInfo.getAccountId());
			b = this.update(s);
		}
		if(!b){
			throw new CommonRollbackException("签到异常，请再次签到");
		}
		SignRecord signRecord=new SignRecord();
		signRecord.setAccountId(thirdInfo.getAccountId());
		signRecord.setIntegral(1.0);
		signRecord.setSignDate(new Date());
		signRecord.setSubscriptionId(subscriptionId);
		b=signRecordService.add(signRecord);
		//记录
		if(!b){
			throw new CommonRollbackException("签到异常，请再次签到");
		}
		//此时计算签到的时间是否有资格获取奖品
		Wrapper<Prize> pw=new EntityWrapper<>();
	 	Map<String,Object> pwmap=new HashMap<String,Object>();
	 	pwmap.put("day_number", realDayNumber);
	 	pw.allEq(MyDom4jUtil.getNoNullMap(pwmap));
		List<Prize> pl = prizeService.list(1, Integer.MAX_VALUE, null, null, pw);
		if(pl.size()>0){
			pl.forEach((e)->{
				Prize p=pl.get(0);
				if(subscription.getAccountId().equals(e.getAccountId())//创建奖品的账户id
						&&subscriptionId.equals(e.getSubscriptionId())
						){
					//符合最高级别的
					p=e;
				}else if(subscriptionId.equals(e.getSubscriptionId())){
					//公众号级别为第二
					p=e;
				}else if(subscription.getAccountId().equals(e.getAccountId())){
					p=e;
				}
				//发奖品
				SignPrize sp=new SignPrize();
				sp.setDayNumber(p.getDayNumber());
				sp.setName(p.getName());
				sp.setNumber(p.getNumber());
				sp.setImgAddress(p.getImgAddress());
				sp.setContent(p.getContent());
				sp.setPrizeDate(new Date());
				sp.setStatus(1);//状态，1申请领奖，2领取成功，3拒绝发送
				sp.setSubscriptionId(subscriptionId);
				sp.setPrizeId(p.getPrizeId());
				sp.setAccountId(thirdInfo.getAccountId());
				signPrizeService.add(sp);
			});
		}
			return list;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public List<String> openidSign(Long subscriptionId,  String openid) {
		List<String> mpstrlist=new ArrayList<>();
		String mpstr="";
		//公众号
		Subscription subscription = subscriptionService.load(subscriptionId);
		if(subscription==null){
			mpstr="公众号不存在";
			mpstrlist.add(mpstr);
			return mpstrlist;
			//throw new CommonRollbackException("公众号不存在");	
		}
		//查询账户的签到
		Wrapper<Sign> sw=new EntityWrapper<>();
		Map<String,Object> swmap=new HashMap<String,Object>();
		swmap.put("subscription_id", subscriptionId);
		swmap.put("openid", openid);
		Map<String, Object> nswmap = MyDom4jUtil.getNoNullMap(swmap);
		sw.allEq(nswmap);
		List<Sign> signlist = this.list(1, 1, null, null, sw);
		boolean b=false;
		List<Sign> list=new ArrayList<>();
		//签到连续天数
		Integer realDayNumber=0;
		//签到总积分
		Double integral=0.0;
		if(signlist.size()<=0){
			//签到表不存在，第一次签到
			Sign s=new Sign();
			s.setCreateDate(new Date());
			s.setUpdateDate(new Date());
			s.setDayNumber(1);//初始化连续天数
			s.setIntegral(1.0);
			s.setSubscriptionId(subscriptionId);
			s.setOpenid(openid);
			b = this.add(s);
			list.add(s);
			realDayNumber=1;
			integral=1.0;
		}else{
			//记录签到
			Sign s = signlist.get(0);
			Wrapper<SignRecord> srw=new EntityWrapper<>();
			srw.allEq(nswmap);
			//初始化连续天数,先查当前公众号最近一天的
			List<SignRecord> srl = signRecordService.list(1, 1, "signDate", "desc", srw);
			if(srl.size()>0){
				SignRecord sr = srl.get(0);
				Long st = DateUtil.getSeparatedTime(new Date(), sr.getSignDate());
				if(st<1){//同一天
					//throw new CommonRollbackException("今天已经签过到了")
					mpstr="今天您已经签到过了，感谢亲的关注";
					mpstrlist.add(mpstr);
					return mpstrlist;
				}else if(st>1){//相隔天数大于1，从新计算连续天数
					s.setDayNumber(1);
				}else if(st==1){//相隔天数为1，连续天数+1
					s.setDayNumber(s.getDayNumber()+1);
				}
				realDayNumber=s.getDayNumber();
			}
			s.setCreateDate(new Date());
			s.setUpdateDate(new Date());
			s.setIntegral(s.getIntegral()+1);
			s.setSubscriptionId(subscriptionId);
			s.setOpenid(openid);
			b = this.update(s);
			list.add(s);
			integral=s.getIntegral();
		}
		
		if(!b){
			mpstr="签到异常，请再次签到";
			mpstrlist.add(mpstr);
			return mpstrlist;
			//throw new CommonRollbackException("签到异常，请再次签到");
		}
		SignRecord signRecord=new SignRecord();
		signRecord.setOpenid(openid);
		signRecord.setIntegral(1.0);
		signRecord.setSignDate(new Date());
		signRecord.setSubscriptionId(subscriptionId);
		b=signRecordService.add(signRecord);
		//记录
		if(!b){
			mpstr="签到异常，请再次签到";
			mpstrlist.add(mpstr);
			return mpstrlist;
			//throw new CommonRollbackException("签到异常，请再次签到");
		}
		mpstr="签到成功!\n 您已连续签到"+realDayNumber+"次。\n 本次签到，您获得了以下奖励：1积分奖励，您现有总积分:"+integral+",继续签到可获得精美礼品！\n 签到说明：签到送礼为连续签到，若期间忘记者，则会重新开始计算连续签到天数。";
		
		//此时计算签到的时间是否有资格获取奖品
		Wrapper<Prize> pw=new EntityWrapper<>();
		Map<String,Object> pwmap=new HashMap<String,Object>();
		pwmap.put("day_number", realDayNumber);
		pw.allEq(MyDom4jUtil.getNoNullMap(pwmap));
		List<Prize> pl = prizeService.list(1, Integer.MAX_VALUE, null, null, pw);
		if(pl.size()>0){
			Prize p=pl.get(0);
			for (Prize e : pl) {
				if(subscription.getAccountId().equals(e.getAccountId())//创建奖品的账户id
						&&subscriptionId.equals(e.getSubscriptionId())
						){
					//符合最高级别的
					p=e;
				}else if(subscriptionId.equals(e.getSubscriptionId())){
					//公众号级别为第二
					p=e;
				}else if(subscription.getAccountId().equals(e.getAccountId())){
					p=e;
				}
				//发奖品
				SignPrize sp=new SignPrize();
				sp.setDayNumber(p.getDayNumber());
				sp.setName(p.getName());
				sp.setNumber(p.getNumber());
				sp.setImgAddress(p.getImgAddress());
				sp.setContent(p.getContent());
				sp.setPrizeDate(new Date());
				sp.setStatus(1);//状态，1申请领奖，2领取成功，3拒绝发送
				sp.setSubscriptionId(subscriptionId);
				sp.setPrizeId(p.getPrizeId());
				sp.setOpenid(openid);
				signPrizeService.add(sp);
				mpstr+="\n\n >>><a href='"+projectDomainUrl+"/home/sign_prize.html?openid="+openid+"'>点击查看签到礼品</a>";
			}
		}
		mpstrlist.add(mpstr);
		
		return mpstrlist;
	}
}
