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
import com.nieyue.bean.TemplateData;
import com.nieyue.service.TemplateDataService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 模板数据控制类
 * @author yy
 *
 */
@Api(tags={"TemplateData"},value="模板数据",description="模板数据管理")
@RestController
@RequestMapping("/TemplateData")
public class TemplateDataController extends BaseController<TemplateData,Long> {
	@Resource
	private TemplateDataService TemplateDataService;
	
	/**
	 * 模板数据分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "模板数据列表", notes = "模板数据分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="templateMessageId",value="模板消息id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<TemplateData>> list(
			@RequestParam(value="templateMessageId",required=false)Long templateMessageId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<TemplateData> wrapper=new EntityWrapper<TemplateData>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("template_message_id", templateMessageId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<TemplateData>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 模板数据修改
	 * @return
	 */
	@ApiOperation(value = "模板数据修改", notes = "模板数据修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<TemplateData>> update(@ModelAttribute TemplateData templateData,HttpSession session)  {
		templateData.setUpdateDate(new Date());
		StateResultList<List<TemplateData>> u = super.update(templateData);
		return u;
	}
	/**
	 * 模板数据增加
	 * @return 
	 */
	@ApiOperation(value = "模板数据增加", notes = "模板数据增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<TemplateData>> add(@ModelAttribute TemplateData templateData, HttpSession session) {
		templateData.setCreateDate(new Date());
		templateData.setUpdateDate(new Date());
		StateResultList<List<TemplateData>> a = super.add(templateData);
		return a;
	}
	/**
	 * 模板数据删除
	 * @return
	 */
	@ApiOperation(value = "模板数据删除", notes = "模板数据删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="templateDataId",value="模板数据ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<TemplateData>> delete(@RequestParam("templateDataId") Long templateDataId,HttpSession session)  {
		StateResultList<List<TemplateData>> d = super.delete(templateDataId);
		return d;
	}
	/**
	 * 模板数据浏览数量
	 * @return
	 */
	@ApiOperation(value = "模板数据数量", notes = "模板数据数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="templateMessageId",value="模板消息id",dataType="long", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="templateMessageId",required=false)Long templateMessageId,
			HttpSession session)  {
		Wrapper<TemplateData> wrapper=new EntityWrapper<TemplateData>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("template_message_id", templateMessageId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 模板数据单个加载
	 * @return
	 */
	@ApiOperation(value = "模板数据单个加载", notes = "模板数据单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="templateDataId",value="模板数据ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<TemplateData>> loadTemplateData(@RequestParam("templateDataId") Long templateDataId,HttpSession session)  {
		 StateResultList<List<TemplateData>> l = super.load(templateDataId);
		 return l;
	}
	
}
