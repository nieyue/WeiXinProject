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
import com.nieyue.bean.ReceiptInfo;
import com.nieyue.exception.CommonNotRollbackException;
import com.nieyue.service.ReceiptInfoService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 收货信息控制类
 * @author yy
 *
 */
@Api(tags = { "receiptInfo" }, value = "收货信息", description = "收货信息管理")
@RestController
@RequestMapping("/receiptInfo")
public class ReceiptInfoController extends BaseController<ReceiptInfo, Long> {
	@Resource
	private ReceiptInfoService receiptInfoService;

	/**
	 * 收货信息分页浏览
	 * 
	 * @param orderName
	 *            收货信息排序数据库字段
	 * @param orderWay
	 *            收货信息排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "收货信息", notes = "收货信息分页浏览")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "openid", value = "微信openid", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "accountId", value = "账户ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isDefault", value = "默认为0不是，1是", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "date-time", paramType = "query"),
			@ApiImplicitParam(name = "updateDate", value = "更新时间", dataType = "date-time", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "页头数位", dataType = "int", paramType = "query", defaultValue = "1"),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", defaultValue = "10"),
			@ApiImplicitParam(name = "orderName", value = "排序字段", dataType = "string", paramType = "query", defaultValue = "receiptInfoId"),
			@ApiImplicitParam(name = "orderWay", value = "排序方式", dataType = "string", paramType = "query", defaultValue = "desc") 
			})
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> browsePagingReceiptInfo(
			@RequestParam(value = "openid", required = false) Long openid,
			@RequestParam(value = "accountId", required = false) Long accountId,
			@RequestParam(value = "isDefault", required = false) Integer isDefault,
			@RequestParam(value = "createDate", required = false) Date createDate,
			@RequestParam(value = "updateDate", required = false) Date updateDate,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "orderName", required = false, defaultValue = "receiptInfoId") String orderName,
			@RequestParam(value = "orderWay", required = false, defaultValue = "desc") String orderWay) {
		Wrapper<ReceiptInfo> wrapper = new EntityWrapper<>();
		Map<String, Object> map = new HashMap<>();
		map.put("openid", openid);
		map.put("account_id", accountId);
		map.put("is_default", isDefault);
		map.put("create_date", createDate);
		map.put("update_date", updateDate);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<ReceiptInfo>> rl = super.list(pageNum, pageSize, orderName, orderWay, wrapper);
		return rl;
	}

	/**
	 * 收货信息修改
	 * 
	 * @return
	 */
	@ApiOperation(value = "收货信息修改", notes = "收货信息修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> updateReceiptInfo(@ModelAttribute ReceiptInfo receiptInfo,HttpSession session)  {
		receiptInfo.setUpdateDate(new Date());
		StateResultList<List<ReceiptInfo>> u = super.update(receiptInfo);
		return u;
	}
	/**
	 * 收货信息增加
	 * @return 
	 */
	@ApiOperation(value = "收货信息增加", notes = "收货信息增加")
	@RequestMapping(value="/add", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> addReceiptInfo(@ModelAttribute ReceiptInfo receiptInfo, HttpSession session) {
		receiptInfo.setCreateDate(new Date());
		receiptInfo.setUpdateDate(new Date());
		StateResultList<List<ReceiptInfo>> a = super.add(receiptInfo);
		return a;
	}

	/**
	 * 收货信息删除
	 * 
	 * @return
	 */
	@ApiOperation(value = "收货信息删除", notes = "收货信息删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="receiptInfoId",value="收货信息ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ReceiptInfo>> delReceiptInfo(@RequestParam("receiptInfoId") Long receiptInfoId,HttpSession session)  {
		StateResultList<List<ReceiptInfo>> d = super.delete(receiptInfoId);
		return d;
	}
	/**
	 * 收货信息浏览数量
	 * @return
	 */
	@ApiOperation(value = "收货信息数量", notes = "收货信息数量查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="openid",value="微信openid",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="accountId",value="收货信息id",dataType="long", paramType = "query"),
		@ApiImplicitParam(name="isDefault",value="'默认为0不是，1是",dataType="int", paramType = "query"),
		@ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
		@ApiImplicitParam(name="updateDate",value="更新时间",dataType="date-time", paramType = "query")})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="openid",required=false)Long openid,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="isDefault",required=false)Integer isDefault,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			HttpSession session)  {
		Wrapper<ReceiptInfo> wrapper=new EntityWrapper<>();
	 	Map<String,Object> map=new HashMap<>();
	 	map.put("openid", openid);
	 	map.put("account_id", accountId);
	 	map.put("is_default", isDefault);
	 	map.put("create_date", createDate);
	 	map.put("update_date", updateDate);
	 	wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}

	/**
	 * 收货信息单个加载
	 * 
	 * @return
	 */
	@ApiOperation(value = "收货信息单个加载", notes = "收货信息单个加载")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "receiptInfoId", value = "收货信息ID", dataType = "int", paramType = "query", required = true) })
	@RequestMapping(value = "/load", method = { RequestMethod.GET, RequestMethod.POST })
	public StateResultList<List<ReceiptInfo>> loadReceiptInfo(@RequestParam("receiptInfoId") Long receiptInfoId,
			HttpSession session) {
		StateResultList<List<ReceiptInfo>> l = super.load(receiptInfoId);
		return l;
	}

	/**
	 * 设置默认收货信息
	 * 
	 * @return
	 * @throws CommonNotRollbackException 
	 */
	@ApiOperation(value = "设置默认收货信息", notes = "设置默认收货信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "receiptInfoId", value = "收货信息ID", dataType = "long", paramType = "query", required = true),
			@ApiImplicitParam(name = "accountId", value = "账户ID,与openid必须选一个", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "openid", value = "微信openid,与openid必须选一个", dataType = "long", paramType = "query") })
	@RequestMapping(value = "/setIsDefault", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody StateResultList<List<ReceiptInfo>> setReceiptInfoIsDefault(
			@RequestParam(value = "receiptInfoId") Long receiptInfoId,
			@RequestParam(value = "accountId", required = false) Long accountId,
			@RequestParam(value = "openid", required = false) Long openid, HttpSession session) throws CommonNotRollbackException {
		if (accountId == null && openid == null) {
			throw new CommonNotRollbackException("参数缺失");
		}
		Wrapper<ReceiptInfo> wrapper = new EntityWrapper<>();
		Map<String, Object> map = new HashMap<>();
		if (openid != null) {
			map.put("openid", openid);
		}
		if (openid != null) {
			map.put("account_id", accountId);
		}
		map.put("receiptInfo_id", receiptInfoId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		List<ReceiptInfo> l1 = receiptInfoService.list(1, Integer.MAX_VALUE, null, null, wrapper);
		if (l1.size() > 0) {
			for (int i = 0; i < l1.size(); i++) {
				ReceiptInfo re = l1.get(i);
				re.setIsDefault(0);
				 receiptInfoService.update(re);
			}
		}
		ReceiptInfo newreceiptInfo = receiptInfoService.load(receiptInfoId);
		newreceiptInfo.setIsDefault(1);
		StateResultList<List<ReceiptInfo>> ul = super.update(newreceiptInfo);

		return ul;
	}

}
