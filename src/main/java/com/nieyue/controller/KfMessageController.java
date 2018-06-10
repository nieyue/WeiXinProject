package com.nieyue.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.KfMessage;
import com.nieyue.service.KfMessageService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 客服消息控制类
 * @author yy
 *
 */
@Api(tags={"kfMessage"},value="客服消息",description="客服消息管理")
@RestController
@RequestMapping("/kfMessage")
public class KfMessageController extends BaseController<KfMessage,Long> {
	@Resource
	private KfMessageService kfMessageService;
	
	/**
	 * 客服消息分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "客服消息列表", notes = "客服消息分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="msgtype",value="消息类型",dataType="string", paramType = "query"),
		@ApiImplicitParam(name="subscriptionId",value="公众号id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfMessage>> list(
			@RequestParam(value="msgtype",required=false)String msgtype,
			@RequestParam(value="subscriptionId",required=false)Long subscriptionId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<KfMessage> wrapper=new EntityWrapper<KfMessage>();
		Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("msgtype", msgtype);
	 	map.put("subscription_id", subscriptionId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<KfMessage>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 客服消息修改
	 * @return
	 */
	@ApiOperation(value = "客服消息修改", notes = "客服消息修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfMessage>> update(@ModelAttribute KfMessage kfMessage,HttpSession session)  {
		kfMessage.setUpdateDate(new Date());
		StateResultList<List<KfMessage>> u = super.update(kfMessage);
		return u;
	}
	/**
	 * 客服消息增加
	 * @return 
	 */
	@ApiOperation(value = "客服消息增加", notes = "客服消息增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfMessage>> add(@ModelAttribute KfMessage kfMessage, HttpSession session) {
		kfMessage.setCreateDate(new Date());
		kfMessage.setUpdateDate(new Date());
		StateResultList<List<KfMessage>> a = super.add(kfMessage);
		return a;
	}
	/**
	 * 客服消息删除
	 * @return
	 */
	@ApiOperation(value = "客服消息删除", notes = "客服消息删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="kfMessageId",value="客服消息ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfMessage>> delete(@RequestParam("kfMessageId") Long kfMessageId,HttpSession session)  {
		StateResultList<List<KfMessage>> d = super.delete(kfMessageId);
		return d;
	}
	/**
	 * 客服消息浏览数量
	 * @return
	 */
	@ApiOperation(value = "客服消息数量", notes = "客服消息数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="msgtype",value="消息类型",dataType="string", paramType = "query"),
		@ApiImplicitParam(name="subscriptionId",value="公众号id",dataType="long", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="msgtype",required=false)String msgtype,
			@RequestParam(value="subscriptionId",required=false)Long subscriptionId,
			HttpSession session)  {
		Wrapper<KfMessage> wrapper=new EntityWrapper<KfMessage>();
		Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("msgtype", msgtype);
	 	map.put("subscription_id", subscriptionId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 客服消息单个加载
	 * @return
	 */
	@ApiOperation(value = "客服消息单个加载", notes = "客服消息单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="KfMessageId",value="客服消息ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<KfMessage>> loadKfMessage(@RequestParam("kfMessageId") Long kfMessageId,HttpSession session)  {
		 StateResultList<List<KfMessage>> l = super.load(kfMessageId);
		 return l;
	}
	/**
	 * 客服消息群发
	 * @return
	 */
	@ApiOperation(value = "客服消息群发", notes = "客服消息群发")
	@ApiImplicitParams({
		@ApiImplicitParam(name="KfMessageId",value="客服消息ID",dataType="long", paramType = "query",required=true)
	})
	@RequestMapping(value = "/sendKfMessage", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<KfMessage>> sendKfMessage(@RequestParam("kfMessageId") Long kfMessageId,HttpSession session)  {
		List<KfMessage> list = kfMessageService.sendKfMessage(kfMessageId);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(list);
	}
	
}
