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
import com.nieyue.bean.Sign;
import com.nieyue.service.SignService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 签到控制类
 * @author yy
 *
 */
@Api(tags={"sign"},value="签到",description="签到管理")
@RestController
@RequestMapping("/sign")
public class SignController extends BaseController<Sign,Long> {
	@Resource
	private SignService signService;
	
	/**
	 * 签到分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "签到列表", notes = "签到分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Sign>> list(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<Sign> wrapper=new EntityWrapper<Sign>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Sign>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 签到修改
	 * @return
	 */
	@ApiOperation(value = "签到修改", notes = "签到修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Sign>> update(@ModelAttribute Sign sign,HttpSession session)  {
		sign.setUpdateDate(new Date());
		StateResultList<List<Sign>> u = super.update(sign);
		return u;
	}
	/**
	 * 签到增加
	 * @return 
	 */
	@ApiOperation(value = "签到增加", notes = "签到增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Sign>> add(@ModelAttribute Sign sign, HttpSession session) {
		sign.setCreateDate(new Date());
		sign.setUpdateDate(new Date());
		StateResultList<List<Sign>> a = super.add(sign);
		return a;
	}
	/**
	 * 签到删除
	 * @return
	 */
	@ApiOperation(value = "签到删除", notes = "签到删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="signId",value="签到ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Sign>> delete(@RequestParam("signId") Long signId,HttpSession session)  {
		StateResultList<List<Sign>> d = super.delete(signId);
		return d;
	}
	/**
	 * 签到浏览数量
	 * @return
	 */
	@ApiOperation(value = "签到数量", notes = "签到数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<Sign> wrapper=new EntityWrapper<Sign>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 签到单个加载
	 * @return
	 */
	@ApiOperation(value = "签到单个加载", notes = "签到单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="signId",value="签到ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Sign>> loadSign(@RequestParam("signId") Long signId,HttpSession session)  {
		 StateResultList<List<Sign>> l = super.load(signId);
		 return l;
	}
	
}
