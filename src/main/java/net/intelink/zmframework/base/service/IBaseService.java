package net.intelink.zmframework.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseService<T, ID extends Serializable> {
    public T insert(T t);

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