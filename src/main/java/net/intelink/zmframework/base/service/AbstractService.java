package net.intelink.zmframework.base.service;


import net.intelink.zmframework.base.dao.IBaseDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础业务实现抽象类
 * @param <T> 业务实体
 * @param <ID> 业务主键id
 *
 * @author suzhongqiang
 * @date 2017.05.27
 */
public abstract class AbstractService<T, ID extends Serializable> implements IBaseService<T, ID> {

	public abstract IBaseDAO<T, ID> getBaseDao();
	
	@Override
	public T insert(T t) {
		getBaseDao().insert(t);
		return t;
	}

	@Override
	public Integer insertBatch(List<T> t) {
		return getBaseDao().insertBatch(t);
	}

	@Override
	public Integer deleteBatchById(List<ID> ids) {
		return getBaseDao().deleteBatchById(ids);
	}

	@Override
	public Integer deleteById(ID id) {
		return getBaseDao().deleteById(id);
	}

	@Override
	public Integer update(T t) {
		return getBaseDao().update(t);
	}

	@Override
	public T find(Map<String, Object> parameter) {
		return getBaseDao().find(parameter);
	}

	@Override
	public T findById(ID id) {
		return getBaseDao().findById(id);
	}

	@Override
	public List<T> findList(Map<String, Object> parameter) {
		return getBaseDao().findList(parameter);
	}

	@Override
	public List<T> findListByPage(Map<String, Object> parameter) {
		return getBaseDao().findListByPage(parameter);
	}

	@Override
	public Integer count(Map<String, Object> parameter) {
		return getBaseDao().count(parameter);
	}

}
