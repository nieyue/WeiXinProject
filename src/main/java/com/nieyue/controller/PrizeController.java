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
import com.nieyue.bean.Prize;
import com.nieyue.service.PrizeService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 奖品控制类
 * @author yy
 *
 */
@Api(tags={"prize"},value="奖品",description="奖品管理")
@RestController
@RequestMapping("/prize")
public class PrizeController extends BaseController<Prize,Long> {
	@Resource
	private PrizeService prizeService;
	
	/**
	 * 奖品分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "奖品列表", notes = "奖品分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="dayNumber",value="连续天数id",dataType="int", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Prize>> list(
			@RequestParam(value="dayNumber",required=false)Integer dayNumber,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<Prize> wrapper=new EntityWrapper<Prize>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("day_number", dayNumber);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Prize>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 奖品修改
	 * @return
	 */
	@ApiOperation(value = "奖品修改", notes = "奖品修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Prize>> update(@ModelAttribute Prize prize,HttpSession session)  {
		prize.setUpdateDate(new Date());
		StateResultList<List<Prize>> u = super.update(prize);
		return u;
	}
	/**
	 * 奖品增加
	 * @return 
	 */
	@ApiOperation(value = "奖品增加", notes = "奖品增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Prize>> add(@ModelAttribute Prize prize, HttpSession session) {
		prize.setCreateDate(new Date());
		prize.setUpdateDate(new Date());
		StateResultList<List<Prize>> a = super.add(prize);
		return a;
	}
	/**
	 * 奖品删除
	 * @return
	 */
	@ApiOperation(value = "奖品删除", notes = "奖品删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="prizeId",value="奖品ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Prize>> delete(@RequestParam("prizeId") Long prizeId,HttpSession session)  {
		StateResultList<List<Prize>> d = super.delete(prizeId);
		return d;
	}
	/**
	 * 奖品浏览数量
	 * @return
	 */
	@ApiOperation(value = "奖品数量", notes = "奖品数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户id",dataType="int", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="dayNumber",required=false)Integer dayNumber,
			HttpSession session)  {
		Wrapper<Prize> wrapper=new EntityWrapper<Prize>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("day_number", dayNumber);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 奖品单个加载
	 * @return
	 */
	@ApiOperation(value = "奖品单个加载", notes = "奖品单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="prizeId",value="奖品ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Prize>> loadPrize(@RequestParam("prizeId") Long prizeId,HttpSession session)  {
		 StateResultList<List<Prize>> l = super.load(prizeId);
		 return l;
	}
	
}
