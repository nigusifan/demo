package net.intelink.zmframework.util;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import net.intelink.zmframework.model.RequestHeader;
import net.intelink.zmframework.model.RequestModel;

public class RequestUtil {
	
	/**
	 * 获取请求Model
	 * @param t
	 * @return
	 */
	public static <T>RequestModel<T> getRequestModel(HttpServletRequest request,Class<T> t){
		RequestModel<T> requertModel = new RequestModel<>();
		String header = request.getParameter("header");
		if(StringUtil.isNotEmpty(header)){
			RequestHeader rh = JSON.parseObject(header, RequestHeader.class);
			requertModel.setHeader(rh);
		}
		if(t != null){
			String body = request.getParameter("body");
			if(StringUtil.isNotEmpty(body)){
				requertModel.setBody(JSON.parseObject(body, t));;
			}
		}
		return requertModel;
	}
	/**
	 * 获取请求Model
	 * @param t
	 * @return
	 */
	public static <T>RequestModel<T> getRequestModel(RequestHeader header,String body,Class<T> t){
		RequestModel<T> requertModel = new RequestModel<>();
		requertModel.setHeader(header);
		if(t != null){
			if(StringUtil.isNotEmpty(body)){
				requertModel.setBody(JSON.parseObject(body, t));;
			}
		}
		return requertModel;
	}
	
	public static <T>RequestModel<T> getRequestModel(String header,String body,Class<T> t){
		RequestModel<T> requertModel = new RequestModel<>();
		requertModel.setHeader(JsonUtil.json2Object(header, RequestHeader.class));
		if(t != null){
			if(StringUtil.isNotEmpty(body)){
				requertModel.setBody(JsonUtil.json2Object(body, t));;
			}
		}
		return requertModel;
	}
}
