package com.nieyue.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.nieyue.dao.SignDao;
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
	SignDao signDao;
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
	@Override
	public List<Sign> list(int pageNum, int pageSize, String orderName, String orderWay, Wrapper<Sign> wrapper) {
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
				List<Sign> rl = signDao.selectPage(rowBounds, wrapper);
				if(rl!=null&&rl.size()>0){
			 		rl.forEach((a)->{
			 			//公众号
			 			Subscription subscription = subscriptionService.load(a.getSubscriptionId());
			 			a.setSubscription(subscription);
			 			//账户
			 			Account account = accountService.load(a.getAccountId());
			 			a.setAccount(account);
			 			
			 		});
				}
				return rl;
	}
	@Override
	public Sign load(Long id) {
		Sign a = signDao.selectById(id);
		//公众号
		Subscription subscription = subscriptionService.load(a.getSubscriptionId());
		a.setSubscription(subscription);
		//账户
		Account account = accountService.load(a.getAccountId());
		a.setAccount(account);
	 	return a;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public List<Sign> signSign(Long subscriptionId, Long accountId, String wxOpenid, String wxUuid) {
		//公众号
		Subscription subscription = subscriptionService.load(subscriptionId);
		if(subscription==null){
			throw new CommonRollbackException("公众号不存在");	
		}
		//查询账户的第三方信息
		Wrapper<ThirdInfo> wrapper=new EntityWrapper<>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	map.put("wx_uuid", wxUuid);
	 	Map<String,Object> likemap=new HashMap<String,Object>();
	 	//openid为数据集合，所以，模糊查询
	 	map.put("wx_openid", wxOpenid);
	 	Map<String, Object> nmap = MyDom4jUtil.getNoNullMap(map);
	 	Map<String, Object> nlikemap = MyDom4jUtil.getNoNullMap(likemap);
	 	if(nmap.size()<=0&&nlikemap.size()<=0){
	 		throw new CommonRollbackException("最少一个参数");	 		
	 	}
	 	wrapper.allEq(nmap);
	 	Set<Entry<String, Object>> newmaplie = nlikemap.entrySet();
	 	for (Entry<String, Object> entry : newmaplie) {
	 		wrapper.like(entry.getKey(),(String)entry.getValue());			
		}
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
		List<Sign> list=new ArrayList<>();
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
			list.add(s);
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
			list.add(s);
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
		List<Prize> pl = prizeService.list(1, 1, null, null, pw);
		if(pl.size()>0){
			Prize p = pl.get(0);
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
		}
			return list;
	}
}
