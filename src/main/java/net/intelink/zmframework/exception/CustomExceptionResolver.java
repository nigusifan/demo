/**
 *
 */
package net.intelink.zmframework.exception;


import net.intelink.zmframework.util.RespUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一自定义异常拦截
 *
 * @author suzhq
 * @project
 * @date 2017.05.31
 */
public class CustomExceptionResolver extends SimpleMappingExceptionResolver {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            log.error("系统异常:",ex);
            log.error("请求路径:" + request.getRequestURI());

            log.error(ex.getMessage());
            RespUtil.error(ex, response);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ModelAndView();

    }

}
