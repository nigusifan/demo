package net.intelink.zmframework.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.intelink.zmframework.util.SystemPropertiesUtil;

/**
 * Controller 基础类
 *
 * @author suzhongqiang
 * @date 2017.06.03
 */
public abstract class BaseController {

	/**
	 * 开发环境设置允许跨域
	 * 
	 * @param response
	 */
	@ModelAttribute
	public void init(HttpServletResponse response) {
		String environment = SystemPropertiesUtil.getContextProperty("system.environment");
		if ("Dev".equals(environment)) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
			response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		}
	}


}
