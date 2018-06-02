package com.nieyue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.BaseService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;


/**
 * 基础控制类
 * @author yy
 *
 */
public class BaseController<T,ID> {
	@Autowired
	private BaseService<T,ID> baseService;
	

	/**
	 * 增加
	 * @return 
	 */
	public  StateResultList<List<T>> add( T t) {
		List<T> list = new ArrayList<T>();
		boolean am = baseService.add(t);
		if(am){
			list.add(t);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		throw new CommonRollbackException("增加失败");
	}
	/**
	 * 修改
	 * @return
	 */
	public  StateResultList<List<T>> update( T t)  {
		List<T> list = new ArrayList<T>();
		boolean um = baseService.update(t);
		if(um){
			list.add(t);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		throw new CommonRollbackException("修改失败");
	}
	/**
	 * 删除
	 * @return
	 */
	public StateResultList<List<T>> delete(ID id)  {
		StateResultList<List<T>> ll = load(id);
		boolean dm = baseService.delete(id);
		if(dm){
			return ll;
		}
		throw new CommonRollbackException("删除失败");
	}
	/**
	 * 数量
	 * @return
	 */
	public StateResultList<List<Integer>> count(Wrapper<T> wrapper)  {
		List<Integer> list = new ArrayList<Integer>();
		int count = baseService.count(wrapper);
		if(count>=0){			
		list.add(count);
		return ResultUtil.getSlefSRSuccessList(list);
		}else{
			throw new NotIsNotExistException("");//不存在
		}
	}
	/**
	 * 单个加载
	 * @return
	 */
	public  StateResultList<List<T>> load(ID id)  {
		List<T> list = new ArrayList<T>();
		T t = baseService.load(id);
			if(t!=null &&!t.equals("")){
				list.add(t);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotIsNotExistException("");//不存在
			}
	}
	/**
	 * 分页浏览
	 * @return
	 */
	public  StateResultList<List<T>> list(
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay,
			Wrapper<T> wrapper)  {
		
			List<T> list = baseService.list(pageNum,pageSize,orderName,orderWay,wrapper);
			if(list!=null&&list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotAnymoreException();//没有更多
			}
	}
	
}
