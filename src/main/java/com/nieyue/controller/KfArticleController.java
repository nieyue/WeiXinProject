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
import com.nieyue.bean.KfArticle;
import com.nieyue.service.KfArticleService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 客服消息文章控制类
 * @author yy
 *
 */
@Api(tags={"kfArticle"},value="客服消息文章",description="客服消息文章管理")
@RestController
@RequestMapping("/kfArticle")
public class KfArticleController extends BaseController<KfArticle,Long> {
	@Resource
	private KfArticleService kfArticleService;
	
	/**
	 * 客服消息文章分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "客服消息文章列表", notes = "客服消息文章分页浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name="kfMessageId",value="客服消息id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
		@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
		@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
		@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfArticle>> list(
			@RequestParam(value="kfMessageId",required=false)Long kfMessageId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<KfArticle> wrapper=new EntityWrapper<KfArticle>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("kf_message_id", kfMessageId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<KfArticle>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 客服消息文章修改
	 * @return
	 */
	@ApiOperation(value = "客服消息文章修改", notes = "客服消息文章修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfArticle>> update(@ModelAttribute KfArticle kfArticle,HttpSession session)  {
		kfArticle.setUpdateDate(new Date());
		StateResultList<List<KfArticle>> u = super.update(kfArticle);
		return u;
	}
	/**
	 * 客服消息文章增加
	 * @return 
	 */
	@ApiOperation(value = "客服消息文章增加", notes = "客服消息文章增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfArticle>> add(@ModelAttribute KfArticle kfArticle, HttpSession session) {
		kfArticle.setCreateDate(new Date());
		kfArticle.setUpdateDate(new Date());
		StateResultList<List<KfArticle>> a = super.add(kfArticle);
		return a;
	}
	/**
	 * 客服消息文章删除
	 * @return
	 */
	@ApiOperation(value = "客服消息文章删除", notes = "客服消息文章删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="kfArticleId",value="客服消息文章ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<KfArticle>> delete(@RequestParam("kfArticleId") Long kfArticleId,HttpSession session)  {
		StateResultList<List<KfArticle>> d = super.delete(kfArticleId);
		return d;
	}
	/**
	 * 客服消息文章浏览数量
	 * @return
	 */
	@ApiOperation(value = "客服消息文章数量", notes = "客服消息文章数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="kfMessageId",value="客服消息id",dataType="long", paramType = "query"),
	  })
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="kfMessageId",required=false)Long kfMessageId,
			HttpSession session)  {
		Wrapper<KfArticle> wrapper=new EntityWrapper<KfArticle>();
	 	Map<String,Object> map=new HashMap<String,Object>();
	 	map.put("kf_message_id", kfMessageId);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 客服消息文章单个加载
	 * @return
	 */
	@ApiOperation(value = "客服消息文章单个加载", notes = "客服消息文章单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="KfArticleId",value="客服消息文章ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<KfArticle>> loadKfArticle(@RequestParam("kfArticleId") Long kfArticleId,HttpSession session)  {
		 StateResultList<List<KfArticle>> l = super.load(kfArticleId);
		 return l;
	}
	
}
