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
import com.nieyue.bean.SignPrize;
import com.nieyue.service.SignPrizeService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 签到奖品控制类
 * @author yy
 *
 */
@Api(tags={"signPrize"},value="签到奖品",description="签到奖品管理")
@RestController
@RequestMapping("/signPrize")
public class SignPrizeController extends BaseController<SignPrize,Long> {
	@Resource
	private SignPrizeService signPrizeService;
	
	/**
	 * 签到奖品分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "签到奖品列表", notes = "签到奖品分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="dayNumber",value="连续天数",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="status",value="状态，1待申请，2申请领奖，3领取成功，4拒绝发送",dataType="int", paramType = "query"),
		@ApiImplicitParam(name="subscriptionId",value="公众号id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="prizeId",value="奖品id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="prizeDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignPrize>> list(
			@RequestParam(value="dayNumber",required=false)Long dayNumber,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="subscriptionId",required=false)Long subscriptionId,
			@RequestParam(value="prizeId",required=false)Long prizeId,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="prizeDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<SignPrize> wrapper=new EntityWrapper<SignPrize>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("day_number", dayNumber);
	 	map.put("status", status);
	 	map.put("subscription_id", subscriptionId);
	 	map.put("prize_id", prizeId);
	 	map.put("account_id", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<SignPrize>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 签到奖品修改
	 * @return
	 */
	@ApiOperation(value = "签到奖品修改", notes = "签到奖品修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignPrize>> update(@ModelAttribute SignPrize signPrize,HttpSession session)  {
		StateResultList<List<SignPrize>> u = super.update(signPrize);
		return u;
	}
	/**
	 * 签到奖品增加
	 * @return 
	 */
	@ApiOperation(value = "签到奖品增加", notes = "签到奖品增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignPrize>> add(@ModelAttribute SignPrize signPrize, HttpSession session) {
		signPrize.setPrizeDate(new Date());
		StateResultList<List<SignPrize>> a = super.add(signPrize);
		return a;
	}
	/**
	 * 签到奖品删除
	 * @return
	 */
	@ApiOperation(value = "签到奖品删除", notes = "签到奖品删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="signPrizeId",value="签到奖品ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignPrize>> delete(@RequestParam("signPrizeId") Long signPrizeId,HttpSession session)  {
		StateResultList<List<SignPrize>> d = super.delete(signPrizeId);
		return d;
	}
	/**
	 * 签到奖品浏览数量
	 * @return
	 */
	@ApiOperation(value = "签到奖品数量", notes = "签到奖品数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="dayNumber",value="连续天数",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="status",value="状态，1待申请，2申请领奖，3领取成功，4拒绝发送",dataType="int", paramType = "query"),
		@ApiImplicitParam(name="subscriptionId",value="公众号id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="prizeId",value="奖品id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="dayNumber",required=false)Long dayNumber,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="subscriptionId",required=false)Long subscriptionId,
			@RequestParam(value="prizeId",required=false)Long prizeId,
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<SignPrize> wrapper=new EntityWrapper<SignPrize>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("day_number", dayNumber);
	 	map.put("status", status);
	 	map.put("subscription_id", subscriptionId);
	 	map.put("prize_id", prizeId);
	 	map.put("account_id", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 签到奖品单个加载
	 * @return
	 */
	@ApiOperation(value = "签到奖品单个加载", notes = "签到奖品单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="signPrizeId",value="签到奖品ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList<List<SignPrize>> loadSignPrize(@RequestParam("signPrizeId") Long signPrizeId,HttpSession session)  {
		 StateResultList<List<SignPrize>> l = super.load(signPrizeId);
		 return l;
	}
	
}
