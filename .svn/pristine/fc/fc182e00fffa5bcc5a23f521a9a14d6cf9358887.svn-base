package net.intelink.zmframework.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * fastJson字段过滤工具类
 * @author feng
 *
 */
public class ResultFilterUtil {

	/**
	 * 过滤字段
	 * @param filterList
	 * @param listFiled
	 * @param t
	 * @return
	 */
	public static <T> List<T> getResultFilterStr(List<T> filterList,List<String> listFiled,Class<T> t ) {
		PropertyFilter statProfilter = new PropertyFilter() {
			@Override
			public boolean apply(Object object, String name, Object value) {
				if(listFiled != null && listFiled.size() > 0){
					return listFiled.contains(name);
				}else{
					return true;
				}
			}
		};
		return JsonUtil.json2List(JSON.toJSONString(filterList, statProfilter), t);
	}
	
	
}
