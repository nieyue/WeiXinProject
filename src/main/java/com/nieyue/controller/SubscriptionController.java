package com.nieyue.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.nieyue.bean.Subscription;
import com.nieyue.business.WeiXinMpBusiness;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.SubscriptionService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.exception.WxErrorException;


/**
 * 公众号控制类
 * @author yy
 *
 */
@Api(tags={"subscription"},value="公众号",description="公众号管理")
@RestController
@RequestMapping("/subscription")
public class SubscriptionController extends BaseController<Subscription,Long> {
	@Resource
	private SubscriptionService subscriptionService;
	@Autowired
	WeiXinMpBusiness weiXinMpBusiness;
	
	/**
	 * 公众号分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "公众号列表", notes = "公众号分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Subscription>> list(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			Wrapper<Subscription> wrapper=new EntityWrapper<Subscription>();
	 		Map<String,Object> map=new HashMap<String,Object>();
	 		map.put("account_id", accountId);
	 		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
	 		StateResultList<List<Subscription>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 公众号修改
	 * @return
	 */
	@ApiOperation(value = "公众号修改", notes = "公众号修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Subscription>> update(@ModelAttribute Subscription subscription,HttpSession session)  {
		if(StringUtils.isEmpty(subscription.getAppid())){
			throw new CommonRollbackException("appid必填");
		}
		Wrapper<Subscription> wrapper=new EntityWrapper<Subscription>();
		Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("appid", subscription.getAppid());
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		List<Subscription> s = subscriptionService.list(1, 1, null, null, wrapper);
		if(s.size()>0 && !s.get(0).getSubscriptionId().equals(subscription.getSubscriptionId())){
			throw new CommonRollbackException("appid已经存在");
		}
		subscription.setUpdateDate(new Date());
		StateResultList<List<Subscription>> u = super.update(subscription);
		if(u.getCode()==200){
			try {
				//更新内存中的
				weiXinMpBusiness.initupdate(subscription.getAppid(), subscription.getAppsecret(), subscription.getToken(), "aes");
			} catch (WxErrorException e) {
				
			}
		}
		return u;
	}
	/**
	 * 公众号增加
	 * @return 
	 */
	@ApiOperation(value = "公众号增加", notes = "公众号增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Subscription>> add(@ModelAttribute Subscription subscription, HttpSession session) {
		if(StringUtils.isEmpty(subscription.getAppid())){
			throw new CommonRollbackException("appid必填");
		}
		Wrapper<Subscription> wrapper=new EntityWrapper<Subscription>();
		Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("appid", subscription.getAppid());
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		int n = subscriptionService.count(wrapper);
		if(n>0){
			throw new CommonRollbackException("appid已经存在");
		}
		subscription.setCreateDate(new Date());
		subscription.setUpdateDate(new Date());
		StateResultList<List<Subscription>> a = super.add(subscription);
		if(a.getCode()==200){
			try {
				//更新内存中的
				weiXinMpBusiness.init(subscription.getAppid(), subscription.getAppsecret(), subscription.getToken(), "aes");
			} catch (WxErrorException e) {
				
			}
		}
		return a;
	}
	/**
	 * 公众号删除
	 * @return
	 */
	@ApiOperation(value = "公众号删除", notes = "公众号删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="subscriptionId",value="公众号ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Subscription>> delete(@RequestParam("subscriptionId") Long subscriptionId,HttpSession session)  {
		StateResultList<List<Subscription>> d = super.delete(subscriptionId);
		if(d.getCode()==200){
			Subscription s = d.getData().get(0);
			try {
				//更新内存中的
				weiXinMpBusiness.initdelete(s.getAppid(), s.getAppsecret(), s.getToken(), "aes");
			} catch (WxErrorException e) {
				
			}
		}
		return d;
	}
	/**
	 * 公众号浏览数量
	 * @return
	 */
	@ApiOperation(value = "公众号数量", notes = "公众号数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<Subscription> wrapper=new EntityWrapper<Subscription>();
		Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 公众号单个加载
	 * @return
	 */
	@ApiOperation(value = "公众号单个加载", notes = "公众号单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="subscriptionId",value="公众号ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Subscription>> loadSubscription(@RequestParam("subscriptionId") Long subscriptionId,HttpSession session)  {
		 StateResultList<List<Subscription>> l = super.load(subscriptionId);
		 return l;
	}
	
}
