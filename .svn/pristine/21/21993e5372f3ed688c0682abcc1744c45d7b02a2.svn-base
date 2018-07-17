package net.intelink.zmframework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import net.intelink.zmframework.component.ICache;
import net.intelink.zmframework.component.impl.RedisCacheImpl;
import net.intelink.zmframework.component.impl.SessionImpl;
import net.intelink.zmframework.constants.SysConstants;
import net.intelink.zmframework.enums.EnumSysEnv;
import net.intelink.zmframework.exception.BaseException;
import net.intelink.zmframework.component.ISession;
import net.intelink.zmframework.model.AuthInfoModel;
import net.intelink.zmframework.model.StatInfo;
import net.intelink.zmframework.model.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @description session管理
 * @author suzhongqiang
 * @date 17/7/1 下午2:29
 * @version 1.0.0
 */

public class SessionManagerUtil {

    static LogHelper log = new LogHelper(SessionManagerUtil.class);

    private static ICache iCache = AppContextUtil.getBean(RedisCacheImpl.class);

//    public static UserInfoModel getOldUser(String token) {
//        String env = SystemPropertiesUtil.getContextProperty("system.environment");
//
////        if (env.toUpperCase().equals(EnumSysEnv.DEV.name())) {
//            String oldUserInfoStr = iCache.get(MessageFormat.format(SysConstants.TMS_OLD_SESSION, token));
//
//            if(StringUtil.isNotEmpty(oldUserInfoStr)){
//
//                UserInfoModel userInfoModel = JsonUtil.json2Object(oldUserInfoStr, UserInfoModel.class);
//
//                JSONObject objects = (JSONObject) JSON.parse(oldUserInfoStr);
//                Object userInfoMap = objects.get("userInfo");
//
//                StatInfo statInfo = JsonUtil.map2Object((Map) userInfoMap, StatInfo.class);
//                userInfoModel.setStatInfo(statInfo);
//
//                return userInfoModel;
//            }
//
////        }
//
//        ISession session = getSession(token);
//
//        String userInfoStr = session.getAttr(SysConstants.SESSION_USER_INFO);
//
//        return JsonUtil.json2Object(userInfoStr,UserInfoModel.class);
//    }

//    public static <T> T getUser(String token,Class<T> clazz) {
//
//        ISession session = getSession(token);
//        String userInfoStr = session.getAttr(SysConstants.SESSION_USER_INFO);
//
//        return JsonUtil.json2Object(userInfoStr,clazz);
//    }

//    public static void setExtInfo(String token,String name,String value){
//
//    }

//    public static AuthInfoModel getAuthInfo(String token) {
//        String authInfoStr = getSession(token).get(token,SysConstants.SESSION_AUTH_INFO);
//
//        return JsonUtil.json2Object(authInfoStr,AuthInfoModel.class);
//    }

//    public static void setUserInfo(String token,UserInfoModel userInfoModel){
//        setAttr(token,SysConstants.SESSION_USER_INFO,userInfoModel);
//    }

//    public static void setAuthInfo(String token, AuthInfoModel authInfoModel){
//        setAttr(token,SysConstants.SESSION_AUTH_INFO,authInfoModel);
//    }
//
//    public static void setAttr(String token, String name,Object value) {
//        checkParams(token,name,value);
//        getSession(token).setAttr(name,JsonUtil.toJson(value));
//
//    }

//    public static void removeAttr(String token,String name){
//        getSession(token).removeAttr(name);
//    }
//
//    public static void removeOldSession(String token){
//        getSession(token).removeOldSession();
//    }
//    private static void checkParams(String token,String name,Object value){
//        if (StringUtil.isEmpty(token))
//            throw new BaseException("[token] is null.");
//
//        if (StringUtil.isEmpty(name))
//            throw new BaseException("[name] is null.");
//
//        if(value == null)
//            throw new BaseException("[value] is null.");
//
//    }


    public static ISession getSession(String token){
        return new SessionImpl(token);
    }


}
