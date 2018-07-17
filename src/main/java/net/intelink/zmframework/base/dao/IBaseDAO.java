package net.intelink.zmframework.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Dao 基础类
 * @param <T> 业务实体
 * @param <ID> 业务主键类型
 *
 * @author suzhongqiang
 * @date 2017.06.03
 */
public interface IBaseDAO<T, ID extends Serializable> {
    public Integer insert(T t);

    public Integer insertBatch(List<T> t);

    public Integer deleteBatchById(List<ID> ids);

    public Integer deleteById(ID id);

    public Integer update(T t);

    public T find(Map<String, Object> conditions);

    public T findById(ID id);

    public List<T> findList(Map<String, Object> conditions);

    public List<T> findListByPage(Map<String, Object> conditions);

    public Integer count(Map<String, Object> conditions);
}
