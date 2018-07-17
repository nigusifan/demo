package net.intelink.zmframework.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import net.intelink.zmframework.component.ISession;
import net.intelink.zmframework.enums.EnumResCode;
import net.intelink.zmframework.exception.BaseException;
import net.intelink.zmframework.model.RequestHeader;
import net.intelink.zmframework.model.UserInfoModel;
import net.intelink.zmframework.util.JsonUtil;
import net.intelink.zmframework.util.LogHelper;
import net.intelink.zmframework.util.RespUtil;
import net.intelink.zmframework.util.SessionManagerUtil;
import net.intelink.zmframework.util.StringUtil;
import net.intelink.zmframework.util.SystemPropertiesUtil;

public class SessionCheckFilter implements Filter {

    LogHelper log = new LogHelper(SessionCheckFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        
        String url = request.getServletPath();
        
        if(StringUtil.isNotEmpty(url) && url.contains("imageUpload")){
        	filterChain.doFilter(servletRequest, servletResponse);
        	return;
        }

        
        if(!isPassUrl(url)){
        	String header = null;
        	String contentType = request.getContentType();
        	if(StringUtil.isNotEmpty(contentType) && contentType.contains("multipart/form-data")){
        		MultipartResolver  multipartResolver = new CommonsMultipartResolver();
        		request = multipartResolver.resolveMultipart((HttpServletRequest) request);
        	}
        	header = request.getParameter("header");
        	System.out.println(header);
            if (StringUtil.isEmpty(header)) {
                throw new BaseException(EnumResCode.PARAMETER_ERROR,header);
            }

            RequestHeader requestHeader = JsonUtil.json2Object(header, RequestHeader.class);
            
            ISession session = SessionManagerUtil.getSession(requestHeader.getToken());
            UserInfoModel userInfo = session.getOldUser();
            if (userInfo == null) {

                RespUtil.writer(EnumResCode.NO_LOGIN,response);
                return ;
            }
        }
        
        

        filterChain.doFilter(request, servletResponse);

       

        

    }

    @Override
    public void destroy() {

    }

    private boolean isPassUrl(String url){
        String passUrlStr = SystemPropertiesUtil.getContextProperty("pass.url");
        if (StringUtil.isNotEmpty(passUrlStr)) {
            String[] passUrlArr = passUrlStr.split(",");

            for (String passUrl : passUrlArr) {
                if(url.endsWith(passUrl)){
                    return true;
                }
            }

        }

        return false;
    }
}
