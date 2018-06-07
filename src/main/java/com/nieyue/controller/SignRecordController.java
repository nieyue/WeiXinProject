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
import com.nieyue.bean.SignRecord;
import com.nieyue.service.SignRecordService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 签到记录控制类
 * @author yy
 *
 */
@Api(tags={"signRecord"},value="签到记录",description="签到记录管理")
@RestController
@RequestMapping("/signRecord")
public class SignRecordController extends BaseController<SignRecord,Long> {
	@Resource
	private SignRecordService signRecordService;
	
	/**
	 * 签到记录分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "签到记录列表", notes = "签到记录分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="signDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignRecord>> list(
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="signDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<SignRecord> wrapper=new EntityWrapper<SignRecord>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<SignRecord>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 签到记录修改
	 * @return
	 */
	@ApiOperation(value = "签到记录修改", notes = "签到记录修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignRecord>> update(@ModelAttribute SignRecord signRecord,HttpSession session)  {
		StateResultList<List<SignRecord>> u = super.update(signRecord);
		return u;
	}
	/**
	 * 签到记录增加
	 * @return 
	 */
	@ApiOperation(value = "签到记录增加", notes = "签到记录增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignRecord>> add(@ModelAttribute SignRecord signRecord, HttpSession session) {
		signRecord.setSignDate(new Date());
		StateResultList<List<SignRecord>> a = super.add(signRecord);
		return a;
	}
	/**
	 * 签到记录删除
	 * @return
	 */
	@ApiOperation(value = "签到记录删除", notes = "签到记录删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="signRecordId",value="签到记录ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<SignRecord>> delete(@RequestParam("signRecordId") Long signRecordId,HttpSession session)  {
		StateResultList<List<SignRecord>> d = super.delete(signRecordId);
		return d;
	}
	/**
	 * 签到记录浏览数量
	 * @return
	 */
	@ApiOperation(value = "签到记录数量", notes = "签到记录数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<SignRecord> wrapper=new EntityWrapper<SignRecord>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("account_id", accountId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 签到记录单个加载
	 * @return
	 */
	@ApiOperation(value = "签到记录单个加载", notes = "签到记录单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="signRecordId",value="签到记录ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<SignRecord>> loadSignRecord(@RequestParam("signRecordId") Long signRecordId,HttpSession session)  {
		 StateResultList<List<SignRecord>> l = super.load(signRecordId);
		 return l;
	}
	
}
